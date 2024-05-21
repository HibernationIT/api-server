package app.hbnationit.apiserver.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key secretKey;

    @Value("${spring.security.oauth2.jwt.access-expired}")
    private long accessExp;
    @Value("${spring.security.oauth2.jwt.refresh-expired}")
    private long refreshExp;

    public JwtUtil(@Value("${spring.security.oauth2.jwt.secret.key}") String secretKey) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateAccessJwt(String id, String username) {
        Claims claims = Jwts.claims()
                .setId(id)
                .setExpiration(new Date(System.currentTimeMillis() + accessExp));
        return generateJwt(claims, username);
    }
    public String generateRefreshJwt(String id, String username, String ati) {
        Claims claims = Jwts.claims()
                .setId(id)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExp));
        claims.put("ati", ati);
        return generateJwt(claims, username);
    }

    public String generateJwt(Claims claims, String username) {
        claims
                .setSubject(username)
                .setIssuer("HibernationIT")
                .setIssuedAt(new Date(System.currentTimeMillis()));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parsingJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody();
    }
}
