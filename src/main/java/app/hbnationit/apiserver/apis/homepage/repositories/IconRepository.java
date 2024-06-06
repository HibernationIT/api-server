package app.hbnationit.apiserver.apis.homepage.repositories;

import app.hbnationit.apiserver.apis.homepage.models.HpIcon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IconRepository extends JpaRepository<HpIcon, String> {
    Page<HpIcon> findByNameLike(Pageable pageable, String name);
    Page<HpIcon> findByViewIsTrue(Pageable pageable);
    Page<HpIcon> findByViewIsTrueAndNameLike(Pageable pageable, String name);
}
