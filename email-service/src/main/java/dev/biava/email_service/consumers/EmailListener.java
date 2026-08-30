package dev.biava.email_service.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import dev.biava.email_service.domain.EmailDTO;
import dev.biava.email_service.services.EmailService;

@Component
public class EmailListener {
    
    private EmailService emailService;

    @RabbitListener(queues = "${app.rabbitmq.queue.email}")
    public void consumeEmails(EmailDTO emailDTO) {
        System.out.println("Iniciando consumo de e-mail...");
        
        try {
            emailService.sendEmail(emailDTO);
            System.out.println("Email consumido...");
        }
        catch (Exception e) {
            System.out.println("Erro ao consumir e-mail: " + e.getMessage());
        }
        finally {
            System.out.println("Finalizando consumo de e-mail...");
            System.out.println();
        }
    }
}
