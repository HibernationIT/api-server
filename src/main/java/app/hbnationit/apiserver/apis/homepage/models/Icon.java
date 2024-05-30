package app.hbnationit.apiserver.apis.homepage.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "homepage_icons")
@NoArgsConstructor
public @Getter @Setter class Icon {
    @Id
    private @Column(name = "name") String name;
    private @Column(name = "svg") String svg;
    private @Column(name = "view") Boolean view;

    @Builder
    public Icon(String name, String svg, Boolean view) {
        this.name = name;
        this.svg = svg;
        this.view = view;
    }

    @Override
    public String toString() {
        return "Icon{" +
                "name='" + name + '\'' +
                ", svg='" + svg + '\'' +
                ", view=" + view +
                '}';
    }
}
