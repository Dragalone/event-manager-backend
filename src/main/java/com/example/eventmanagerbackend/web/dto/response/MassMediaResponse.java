package com.example.eventmanagerbackend.web.dto.response;

import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MassMediaResponse {


    private UUID id;

    private String firstname;

    private String middlename;

    private String lastname;

    private String passportSeries;

    private String passportNumber;

    private String company;

    private String position;

    private String equipment;

    private Approvement approvement;

}
