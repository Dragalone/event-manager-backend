package com.example.eventmanagerbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_id")
    private UUID eventId;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_summary")
    private String eventSummary;

    @Column(name = "event_date")
    private Date eventDate;

    @Column(name = "reg_open")
    private Boolean regOpen;

    @Column(name = "address")
    private String address;

    @Column(name = "start_registration_date")
    private Instant startRegistrationDate;

    @Column(name = "close_registration_date")
    private Instant closeRegistrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    @ToString.Exclude
    private User organizer;
}

