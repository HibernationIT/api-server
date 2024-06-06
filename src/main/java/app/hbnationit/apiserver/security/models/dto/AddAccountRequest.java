package app.hbnationit.apiserver.security.models.dto;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class AddAccountRequest {
    private String email;
    private String name;
}
