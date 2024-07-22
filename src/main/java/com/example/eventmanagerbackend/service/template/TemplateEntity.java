package com.example.eventmanagerbackend.service.template;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

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

    @Column(name = "eventid")
    private UUID eventid;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "temptype")
    private Type temptype;

    @Getter
    @Column (name = "templatename")
    private String templateName;

}
