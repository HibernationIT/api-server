package app.hbnationit.apiserver.apis.homepage.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public @Getter @Setter class AddBlogRequest {
    private String name;
    private String description;
    private Set<String> tags;
    private Boolean view;
    private String image;
    private String content;

    @Builder
    public AddBlogRequest(String name, String description, Set<String> tags, Boolean view, String image, String content) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.view = view;
        this.image = image;
        this.content = content;
    }
}
