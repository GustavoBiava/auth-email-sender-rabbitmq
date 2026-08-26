package dev.biava.auth.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import dev.biava.auth.domain.User.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    
    public UserDetails findByEmail(String email);
}
