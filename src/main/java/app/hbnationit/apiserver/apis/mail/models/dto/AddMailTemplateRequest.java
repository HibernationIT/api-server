package app.hbnationit.apiserver.apis.mail.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public @Getter @Setter class AddMailTemplateRequest {
    private String name;
    private String content;
    private List<String> variables;
}
