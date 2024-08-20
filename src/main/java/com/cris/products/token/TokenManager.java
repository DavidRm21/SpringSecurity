package com.cris.products.token;


import com.cris.products.clients.AuthService;
import com.cris.products.model.dto.TokenResponse;

import java.time.Instant;

public class TokenManager {

    private static volatile TokenManager instance;

    private final AuthService thalesFeignClient;
    private String accessToken;
    private Instant tokenExpirationTime;
    private String scope;
    private String tokenType;

    private TokenManager(AuthService thalesFeignClient) {
        this.thalesFeignClient = thalesFeignClient;
    }

    public static TokenManager getInstance(AuthService thalesFeignClient) {
        if (instance == null) {
            synchronized (TokenManager.class) {
                if (instance == null) {
                    instance = new TokenManager(thalesFeignClient);
                }
            }
        }
        return instance;
    }

    public synchronized String getAccessToken() {
        if (accessToken == null || Instant.now().isAfter(tokenExpirationTime)) {
            refreshAccessToken();
        }
        return accessToken;
    }

    private void refreshAccessToken() {
        TokenResponse response = thalesFeignClient.getTokenThales("",
                "urn:ietf:params:oauth:grant-type:jwt-bearer", "your_assertion_here");
        this.accessToken = response.getAccessToken();
        this.tokenExpirationTime = Instant.now().plusSeconds(response.getExpiresIn());
    }
}
