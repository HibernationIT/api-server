package app.hbnationit.apiserver.apis.homepage.models;

import app.hbnationit.apiserver.global.models.DefaultEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "homepage_blogs")
@NoArgsConstructor
public @Getter @Setter class HpBlog extends DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "id") Long id;
    private @Column(name = "name") String name;
    private @Column(name = "description") String description;
    private @Column(name = "tags") String tags;
    private @Column(name = "view") Boolean view;
    private @Column(name = "image") String image;
    private @Column(name = "content", length = 1024*32) String content;

    @Builder
    public HpBlog(Long id, String name, String description, String tags, Boolean view, String image, String content) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.view = view;
        this.image = image;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                ", view=" + view +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
