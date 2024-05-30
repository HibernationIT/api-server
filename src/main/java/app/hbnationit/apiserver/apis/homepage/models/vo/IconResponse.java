package app.hbnationit.apiserver.apis.homepage.models.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Getter class IconResponse {
    private String name;
    private String svg;

    @Builder
    public IconResponse(String name, String svg) {
        this.name = name;
        this.svg = svg;
    }

    @Override
    public String toString() {
        return "IconResponse{" +
                "name='" + name + '\'' +
                ", svg='" + svg + '\'' +
                '}';
    }
}
