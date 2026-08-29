package dev.biava.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.biava.auth.domain.User.User;
import dev.biava.auth.domain.User.UserLoginDTO;
import dev.biava.auth.domain.User.UserRegisterDTO;
import dev.biava.auth.repositories.UserRepository;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userLoginDTO.email(), userLoginDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated UserRegisterDTO userRegisterDTO) {
        if (this.userRepository.findByEmail(userRegisterDTO.email()) != null) return ResponseEntity.badRequest().build();
        
        String encryptedPassword = new BCryptPasswordEncoder().encode(userRegisterDTO.password());
        var user = new User(userRegisterDTO.username(), userRegisterDTO.email(), encryptedPassword, userRegisterDTO.role());
        
        this.userRepository.save(user);
        
        return ResponseEntity.ok().build();
    }
    
    
}
