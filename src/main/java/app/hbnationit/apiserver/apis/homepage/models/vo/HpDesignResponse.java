package app.hbnationit.apiserver.apis.homepage.models.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
public @Getter @Setter class HpDesignResponse {
    private Long id;
    private String name;
    private String link;
    private String description;
    private String image;
    private Set<String> designs;
    private LocalDateTime createdAt;

    @Builder
    public HpDesignResponse(Long id, String name, String link, String description, String image, Set<String> designs, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.description = description;
        this.image = image;
        this.designs = designs;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "DesignResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", designs=" + designs +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
