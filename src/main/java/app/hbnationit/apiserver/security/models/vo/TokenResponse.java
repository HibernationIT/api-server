package app.hbnationit.apiserver.security.models.vo;

import lombok.Getter;

public @Getter class TokenResponse {
    private final String username;
    private final String accessToken;
    private final String refreshToken;

    public TokenResponse(String username, String accessToken, String refreshToken) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
