package com.example.eventmanagerbackend.service.impl;

import com.example.eventmanagerbackend.aop.AccessCheckType;
import com.example.eventmanagerbackend.service.AbstractAccessCheckerService;

import java.util.UUID;

public class UserCheckerService extends AbstractAccessCheckerService {

    @Override
    protected boolean check(UUID entityId, UUID userId) {
        return entityId.equals(userId);
    }

    @Override
    public AccessCheckType getType() {
        return AccessCheckType.USER;
    }
}
