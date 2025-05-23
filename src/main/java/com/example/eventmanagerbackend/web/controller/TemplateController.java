package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.entity.Type;
import com.example.eventmanagerbackend.service.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/templates")
public class TemplateController {

    private final TemplateService templateService;

    private final String templatesPath = "templates/";

    @GetMapping("get/{eventId}")
    public ResponseEntity<List<String>> getAllTemplateNames(@PathVariable UUID eventId) {
    return templateService.getAllTemplatesByEventId(eventId);
    }

    @GetMapping("/{templateName}/{eventId}")
    public ResponseEntity<String> getTemplate(@PathVariable String templateName, @PathVariable UUID eventId) {
        return templateService.getAllTemplates(templateName, eventId);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveTemplate(
            @RequestParam UUID eventId,
            @RequestParam String templateName,
            @RequestParam Type type,  // Параметр необязательный, значение по умолчанию
            @RequestBody Map<String, String> request) {

        return templateService.saveTemplate(eventId, templateName, type, request);
    }


    @GetMapping("/download/{templateName}/{eventId}")
    public ResponseEntity<byte[]> downloadTemplate(@PathVariable String templateName, @PathVariable UUID eventId) {
        return templateService.downloadTemplate(templateName, eventId);
    }
}