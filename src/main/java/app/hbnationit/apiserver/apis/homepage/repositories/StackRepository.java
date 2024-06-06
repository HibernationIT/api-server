package app.hbnationit.apiserver.apis.homepage.repositories;

import app.hbnationit.apiserver.apis.homepage.models.HpStack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StackRepository extends JpaRepository<HpStack, String> {
    Page<HpStack> findByStackType(Pageable pageable, String stackType);
}
