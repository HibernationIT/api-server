package app.hbnationit.apiserver.apis.homepage.models;

import app.hbnationit.apiserver.global.models.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "homepage_stacks")
@NoArgsConstructor
public @Getter @Setter class Stack extends DefaultEntity {
    @Id
    private @Column(name = "name") String name;
    private @Column(name = "image") String image;
    private @Column(name = "stack_type") String stackType;
    private @Column(name = "proficiency") Integer proficiency;

    @Builder
    public Stack(String name, String image, String stackType, Integer proficiency) {
        this.name = name;
        this.image = image;
        this.stackType = stackType;
        this.proficiency = proficiency;
    }

    @Override
    public String toString() {
        return "Stack{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", stackType='" + stackType + '\'' +
                ", proficiency=" + proficiency +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
