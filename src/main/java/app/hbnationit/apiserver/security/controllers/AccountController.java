package app.hbnationit.apiserver.security.controllers;

import app.hbnationit.apiserver.security.models.dto.AddAccountRequest;
import app.hbnationit.apiserver.security.models.dto.ModifyAccountRequest;
import app.hbnationit.apiserver.security.services.AccountService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/console/account")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> accountList(Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAccounts(pageable));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> accountDetails(@PathVariable(name = "email") String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAccount(email));
    }

    @PostMapping
    public ResponseEntity<?> accountAdd(@RequestBody AddAccountRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addAccount(body));
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> accountModify(
            @PathVariable(name = "email") String email,
            @RequestBody ModifyAccountRequest body
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.modifyStack(email, body));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> accountRemove(@PathVariable(name = "email") String email) {
        service.removeAccount(email);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
