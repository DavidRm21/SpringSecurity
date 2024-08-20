package com.cris.products.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private Integer expiresIn;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_at")
    private String expiresAt;
}
