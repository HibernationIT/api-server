package app.hbnationit.apiserver.apis.homepage.models;

import app.hbnationit.apiserver.global.models.DefaultEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "homepage_designs")
@NoArgsConstructor
public @Getter @Setter class Design extends DefaultEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "id") Long id;
    private @Column(name = "name") String name;
    private @Column(name = "link") String link;
    private @Column(name = "description") String description;
    private @Column(name = "view") Boolean view;
    private @Column(name = "image") String image;
    private @Column(name = "designs", length = 1024*32) String designs;

    @Builder
    public Design(Long id, String name, String link, String description, Boolean view, String image, String designs) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.description = description;
        this.view = view;
        this.image = image;
        this.designs = designs;
    }

    @Override
    public String toString() {
        return "Design{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", view=" + view +
                ", image='" + image + '\'' +
                ", designs='" + designs + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
