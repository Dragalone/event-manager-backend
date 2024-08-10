package com.example.eventmanagerbackend.service.schedule;

import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.entity.Waiter;
import com.example.eventmanagerbackend.repository.EventRepository;
import com.example.eventmanagerbackend.service.mail.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
public class EventScheduleService {

    private final EventRepository eventRepository;
    private final EmailService emailService;
    @Autowired
    public EventScheduleService(EventRepository eventRepository, EmailService emailService) {
        this.eventRepository = eventRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "${interval-delete-event-cron}")
    @Transactional
    public void changeRegistrationStatus() {
        log.info("Change events registration status");
        LocalDateTime currentDate = LocalDateTime.now(); // текущая дата и время
        List<Event> events = eventRepository.findAll();

        events.forEach(event -> {
            ZonedDateTime eventEndZoned = event.getCloseRegistrationDate().atZone(ZoneId.systemDefault());
            LocalDateTime eventEnd = eventEndZoned.toLocalDateTime();
            ZonedDateTime eventStartZoned = event.getStartRegistrationDate().atZone(ZoneId.systemDefault());
            LocalDateTime eventStart = eventStartZoned.toLocalDateTime();

            if (event.getRegOpen() && currentDate.isAfter(eventEnd)) {
                log.info("Event with ID {} close registration", event.getId());
                event.setRegOpen(Boolean.FALSE);
            } else if (!event.getRegOpen() && currentDate.isAfter(eventStart) && currentDate.isBefore(eventEnd)) {
                log.info("Event with ID {} open registration", event.getId());
                event.setRegOpen(Boolean.TRUE);

                List<Waiter> waiters = event.getWaiters();
                waiters.forEach(waiter -> {
                    try {
                        emailService.sendAlert(waiter.getId());
                        log.info("Send allert to waiter with  ID {}", waiter.getId());
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                });

            }
        });

        if (!events.isEmpty()) {
            eventRepository.saveAll(events);
        }
    }

}