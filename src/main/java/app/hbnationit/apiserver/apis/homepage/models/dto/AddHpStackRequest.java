package app.hbnationit.apiserver.apis.homepage.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class AddHpStackRequest {
    private String name;
    private String image;
    private String stackType;
    private Integer proficiency;

    @Builder
    public AddHpStackRequest(String name, String image, String stackType, Integer proficiency) {
        this.name = name;
        this.image = image;
        this.stackType = stackType;
        this.proficiency = proficiency;
    }
}
