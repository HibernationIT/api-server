package app.hbnationit.apiserver.apis.homepage.repositories;

import app.hbnationit.apiserver.apis.homepage.models.HpBlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<HpBlog, Long> {

}
