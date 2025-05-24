package com.example.eventmanagerbackend.service.mail;



import com.example.eventmanagerbackend.entity.*;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.repository.EventMemberRepository;
import com.example.eventmanagerbackend.repository.EventRepository;
import com.example.eventmanagerbackend.repository.WaiterRepository;
import com.example.eventmanagerbackend.service.EventService;
import com.example.eventmanagerbackend.service.document.DocumentService;
import com.example.eventmanagerbackend.service.template.TemplateService;
import com.example.eventmanagerbackend.utils.mail.AbstractEmailContext;
import com.example.eventmanagerbackend.utils.mail.EmailContext;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.lowagie.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    private final EventMemberRepository eventMemberRepository;
    private final EventRepository eventRepository;
    private final WaiterRepository waiterRepository;

    private final TemplateService templateService;
    private final DocumentService documentService;

    public void sendMailWithPdf(UUID memberId) throws MessagingException, IOException, DocumentException {
        EventMember eventMember = eventMemberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Event member with ID {0} not found!", memberId)
                ));
        Event event = eventRepository.findById(eventMember.getEvent().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Event with ID {0} not found!", eventMember.getEvent().getId())
                ));

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd.MM.yyyy")
                .withZone(ZoneId.of("Europe/Moscow"));
        String formattedEventDate = formatter.format(event.getDate());

        Map<String, Object> pdfContext = new HashMap<>();
        pdfContext.put("memberId", eventMember.getId());
        pdfContext.put("имя", eventMember.getFirstname());
        pdfContext.put("отчество", eventMember.getMiddlename());
        pdfContext.put("фамилия", eventMember.getLastname());
        pdfContext.put("email", eventMember.getEmail());
        pdfContext.put("телефон", eventMember.getPhone());
        pdfContext.put("должность", eventMember.getPosition());
        pdfContext.put("организация", eventMember.getCompany());
        pdfContext.put("статус", eventMember.getStatus().getStatus());
        pdfContext.put("ивент_дата",formattedEventDate);
        pdfContext.put("ивент_имя", event.getName());
        pdfContext.put("ивент_описание", event.getSummary());
        pdfContext.put("ивент_адрес", event.getAddress());
        pdfContext.put("eventId", event.getId());

        //ByteArrayInputStream bis = documentService.generatePdf("qr.png", eventMember, eventService.findById(eventMember.getEventId()));

        Map<String, Object> templateContext = new HashMap<>();
        String templateContent;

        Optional<TemplateEntity> templateOpt = templateService.getTemplateByEventIdAndType(eventMember.getEvent().getId(), Type.APPROVED);

        if (templateOpt.isEmpty()) {
            try {
                Resource resource = new ClassPathResource("templates/email_message2.html");
                templateContent = Files.readString(Paths.get(resource.getURI()));
            } catch (IOException e) {

                e.printStackTrace();
                return;
            }
        } else {
            templateContent = templateOpt.get().getTemptext(); // Получаем содержимое шаблона из базы данных
        }


        // Вся инфа об участнике поступает в template
        templateContext.put("имя", eventMember.getFirstname());
        templateContext.put("отчество", eventMember.getMiddlename());
        templateContext.put("фамилия", eventMember.getLastname());
        templateContext.put("email", eventMember.getEmail());
        templateContext.put("телефон", eventMember.getPhone());
        templateContext.put("должность", eventMember.getPosition());
        templateContext.put("организация", eventMember.getCompany());
        templateContext.put("статус", eventMember.getStatus().getStatus());
        templateContext.put("memberId", eventMember.getId());
        templateContext.put("ивент_дата", formattedEventDate);
        templateContext.put("ивент_имя", event.getName());
        templateContext.put("ивент_описание", event.getSummary());
        templateContext.put("ивент_адрес", event.getAddress());
        AbstractEmailContext emailContext = new EmailContext();
        emailContext.setContext(templateContext);
        emailContext.setTo(eventMember.getEmail());
        emailContext.setSubject("Приглашение на мероприятие: "+ event.getName());
        emailContext.setTemplateLocation(templateContent);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        // Create Mustache context
        Map<String, Object> context = new HashMap<>(emailContext.getContext());
        // Compile Mustache template
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(emailContext.getTemplateLocation()), "template");
        // Execute template rendering
        StringWriter writer = new StringWriter();
        mustache.execute(writer, context);
        String emailContent = writer.toString();

        mimeMessageHelper.setTo(emailContext.getTo());
        mimeMessageHelper.setSubject(emailContext.getSubject());
        //mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setText(emailContent, true);
        ByteArrayInputStream bis = documentService.generatePdfQrReport(pdfContext);
        InputStreamSource inputStreamSource = new ByteArrayResource(bis.readAllBytes());
        mimeMessageHelper.addAttachment("Invitation.pdf",inputStreamSource);
        emailSender.send(message);
    }

    public void sendMail(UUID memberId) throws MessagingException {

        EventMember eventMember = eventMemberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Event member not found"));
        Event event = eventRepository.findById(eventMember.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        String templateContent = "";

        Optional<TemplateEntity> templateOpt = templateService.getTemplateByEventIdAndType(eventMember.getEvent().getId(), Type.GREETINGS);
        if (templateOpt.isEmpty()) {
            try {
                Resource resource = new ClassPathResource("templates/greetings2.html");
                templateContent = Files.readString(Paths.get(resource.getURI()));
            } catch (IOException e) {
                 new ResponseEntity<>("Unable to load default template", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            templateContent = templateOpt.get().getTemptext(); // Получаем содержимое шаблона из базы данных
        }

        // Подготовка контекста для шаблона
        Map<String, Object> templateContext = new HashMap<>();
        templateContext.put("имя", eventMember.getFirstname());
        templateContext.put("отчество", eventMember.getMiddlename());
        templateContext.put("фамилия", eventMember.getLastname());
        templateContext.put("email", eventMember.getEmail());
        templateContext.put("телефон", eventMember.getPhone());
        templateContext.put("должность", eventMember.getPosition());
        templateContext.put("организация", eventMember.getCompany());
        templateContext.put("статус", eventMember.getStatus().getStatus());
        templateContext.put("memberId", eventMember.getId());
        templateContext.put("ивент_дата", event.getDate());
        templateContext.put("ивент_имя", event.getName());
        templateContext.put("ивент_описание", event.getSummary());
        templateContext.put("ивент_адрес", event.getAddress());

        // Настройка контекста email
        AbstractEmailContext emailContext = new EmailContext();
        emailContext.setContext(templateContext);
        emailContext.setTo(eventMember.getEmail());
        emailContext.setSubject("Регистрация на мероприятие: " + event.getName());
        emailContext.setTemplateLocation(templateContent); // Используем содержимое шаблона напрямую


        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        // Create Mustache context
        Map<String, Object> context = new HashMap<>(emailContext.getContext());

        // Compile Mustache template from HTML content
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(emailContext.getTemplateLocation()), "template");

        // Execute template rendering
        StringWriter writer = new StringWriter();
        mustache.execute(writer, context);
        String emailContent = writer.toString();

        mimeMessageHelper.setTo(emailContext.getTo());
        mimeMessageHelper.setSubject(emailContext.getSubject());
        mimeMessageHelper.setText(emailContent, true);

        emailSender.send(message);
    }

    public void sendAlert(UUID waiterId)  throws MessagingException
    {
        Waiter waiter = waiterRepository.findById(waiterId).orElseThrow(() ->
                new EntityNotFoundException(
                        MessageFormat.format("Waiter with ID {0} now founded", waiterId)
                ));
        Event event = eventRepository.findById(waiter.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        String templateContent = "";

        Optional<TemplateEntity> templateOpt = templateService.getTemplateByEventIdAndType(waiter.getEvent().getId(), Type.GREETINGS);
        if (templateOpt.isEmpty()) {
            try {
                Resource resource = new ClassPathResource("templates/email_allert.html");
                templateContent = Files.readString(Paths.get(resource.getURI()));
            } catch (IOException e) {
                new ResponseEntity<>("Unable to load default template", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            templateContent = templateOpt.get().getTemptext(); // Получаем содержимое шаблона из базы данных
        }

        // Подготовка контекста для шаблона
        Map<String, Object> templateContext = new HashMap<>();
        templateContext.put("ивент_дата", event.getDate());
        templateContext.put("ивент_имя", event.getName());
        templateContext.put("ивент_описание", event.getSummary());
        templateContext.put("ивент_адрес", event.getAddress());

        templateContext.put("ссылка", "https://mkrit.ru/"+event.getId()+"/registration-form/");

        // Настройка контекста email
        AbstractEmailContext emailContext = new EmailContext();
        emailContext.setContext(templateContext);
        emailContext.setTo(waiter.getEmail());
        emailContext.setSubject("Открытие регистрации на: " + event.getName());
        emailContext.setTemplateLocation(templateContent); // Используем содержимое шаблона напрямую
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        // Create Mustache context
        Map<String, Object> context = new HashMap<>(emailContext.getContext());

        // Compile Mustache template from HTML content
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(emailContext.getTemplateLocation()), "template");

        // Execute template rendering
        StringWriter writer = new StringWriter();
        mustache.execute(writer, context);
        String emailContent = writer.toString();

        mimeMessageHelper.setTo(emailContext.getTo());
        mimeMessageHelper.setSubject(emailContext.getSubject());
        mimeMessageHelper.setText(emailContent, true);

        emailSender.send(message);
    }



// UNUSED FUNCTIONS
//    public void sendSimpleEmail(String toAddress, String subject, String message) {
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//
//        simpleMailMessage.setTo(toAddress);
//        simpleMailMessage.setSubject(subject);
//        simpleMailMessage.setText(message);
//
//        emailSender.send(simpleMailMessage);
//    }

}

