package com.example.eventmanagerbackend.web.dto.request;

import lombok.Data;

@Data
public class JwtRequest {

    private String login;

    private String password;
}
