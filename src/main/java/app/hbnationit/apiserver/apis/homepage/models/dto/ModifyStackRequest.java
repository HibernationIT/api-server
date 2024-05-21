package app.hbnationit.apiserver.apis.homepage.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class ModifyStackRequest {
    private String image;
    private String stackType;
    private Integer proficiency;

    @Builder

    public ModifyStackRequest(String image, String stackType, Integer proficiency) {
        this.image = image;
        this.stackType = stackType;
        this.proficiency = proficiency;
    }
}
