package app.hbnationit.apiserver.security.models.dto;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class ModifyAccountRequest {
    private String name;
    private String image;
}
