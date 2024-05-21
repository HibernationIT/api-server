package app.hbnationit.apiserver.security.repositories;

import app.hbnationit.apiserver.security.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
