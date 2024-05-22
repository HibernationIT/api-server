package app.hbnationit.apiserver.utils;

import io.netty.handler.codec.MessageAggregationException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class MailUtil {
    protected Log logger = LogFactory.getLog(getClass());
    private final Session mailSession;

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    public MailUtil() {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        this.mailSession = Session.getDefaultInstance(props);
    }

    public void send(String sender, String[] receivers, String subject, String body) {
        send(sender, sender, receivers, subject, body);
    }

    public void send(String sender, String senderName, String[] receivers, String subject, String body) {
        MimeMessage msg = new MimeMessage(mailSession);
        Transport transport;

        try {
            msg.setFrom(new InternetAddress(sender, senderName));
            msg.setRecipients(Message.RecipientType.TO, String.join(",", receivers));
            msg.setSubject(subject);
            msg.setContent(body,"text/html");

            transport = mailSession.getTransport();
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new MessageAggregationException();
        }

        try {
            transport.connect(host, username, password);
            transport.sendMessage(msg, msg.getAllRecipients());

            logger.debug("mail send complete.");
        } catch (Exception ignore) {
            throw new MessageAggregationException();
        } finally {
            try {
                transport.close();
            } catch (MessagingException e) {
                throw new MessageAggregationException();
            }
        }
    }
}
