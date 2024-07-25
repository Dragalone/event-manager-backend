package com.example.eventmanagerbackend.service.template;

import com.example.eventmanagerbackend.entity.TemplateEntity;
import com.example.eventmanagerbackend.entity.Type;
import com.example.eventmanagerbackend.repository.EventRepository;
import com.example.eventmanagerbackend.repository.TemplateRepository;
import com.example.eventmanagerbackend.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
@RequiredArgsConstructor
@Service
@Slf4j
public class TemplateService {
    private final String templatesPath = "templates/";

    private final TemplateRepository templateRepository;

    private final EventService eventService;
    private final EventRepository eventRepository;

    public ResponseEntity<String> getAllTemplates(String templateName, UUID eventId) {
        log.info("Request received for template: {} and eventId: {}", templateName, eventId);

        try {
            String filePath = templatesPath + templateName;
            log.info("Attempting to read template from path: {}", filePath);
            Resource resource = new ClassPathResource(filePath);
            Path path = Paths.get(resource.getURI());
            log.info("Resource exists: {}", resource.exists());
            if (resource.exists()) {

                byte[] data = Files.readAllBytes(path);
                String htmlContent = new String(data);
                log.info("Successfully read template content");
                return ResponseEntity.ok(htmlContent);
            } else {
                log.warn("Resource not found: {}", filePath);
            }
        } catch (IOException e) {
            log.error("Error reading template file: {}", templatesPath + templateName, e);
        }

        Optional<TemplateEntity> templateEntity = templateRepository.findAll().stream()
                .filter(template -> template.getTemplateName().equals(templateName) && template.getEvent().getId().equals(eventId))
                .findFirst();
        if (templateEntity.isPresent()) {
            log.info("Template found in database");
            return ResponseEntity.ok(templateEntity.get().getTemptext());
        } else {
            log.warn("Template not found in database for templateName: {} and eventId: {}", templateName, eventId);
        }

        log.warn("Returning 404 Not Found for templateName: {} and eventId: {}", templateName, eventId);
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> saveTemplate(UUID eventId, String templateName, Type type, Map<String, String> request) {
        try {
            String htmlContent = request.get("content");

            if (templateName == null || templateName.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            if (htmlContent == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Optional<TemplateEntity> existingTemplate = findExistingTemplate(eventId, type);

            existingTemplate.ifPresent(template -> {
                templateRepository.delete(template);
            });

            TemplateEntity newTemplate = new TemplateEntity();
            newTemplate.setTemplateName(templateName);
            newTemplate.setTemptext(htmlContent);
            newTemplate.setEvent(eventRepository.findById(eventId).get());
            newTemplate.setTemptype(type);
            templateRepository.save(newTemplate);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Optional<TemplateEntity> getTemplateByEventIdAndType(UUID eventId, Type type) {
        return templateRepository.findByEventIdAndTemptype(eventId, type);
    }

    public ResponseEntity<List<String>> getAllTemplatesByEventId(UUID eventId) {
        List<String> templateNames = new ArrayList<>();
        try {
            Resource resource = new ClassPathResource(templatesPath);
            Path path = Paths.get(resource.getURI());
            Files.list(path).filter(Files::isRegularFile).forEach(file -> {
                String fileName = file.getFileName().toString();
                if (!fileName.equals("badge2.html")) {
                    templateNames.add(fileName);
                }
            });
            Iterable<TemplateEntity> templates = templateRepository.findAllByEventId(eventId);
            templates.forEach(template -> templateNames.add(template.getTemplateName()));

            return ResponseEntity.ok(templateNames);
        } catch (IOException e) {
            e.printStackTrace(); // Вывод стек трейс для отладки
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    private Optional<TemplateEntity> findExistingTemplate(UUID eventId, Type type) {
        return templateRepository.findAll().stream()
                .filter(template -> template.getEvent().getId().equals(eventId) && template.getTemptype().equals(type))
                .findFirst();
    }


    public void deleteTemplate(TemplateEntity template) {
        templateRepository.delete(template);
    }

    public ResponseEntity<byte[]> downloadTemplate(String templateName, UUID eventId) {
        try {
            // Попытка загрузки шаблона из classpath
            Resource resource = new ClassPathResource(templatesPath + templateName);
            byte[] data = Files.readAllBytes(resource.getFile().toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            headers.setContentDispositionFormData("attachment", templateName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(data);
        } catch (IOException e) {
            // Если шаблон не найден в classpath, ищем в базе данных
            Optional<TemplateEntity> templateEntity = templateRepository.findAll().stream()
                    .filter(template -> template.getTemplateName().equals(templateName) && template.getEvent().getId().equals(eventId))
                    .findFirst();
            if (templateEntity.isPresent()) {
                byte[] data = templateEntity.get().getTemptext().getBytes();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.TEXT_HTML);
                headers.setContentDispositionFormData("attachment", templateName);

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(data);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
