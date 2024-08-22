package com.cris.products.config;

import com.cris.products.clients.AuthService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    public AuthenticationFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        JsonNode jsonNode = authService.validateToken(request.getHeader("client_id"), request.getHeader("client_secret"), request.getHeader("token"));
//        if (request.getHeader("token") != null){
//
//            if(jsonNode.get("active").asBoolean()){
//                log.info("Activo -> {}", jsonNode);
//                filterChain.doFilter(request, response);
//            } else {
//                log.info("No valido: {} ", jsonNode);
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, jsonNode.asText());
//            }
//            filterChain.doFilter(request, response);
//        }
        log.info("Filtor ejecutado");
        filterChain.doFilter(request, response);
    }
}
