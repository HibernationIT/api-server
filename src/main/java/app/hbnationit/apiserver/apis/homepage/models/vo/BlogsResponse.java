package app.hbnationit.apiserver.apis.homepage.models.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
public @Getter class BlogsResponse {
    private Long id;
    private String name;
    private String description;
    private Set<String> tags;
    private Boolean view;
    private String image;
    private LocalDateTime createdAt;

    @Builder
    public BlogsResponse(Long id, String name, String description, Set<String> tags, Boolean view, String image, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.view = view;
        this.image = image;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "BlogsResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", view=" + view +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
