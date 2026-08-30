package dev.biava.email_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.biava.email_service.domain.EmailDTO;
import dev.biava.email_service.services.EmailService;


@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("send")
    public ResponseEntity send(@RequestBody EmailDTO emailDTO) {
        emailService.sendEmail(emailDTO);
        return ResponseEntity.ok().build();
    }
    
}
