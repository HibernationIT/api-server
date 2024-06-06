package app.hbnationit.apiserver.apis.homepage.models.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public @Getter class HpIconResponse {
    private String name;
    private String svg;
    private LocalDateTime createdAt;

    @Builder
    public HpIconResponse(String name, String svg, LocalDateTime createdAt) {
        this.name = name;
        this.svg = svg;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "IconResponse{" +
                "name='" + name + '\'' +
                ", svg='" + svg + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
