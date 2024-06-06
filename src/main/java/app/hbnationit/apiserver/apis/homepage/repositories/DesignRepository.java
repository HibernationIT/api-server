package app.hbnationit.apiserver.apis.homepage.repositories;

import app.hbnationit.apiserver.apis.homepage.models.HpDesign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignRepository extends JpaRepository<HpDesign, Long> {
}
