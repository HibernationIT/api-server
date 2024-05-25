package app.hbnationit.apiserver.apis.homepage.repositories;

import app.hbnationit.apiserver.apis.homepage.models.Design;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignRepository extends JpaRepository<Design, Long> {
}
