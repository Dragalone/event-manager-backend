package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.entity.*;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.repository.EventMemberRepository;
import com.example.eventmanagerbackend.repository.EventRepository;
import com.example.eventmanagerbackend.repository.MassMediaRepository;
import com.example.eventmanagerbackend.service.EventService;
import com.example.eventmanagerbackend.service.document.DocumentService;
import com.example.eventmanagerbackend.web.dto.response.EventResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.lowagie.text.DocumentException;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@RequestMapping("api/v1/document")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class DocumentController {
    private final DocumentService documentService;
    @Autowired
    EventService eventService;
    @Autowired
    private EventMemberRepository eventMemberRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private MassMediaRepository massMediaRepository;

    @GetMapping(value = "/word/{memberId}",
            produces = "application/vnd.openxmlformats-"
                    + "officedocument.wordprocessingml.document")
    public ResponseEntity<InputStreamResource> word(@PathVariable("memberId") UUID memberId)
            throws IOException, InvalidFormatException {

        ByteArrayInputStream bis = documentService.generateWordBadge(memberId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "inline; filename=badge.docx");
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(bis));
    }
    /*@GetMapping(value = "/pdf",
            produces = "application/vnd.openxmlformats-"
                    + "officedocument.wordprocessingml.document")
    public ResponseEntity<InputStreamResource> pdf()
            throws IOException, InvalidFormatException {
        ByteArrayInputStream bis = DocumentService.generatePdf("qr.png", eventMember);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "inline; filename=mydoc.pdf");
        return ResponseEntity.ok().headers(headers).
                body(new InputStreamResource(bis));
    }
*/

    @GetMapping("/pdf/qr/{memberId}")
    public ResponseEntity<InputStreamResource> generatePdf(@PathVariable UUID memberId)
            throws IOException, DocumentException {
        EventMember eventMember = eventMemberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Event member not found"));
        EventResponse event = eventService.findById(eventMember.getEvent().getId());
        Map<String, Object> data = new HashMap<>();
        data.put("event_name", event.getName());
        data.put("first_name", eventMember.getFirstname());
        data.put("middlename", eventMember.getMiddlename());
        data.put("last_name", eventMember.getLastname());
        data.put("email", eventMember.getEmail());
        data.put("phone", eventMember.getPhone());
        data.put("position",eventMember.getPosition());
        data.put("company", eventMember.getCompany());
        data.put("event_date",event.getDate());
        data.put("status", "Участник");
        data.put("memberId",memberId);
        //ByteArrayInputStream bis = documentService.generatePdf("qr.png", eventMember, eventService.findById(eventMember.getEventId()));
        ByteArrayInputStream bis = documentService.generatePdfQrReport(data);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=mydoc.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/pdf/badges/{eventId}")
    public ResponseEntity<?> generatePdfBadges(@PathVariable UUID eventId)
            throws IOException, DocumentException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        Page<EventMember> members = eventMemberRepository.findByEventIdAndApprovement(eventId,Approvement.APPROVED, Pageable.unpaged());
        if (members.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Участники мероприятия не найдены");
        }

        ByteArrayInputStream bis = documentService.generatePdfBadges(members.getContent(),event.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=badges.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(bis));
    }


    @GetMapping("/csv/{eventId}")
    public ResponseEntity<?> generateCSV(@PathVariable UUID eventId) {
        String filename = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with ID " + eventId + " not found!"))
                .getName() + ".csv";

        // Используем только ASCII символы для имени файла
        String asciiFilename = filename.replaceAll("[^\\p{ASCII}]", "_");

        try {
            // Получаем одобренных участников
            Page<EventMember> approvedMembers = eventMemberRepository.findByEventIdAndApprovement(eventId, Approvement.APPROVED, Pageable.unpaged());

            // Определяем заголовки и данные
            String[] headers = {"Имя", "Фамилия", "Отчество", "Компания", "Должность", "Email", "Номер телефона", "Мероприятие", "Статус" };

            // Создаем ByteArrayOutputStream для записи данных
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {

                // Добавляем BOM для UTF-8
                writer.write("\uFEFF");

                // Записываем заголовки с использованием точки с запятой в качестве разделителя
                writer.write(String.join(";", headers) + "\n");

                // Записываем данные
                for (EventMember member : approvedMembers) {
                    writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                            member.getFirstname(),
                            member.getLastname(),
                            member.getMiddlename(),
                            member.getCompany(),
                            member.getPosition(),
                            member.getEmail(),
                            member.getPhone(),
                            member.getEvent().getName(),
                            member.getStatus().getStatus()));
                }
            }

            // Создаем поток для отдачи пользователю
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + asciiFilename + "\"")
                    .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating CSV file.");
        }
    }

//    @GetMapping("/csv/media/{eventId}")
//    public ResponseEntity<?> generateMediaCSV(@PathVariable UUID eventId) {
//        String filename = eventRepository.findById(eventId)
//                .orElseThrow(() -> new EntityNotFoundException("Event with ID " + eventId + " not found!"))
//                .getName() + ".csv";
//
//        // Используем только ASCII символы для имени файла
//        String asciiFilename = filename.replaceAll("[^\\p{ASCII}]", "_");
//
//        try {
//            // Получаем одобренных участников
//            Page<MassMedia> approvedMassMedia = massMediaRepository.findByEventIdAndApprovement(eventId, Approvement.APPROVED, Pageable.unpaged());
//
//            // Определяем заголовки и данные
//            String[] headers = {"Имя", "Фамилия", "Отчество", "Компания", "Должность", "Email", "Оборудование", "Серия паспорта", "Номер паспорта", "Мероприятие" };
//
//            // Создаем ByteArrayOutputStream для записи данных
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            try (OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {
//
//                // Добавляем BOM для UTF-8
//                writer.write("\uFEFF");
//
//                // Записываем заголовки с использованием точки с запятой в качестве разделителя
//                writer.write(String.join(";", headers) + "\n");
//
//                // Записываем данные
//                for (MassMedia massMedia : approvedMassMedia) {
//                    writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
//                            massMedia.getFirstname(),
//                            massMedia.getLastname(),
//                            massMedia.getMiddlename(),
//                            massMedia.getCompany(),
//                            massMedia.getPosition(),
//                            massMedia.getEmail(),
//                            massMedia.getEquipment(),
//                            massMedia.getPassportNumber(),
//                            massMedia.getPassportSeries(),
//                            massMedia.getEvent().getName()));
//                }
//            }
//
//            // Создаем поток для отдачи пользователю
//            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + asciiFilename + "\"")
//                    .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
//                    .body(resource);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating CSV file.");
//        }
//    }

}