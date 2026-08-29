package dev.biava.auth.configuration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import dev.biava.auth.domain.User.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private static final String secret;

    private static final Algorithm algorithm = Algorithm.HMAC256(secret);
    
    public String generateToken(User user) {
        try {
            String token = JWT.create()
                            .withAudience("auth-api")
                            .withSubject(user.getEmail())
                            .withExpiresAt(this.generateExpirationDate())
                            .sign(algorithm);

            return token;
        }
        catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(algorithm)
                        .withIssuer("auth-api")
                        .build()
                        .verify(token)
                        .getSubject();
        }
        catch (JWTVerificationException e) {
            throw new RuntimeException("Error while validating token", e);
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
