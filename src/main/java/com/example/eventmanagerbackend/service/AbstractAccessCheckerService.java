package com.example.eventmanagerbackend.service;

import com.example.eventmanagerbackend.aop.Accessible;
import com.example.eventmanagerbackend.security.jwt.JwtAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractAccessCheckerService implements AccessCheckerService{


    private static final String ID = "id";



    @Override
    @SuppressWarnings("unchecked")
    public boolean check(HttpServletRequest request, Accessible accessible) {
        if (!isUserAuthenticated()) {
            return false;
        }
        if (hasAccessByRole(accessible)) {
            return true;
        }

        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        UUID id = UUID.fromString(pathVariables.get(ID));
        var user = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        UUID userId = user.getUserId();

        return check(id, userId);
    }

    protected abstract boolean check(UUID entityId, UUID userId);

    protected boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext() != null &&
                SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthentication;
    }

    protected boolean hasAccessByRole(Accessible accessible) {
        if (!isUserAuthenticated()) {
            return false;
        }

        var user = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();

        return accessible.availableForAdmin() &&
                user.hasAnyRole(List.of("ROLE_ADMIN"));
    }


}
