package app.hbnationit.apiserver.apis.mail.controllers;

import app.hbnationit.apiserver.apis.mail.models.dto.AddMailTemplateRequest;
import app.hbnationit.apiserver.apis.mail.models.dto.ModifyMailTemplateRequest;
import app.hbnationit.apiserver.apis.mail.services.MailTemplateService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/console/mail/template")
public class MailTemplateController {
    private final MailTemplateService service;

    public MailTemplateController(MailTemplateService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> mailTemplateList(Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findMailTemplates(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mailTemplateDetails(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findMailTemplate(id));
    }

    @PostMapping
    public ResponseEntity<?> mailTemplateAdd(@RequestBody AddMailTemplateRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addMailTemplate(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> mailTemplateModify(
            @PathVariable(name = "id") Long id,
            @RequestBody ModifyMailTemplateRequest body
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.modifyMailTemplate(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> mailTemplateRemove(@PathVariable(name = "id") Long id) {
        service.removeMailTemplate(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
