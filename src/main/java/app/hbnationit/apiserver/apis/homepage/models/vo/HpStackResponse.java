package app.hbnationit.apiserver.apis.homepage.models.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Getter class HpStackResponse {
    private String name;
    private String image;
    private String stackType;
    private Integer proficiency;

    @Builder
    public HpStackResponse(String name, String image, String stackType, Integer proficiency) {
        this.name = name;
        this.image = image;
        this.stackType = stackType;
        this.proficiency = proficiency;
    }

    @Override
    public String toString() {
        return "StackResponse{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", stackType='" + stackType + '\'' +
                ", proficiency=" + proficiency +
                '}';
    }
}
