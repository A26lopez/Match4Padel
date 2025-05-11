package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.exceptions.EmptyFieldsException;
import com.match4padel.match4padel_api.exceptions.IncorrectPasswordException;
import com.match4padel.match4padel_api.models.AuthResponse;
import com.match4padel.match4padel_api.models.User;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public AuthResponse login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            throw new EmptyFieldsException();
        }
        User user = userService.getUserByUsername(username);
        if (!passwordEncoder.matches(password, user.getAccountSecurity().getPasswordHash())) {
            throw new IncorrectPasswordException();
        }
        String token = generateSessionToken();

        return new AuthResponse(token, user.getId(), username);
    }

    private String generateSessionToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        random.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
