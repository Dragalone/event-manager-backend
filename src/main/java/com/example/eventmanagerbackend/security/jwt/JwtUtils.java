package com.example.eventmanagerbackend.security.jwt;



import com.example.eventmanagerbackend.entity.RoleType;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {
    private static final String USER_ID_CLAIM = "userId";

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setUserId(getUserId(claims));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<RoleType> getRoles(Claims claims) {
        List<Map<String, String>> rolesList = claims.get("roles", List.class);
        if (rolesList == null) {
            return Collections.emptySet();
        }

        Set<RoleType> roles = new HashSet<>();
        for (Map<String, String> roleMap : rolesList) {
            String roleName = roleMap.get("name");
            if (roleName != null) {
                RoleType role = new RoleType();
                role.setName(roleName);
                roles.add(role);
            }
        }

        return roles;
    }

    private static UUID getUserId(Claims claims) {
        Object userIdObj = claims.get(USER_ID_CLAIM);
        if (userIdObj instanceof String) {
            return UUID.fromString((String) userIdObj);
        }
        return null;
    }
}

