package com.example.eventmanagerbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Data
@Entity
@Table(name = "templates")
public class TemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "templateid")
    private UUID id;

    @Column (columnDefinition="TEXT", name = "temptext")
    private String temptext;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "temptype")
    private Type temptype;

    @Getter
    @Column (name = "templatename")
    private String templateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    private Event event;

}
