package app.hbnationit.apiserver.security.models.dto;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class LoginRequest {
    private String username;
    private String code;
}
