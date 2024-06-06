package app.hbnationit.apiserver.apis.mail.models;


import app.hbnationit.apiserver.global.models.DefaultEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mail_template")
@NoArgsConstructor
public @Getter @Setter class MailTemplate extends DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "id") Long id;
    private @Column(name = "name") String name;
    private @Column(name = "content", length = 1024*32) String content;
    private @Column(name = "variables") String variables;

    @Builder
    public MailTemplate(Long id, String name, String content, String variables) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "MailTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", variables='" + variables + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
