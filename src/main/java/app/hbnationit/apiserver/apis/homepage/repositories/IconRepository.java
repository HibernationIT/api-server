package app.hbnationit.apiserver.apis.homepage.repositories;

import app.hbnationit.apiserver.apis.homepage.models.Icon;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IconRepository extends JpaRepository<Icon, String> {
    Page<Icon> findByNameLike(String name);
}
