package app.hbnationit.apiserver.apis.homepage.models;

import app.hbnationit.apiserver.global.models.DefaultEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "homepage_projects")
@NoArgsConstructor
public @Getter @Setter class Project extends DefaultEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "id") Long id;
    private @Column(name = "name") String name;
    private @Column(name = "link") String link;
    private @Column(name = "description") String description;
    private @Column(name = "stacks") String stacks;
    private @Column(name = "view") Boolean view;
    private @Column(name = "image") String image;
    private @Column(name = "content", length = 1024*32) String content;

    @Builder
    public Project(Long id, String name, String link, String description, String stacks, Boolean view, String image, String content) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.description = description;
        this.stacks = stacks;
        this.view = view;
        this.image = image;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", stacks=" + stacks +
                ", view=" + view +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy=" + createdBy +
                ", updatedAt=" + updatedAt +
                ", updatedBy=" + updatedBy +
                '}';
    }
}
