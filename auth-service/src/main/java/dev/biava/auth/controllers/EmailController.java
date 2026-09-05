package dev.biava.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.biava.auth.domain.Email.EmailDTO;
import dev.biava.auth.services.EmailService;


@RestController
@RequestMapping("email")
public class EmailController {
    
    @Autowired
    private EmailService emailService;

    @PostMapping("send")
    public ResponseEntity sendTest(@RequestBody EmailDTO emailDTO) {

        emailService.publishEmailEvent(emailDTO);
        
        return ResponseEntity.ok().build();
    }
    


}
