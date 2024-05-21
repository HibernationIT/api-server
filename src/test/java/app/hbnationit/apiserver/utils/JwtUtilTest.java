package app.hbnationit.apiserver.utils;

import app.hbnationit.apiserver.security.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class JwtUtilTest {
    protected Log logger = LogFactory.getLog(getClass());
    private @Autowired JwtUtil jwtUtil;


    @Test
    void tokenTests() {
        Claims body = Jwts.claims()
                .setExpiration(new Date(System.currentTimeMillis() + 1000));

        String token = jwtUtil.generateJwt(body, "tester");
        logger.info(token);

        Claims claims = jwtUtil.parsingJwt(token);
        logger.info(claims);
    }
}