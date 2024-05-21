package app.hbnationit.apiserver.utils;

import io.netty.handler.codec.MessageAggregationException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class MailUtil {
    protected Log logger = LogFactory.getLog(getClass());
    private final JavaMailSender mailSender;

    public MailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String sender, String[] receivers, String subject, String body) {
        send(sender, sender, receivers, subject, body);
    }

    public void send(String sender, String senderName, String[] receivers, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper messageBuilder = new MimeMessageHelper(message, false, "UTF-8");
            messageBuilder.setFrom(sender, senderName);
            messageBuilder.setTo(receivers);
            messageBuilder.setSubject(subject);
            messageBuilder.setText(body);
        } catch (MessagingException | UnsupportedEncodingException ignore) {
            throw new MessageAggregationException();
        }

        mailSender.send(message);
        logger.debug("mail send complete.");
    }
}
