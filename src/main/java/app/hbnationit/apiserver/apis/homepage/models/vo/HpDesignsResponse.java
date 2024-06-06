package app.hbnationit.apiserver.apis.homepage.models.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
public @Getter @Setter class HpDesignsResponse {
    private Long id;
    private String name;
    private String link;
    private String description;
    private String image;
    private LocalDateTime createdAt;

    @Builder
    public HpDesignsResponse(Long id, String name, String link, String description, String image, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.description = description;
        this.image = image;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "DesignsResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
