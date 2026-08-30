package dev.biava.email_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import dev.biava.email_service.domain.EmailDTO;

@Service
public class EmailService {

    @Value("${spring.mail.from.email}")
    private String fromEmail;
    
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(EmailDTO emailDTO) {
        var email = new SimpleMailMessage();

        email.setTo(emailDTO.to());
        email.setSubject(emailDTO.subject());
        email.setText(emailDTO.message());
        email.setFrom(fromEmail);

        javaMailSender.send(email);
    }

}
