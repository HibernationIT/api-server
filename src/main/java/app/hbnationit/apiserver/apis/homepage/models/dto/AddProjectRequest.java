package app.hbnationit.apiserver.apis.homepage.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public @Getter @Setter class AddProjectRequest {
    private String name;
    private String link;
    private String description;
    private Set<String> stacks;
    private Boolean view;
    private String image;
    private String content;

    @Builder
    public AddProjectRequest(String name, String link, String description, Set<String> stacks, Boolean view, String image, String content) {
        this.name = name;
        this.link = link;
        this.description = description;
        this.stacks = stacks;
        this.view = view;
        this.image = image;
        this.content = content;
    }
}
