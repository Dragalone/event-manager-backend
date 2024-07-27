package com.example.eventmanagerbackend.service.schedule;

import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class EventScheduleService {

    private final EventRepository eventRepository;
    @Autowired
    public EventScheduleService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Scheduled(cron = "${interval-delete-event-cron}")
    public void changeRegistrationStatus(){
        LocalDate currentDate = LocalDate.now();
        List<Event> events = StreamSupport
                .stream(eventRepository.findAll().spliterator(), false)
                .filter(event -> {
                    LocalDate eventEnd = event.getCloseRegistrationDate().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate eventStart = event.getStartRegistrationDate().atZone(ZoneId.systemDefault()).toLocalDate();
                    return (event.getRegOpen() && currentDate.isAfter(eventEnd)) ||
                            (!event.getRegOpen() && currentDate.isAfter(eventStart) && currentDate.isBefore(eventEnd));
                })
                .toList();

        events.forEach(event -> {
            LocalDate eventEnd = event.getCloseRegistrationDate().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate eventStart = event.getStartRegistrationDate().atZone(ZoneId.systemDefault()).toLocalDate();

            if (event.getRegOpen() && currentDate.isAfter(eventEnd)) {
                event.setRegOpen(false);
            } else if (!event.getRegOpen() && currentDate.isAfter(eventStart) && currentDate.isBefore(eventEnd)) {
                event.setRegOpen(true);
            }
        });

        if (!events.isEmpty()) {
            eventRepository.saveAll(events);
        }

    }

}