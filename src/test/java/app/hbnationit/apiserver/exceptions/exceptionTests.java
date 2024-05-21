package app.hbnationit.apiserver.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
public class exceptionTests {
    @Test
    void test1() {
        try {
            throw new UsernameNotFoundException("not found");
        } catch (Exception e) {
            System.out.println(e.getStackTrace()[0]);
        }
    }
}
