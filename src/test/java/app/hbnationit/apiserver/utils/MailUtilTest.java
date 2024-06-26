package app.hbnationit.apiserver.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class MailUtilTest {
    private @Autowired MailUtil mailUtil;

    @Test
    void send() throws Exception {
        String sender = "no-reply@hbnation.app";
        String senderName = "HibernationIT";
        String[] receiver = new String[]{"hbnation.it@gmail.com"};
        String subject = "TEST";
        String body = "test";

        mailUtil.send(sender, senderName, receiver, subject, body);
    }
}