package app.hbnationit.apiserver.apis.homepage.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public @Getter @Setter class ModifyDesignRequest {
    private String name;
    private String description;
    private String link;
    private Boolean view;
    private String image;
    private Set<String> designs;

    @Builder
    public ModifyDesignRequest(String name, String description, String link, Boolean view, String image, Set<String> designs) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.view = view;
        this.image = image;
        this.designs = designs;
    }
}
