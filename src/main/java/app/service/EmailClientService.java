package app.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailClientService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("mailspring794@gmail.com");

        mailSender.send(message);
        System.out.println("mail sent");
    }

    public void sendEmailWithAttachment(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper msgHelper = new MimeMessageHelper(message);

        msgHelper.setTo(to);
        msgHelper.setSubject(subject);
        msgHelper.setText(body, true);
        msgHelper.setFrom("mailspring794@gmail.com");
        mailSender.send(message);
        System.out.println("mail sent");
    }
}
