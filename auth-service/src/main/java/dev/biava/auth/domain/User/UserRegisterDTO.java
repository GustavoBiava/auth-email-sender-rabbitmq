package dev.biava.auth.domain.User;

import dev.biava.auth.enums.UserRole;

public record UserRegisterDTO(String username, String email, String password, UserRole role) {}
