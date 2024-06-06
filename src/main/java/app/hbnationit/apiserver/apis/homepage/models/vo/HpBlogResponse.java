package app.hbnationit.apiserver.apis.homepage.models.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
public @Getter class HpBlogResponse {
    private Long id;
    private String name;
    private String description;
    private Set<String> tags;
    private String image;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public HpBlogResponse(Long id, String name, String description, Set<String> tags, String image, String content, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.image = image;
        this.content = content;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "BlogResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
