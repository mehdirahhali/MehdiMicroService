package org.sid.billingservice.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Principal principal = (Principal) authentication.getPrincipal();
        KeycloakPrincipal keycloakPrincipal= (KeycloakPrincipal) principal;
        String accessToken = keycloakPrincipal.getKeycloakSecurityContext().getTokenString();
        requestTemplate.header("Authorization","Bearer "+accessToken);
    }
}
