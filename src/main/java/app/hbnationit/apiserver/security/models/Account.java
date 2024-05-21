package app.hbnationit.apiserver.security.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@NoArgsConstructor
public @Getter @Setter class Account {
    @Id
    private @Column(name = "email") String email;
    private @Column(name = "name") String name;
    private @Column(name = "image") String image;

    @Builder
    public Account(String email, String name, String image) {
        this.email = email;
        this.name = name;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
