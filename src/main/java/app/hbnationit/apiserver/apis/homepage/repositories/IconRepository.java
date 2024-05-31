package app.hbnationit.apiserver.apis.homepage.repositories;

import app.hbnationit.apiserver.apis.homepage.models.Icon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IconRepository extends JpaRepository<Icon, String> {
    Page<Icon> findByNameLike(Pageable pageable, String name);
    Page<Icon> findByViewIsTrue(Pageable pageable);
    Page<Icon> findByViewIsTrueAndNameLike(Pageable pageable, String name);
}
