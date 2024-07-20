package com.example.eventmanagerbackend.service;


import com.example.eventmanagerbackend.entity.User;
import com.example.eventmanagerbackend.repository.RoleTypeRepository;
import com.example.eventmanagerbackend.repository.UserRepository;
import com.example.eventmanagerbackend.security.jwt.JwtProvider;
import com.example.eventmanagerbackend.web.dto.request.JwtRequest;
import com.example.eventmanagerbackend.web.dto.response.JwtResponse;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService<JwtAuthentication> {

    private final UserRepository userRepository;

    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final RoleTypeRepository roleTypeRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(@NonNull JwtRequest authRequest) {
        final User user;
        try {
            user = userRepository.findByUsername(authRequest.getLogin())
                    .orElseThrow(() -> new AuthException("Пользователь не найден"));
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
        UUID userId = user.getId();

        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getUsername(), refreshToken);
            return new JwtResponse(accessToken, refreshToken,userId );
        } else {
            try {
                throw new AuthException("Неправильный пароль");
            } catch (AuthException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user;
                try {
                    user = userRepository.findByUsername(login)
                            .orElseThrow(() -> new AuthException("Пользователь не найден"));
                } catch (AuthException e) {
                    throw new RuntimeException(e);
                }
                final String accessToken = jwtProvider.generateAccessToken(user);
                System.out.println("Отдаем новый акцес токен = " + accessToken);
                return new JwtResponse(accessToken, null, null);
            }
        }
        return new JwtResponse(null, null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user;
                try {
                    user = userRepository.findByUsername(login)
                            .orElseThrow(() -> new AuthException("Пользователь не найден"));
                } catch (AuthException e) {
                    throw new RuntimeException(e);
                }
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken, null);
            }
        }
        try {
            throw new AuthException("Невалидный JWT токен");
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
