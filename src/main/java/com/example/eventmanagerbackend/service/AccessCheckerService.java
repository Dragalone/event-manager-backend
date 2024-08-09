package com.example.eventmanagerbackend.service;

import com.example.eventmanagerbackend.aop.AccessCheckType;
import com.example.eventmanagerbackend.aop.Accessible;
import jakarta.servlet.http.HttpServletRequest;

public interface AccessCheckerService {

    boolean check(HttpServletRequest request, Accessible accessible);

    AccessCheckType getType();

}
