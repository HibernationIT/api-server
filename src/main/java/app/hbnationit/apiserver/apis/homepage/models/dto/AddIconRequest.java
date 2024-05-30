package app.hbnationit.apiserver.apis.homepage.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class AddIconRequest {
    private String name;
    private String svg;
    private Boolean view;

    @Builder
    public AddIconRequest(String name, String svg, Boolean view) {
        this.name = name;
        this.svg = svg;
        this.view = view;
    }
}
