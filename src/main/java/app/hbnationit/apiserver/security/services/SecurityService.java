package app.hbnationit.apiserver.security.services;

import app.hbnationit.apiserver.security.models.Account;
import app.hbnationit.apiserver.security.models.vo.TokenResponse;
import app.hbnationit.apiserver.security.repositories.AccountRepository;
import app.hbnationit.apiserver.security.utils.JwtUtil;
import app.hbnationit.apiserver.utils.MailUtil;
import app.hbnationit.apiserver.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SecurityService {
    protected final Log logger = LogFactory.getLog(getClass());
    private final AccountRepository repository;
    private final MailUtil mailUtil;
    private final JwtUtil jwtUtil;
    private final RedisUtil redis;

    @Value("${spring.security.oauth2.jwt.refresh-expired}")
    private long refreshExp;

    public ResponseEntity<?> accessCodeSend(String username, String ip) {
        Account account = repository.findById(username).orElseThrow(() ->
                new UsernameNotFoundException("Invalid Username")
        );

        String sender = "no-reply@hbnation.app";
        String[] receivers = new String[]{account.getEmail()};
        String subject = "[HibernationIT] 인증번호 발송";
        String code = randomCode();

        redis.template().set(accessCodeKey(username, ip), code, 5L, TimeUnit.MINUTES);

        mailUtil.send(sender, receivers, subject, code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<TokenResponse> login(String username, String ip, String code) {
        String value = redis.template().getAndDelete(accessCodeKey(username, ip));
        if (value == null || !value.equals(code)) {
            throw new UsernameNotFoundException("Invalid Username or Code");
        }

        return getTokenResponse(username, ip);
    }

    public ResponseEntity<TokenResponse> tokenRefresh(String username, String ip, String token) {
        String value = redis.template().getAndDelete(refreshTokenKey(username, ip));
        if (value == null || !value.equals(token)) {
            throw new AuthenticationCredentialsNotFoundException("Invalid Token");
        }

        return getTokenResponse(username, ip);
    }

    private ResponseEntity<TokenResponse> getTokenResponse(String username, String ip) {
        String ati = UUID.randomUUID().toString();
        String rti = UUID.randomUUID().toString();
        String accessToken = jwtUtil.generateAccessJwt(ati, username);
        String refreshToken = jwtUtil.generateRefreshJwt(rti, username, ati);
        redis.template().set(refreshTokenKey(username, ip), refreshToken, refreshExp, TimeUnit.MILLISECONDS);

        TokenResponse response = new TokenResponse(username, accessToken, refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private String accessCodeKey(String username, String ip) {
        return String.format("accessCode:%s:%s", username, ip);
    }

    private String refreshTokenKey(String username, String ip) {
        return String.format("refreshToken:%s:%s", username, ip);
    }

    private String randomCode() {
        StringBuilder sb = new StringBuilder();

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
