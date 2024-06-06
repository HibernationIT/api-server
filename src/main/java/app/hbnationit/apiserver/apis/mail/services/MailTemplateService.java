package app.hbnationit.apiserver.apis.mail.services;

import app.hbnationit.apiserver.apis.mail.models.MailTemplate;
import app.hbnationit.apiserver.apis.mail.models.dto.AddMailTemplateRequest;
import app.hbnationit.apiserver.apis.mail.models.dto.ModifyMailTemplateRequest;
import app.hbnationit.apiserver.apis.mail.repositories.MailTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MailTemplateService {
    private final MailTemplateRepository repository;

    public MailTemplateService(MailTemplateRepository repository) {
        this.repository = repository;
    }

    public Page<MailTemplate> findMailTemplates(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public MailTemplate findMailTemplate(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found MailTemplate"));
    }

    @Transactional
    public MailTemplate addMailTemplate(AddMailTemplateRequest dto) {
        String variables = String.join(",", dto.getVariables());

        MailTemplate dao = MailTemplate.builder()
                .name(dto.getName())
                .content(dto.getContent())
                .variables(variables)
                .build();

        return repository.save(dao);
    }

    @Transactional
    public MailTemplate modifyMailTemplate(Long id, ModifyMailTemplateRequest dto) {
        MailTemplate dao = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found MailTemplate"));

        String variables = String.join(",", dto.getVariables());

        dao.setName(dto.getName());
        dao.setContent(dto.getContent());
        dao.setVariables(variables);
        return dao;
    }

    @Transactional
    public void removeMailTemplate(Long id) {
        repository.deleteById(id);
    }
}
