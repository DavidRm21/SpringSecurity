package com.cris.products.controller;

import com.cris.products.clients.AuthService;
import com.cris.products.model.dto.TokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Function;


@RestController
@Slf4j
@RequiredArgsConstructor
public class TokenController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthService authService;

    // http://localhost:8080/realms/MyRealmKC/protocol/openid-connect/token
    // grant_type:client_credentials
    // client_id:thales-client
    // client_secret:3NQr77MHh2VDKaXOdbzqcdDflwRS1RKt

    //http://localhost:8080/realms/MyRealmKC/protocol/openid-connect/token/introspect
    // client_id:thales-client
    // client_secret:3NQr7MHh2VDKaXOdbzqcdDflwRS1RK7t
    // token:
    @PostMapping(value = "/authz/d1/v1/oauth2")
    public ResponseEntity<String> getToken(
            @RequestHeader String authorization,
            @RequestParam("grant_type") String grantType) throws JsonProcessingException {
        log.info("Autorizacion,{} {}", authorization, grantType);

        // Funcion de decodificar un valor a base64
        Function<String, String> decodeBase64 = encoded -> new String(Base64.getDecoder().decode(encoded));
        // Obtener las credenciales atraves de un array
        Function<String, String[]> extractCredentials = credentials -> credentials.split(":", 2);

        String[] credentials = Optional.ofNullable(authorization)
                .map(auth -> auth.replace("Bearer", "").trim())
                .map(decodeBase64)
                .map(extractCredentials)
                .orElseThrow(() -> new IllegalArgumentException("autorizacion no valida"));

        String clientId = credentials[0];
        String clientSecret = credentials[1];

        JsonNode response = authService.getToken(clientId, clientSecret, grantType);

        String formattedExpirationTime = Optional.ofNullable(response.get("expires_in"))
                .map(JsonNode::asInt)
                .map(expIn -> Instant.now().plusSeconds(expIn).truncatedTo(ChronoUnit.MILLIS))
                .map(instant -> instant.atZone(ZoneOffset.UTC))
                .map(zdt -> zdt.format(DateTimeFormatter.ISO_INSTANT))
                .orElseThrow(() -> new IllegalArgumentException("Invalid expires_in value"));

        TokenResponse token = TokenResponse.builder()
                .accessToken(response.get("access_token").asText())
                .expiresIn(response.get("expires_in").asInt())
                .scope(response.get("scope").asText())
                .tokenType(response.get("token_type").asText())
                .expiresAt(formattedExpirationTime)
                .build();

        return ResponseEntity.ok(objectMapper.writeValueAsString(token));
    }
}
