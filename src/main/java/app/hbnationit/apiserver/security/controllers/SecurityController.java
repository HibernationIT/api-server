package app.hbnationit.apiserver.security.controllers;

import app.hbnationit.apiserver.security.models.dto.AccessCodeRequest;
import app.hbnationit.apiserver.security.models.dto.LoginRequest;
import app.hbnationit.apiserver.security.models.dto.TokenRefreshRequest;
import app.hbnationit.apiserver.security.models.vo.TokenResponse;
import app.hbnationit.apiserver.security.services.SecurityService;
import jakarta.servlet.ServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class SecurityController {
    private final SecurityService service;

    public SecurityController(SecurityService service) {
        this.service = service;
    }

    @PostMapping("/code")
    public ResponseEntity<?> accessCode(ServletRequest request, @RequestBody AccessCodeRequest usernameRequest) {
        return service.accessCodeSend(usernameRequest.getUsername(), request.getRemoteAddr());
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(ServletRequest request, @RequestBody LoginRequest loginRequest) {
        return service.login(loginRequest.getUsername(), request.getRemoteAddr(), loginRequest.getCode());
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> tokenRefresh(ServletRequest request, @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return service.tokenRefresh(tokenRefreshRequest.getUsername(), request.getRemoteAddr(), tokenRefreshRequest.getRefreshToken());
    }

}
