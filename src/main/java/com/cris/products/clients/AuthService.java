package com.cris.products.clients;

import com.cris.products.model.dto.TokenResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Keycloaks",url = "localhost:8080/realms/MyRealmKC/", configuration = String.class)
public interface AuthService {

    @RequestMapping(method = RequestMethod.POST,
            value = "protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    JsonNode getToken(@RequestPart("client_id") String clientId,
                      @RequestPart("client_secret") String clientSecret,
                      @RequestPart("grant_type") String grantType);

    @RequestMapping(method = RequestMethod.POST, value = "protocol/openid-connect/token/introspect",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    JsonNode validateToken(@RequestPart("client_id") String clientId,
                           @RequestPart("client_secret") String clientSecret,
                           @RequestPart String token);
    // Response {"exp":1724123361,"iat":1724122461,"jti":"319cf707-0721-4bed-af9f-b7b468193255","iss":"http://localhost:8080/realms/MyRealmKC","aud":"account","sub":"838d3785-4aeb-4923-b272-3972e6723453","typ":"Bearer","azp":"thales-client","sid":"3d936071-15e4-40df-9bc5-a97a1a7b2364","acr":"1","allowed-origins":["/*"],"realm_access":{"roles":["default-roles-myrealmkc","ClientUs","offline_access","uma_authorization"]},"resource_access":{"account":{"roles":["manage-account","manage-account-links","view-profile"]}},"scope":"email profile","email_verified":false,"clientHost":"0:0:0:0:0:0:0:1","preferred_username":"service-account-thales-client","clientAddress":"0:0:0:0:0:0:0:1","client_id":"thales-client","username":"service-account-thales-client","token_type":"Bearer","active":true}

    @PostMapping(value = "protocol/openid-connect/token",
            consumes = "application/x-www-form-urlencoded")
    TokenResponse getTokenThales(@RequestHeader("x-correlation-id") String correlationId,
                                 @RequestParam("grant_type") String grantType,
                                 @RequestParam("assertion") String assertion);
}
