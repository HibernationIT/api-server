package app.hbnationit.apiserver.security.models.dto;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class TokenRefreshRequest {
    private String username;
    private String refreshToken;
}
