package app.hbnationit.apiserver.apis.mail.repositories;

import app.hbnationit.apiserver.apis.mail.models.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {
}
