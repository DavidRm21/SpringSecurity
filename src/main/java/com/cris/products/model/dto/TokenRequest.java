package com.cris.products.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
public class TokenRequest {

    @JsonProperty("client-id")
    private String clientId;
    @JsonProperty("client-secret")
    private String clientSecret;
    @JsonProperty("grant_type")
    private String grantType;
    private String scope;

    @Override
    public String toString() {
        return "TokenRequest{" +
                "grantType='" + grantType + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
