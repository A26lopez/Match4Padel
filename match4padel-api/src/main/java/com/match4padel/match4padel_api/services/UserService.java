package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.exceptions.DuplicateFieldException;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    public List<User> getByStatus(boolean isActive) {
        return userRepository.findByAccountInfoIsActive(isActive);
    }

    public List<User> search(String q) {
        return userRepository.search(q);
    }

    public void changeStatus(Long id) {

    }

    @Transactional
    public User create(User user) {
        checkIfUserExists(user);
        String password = user.getAccountSecurity().getPassword();
        String passwordHash = passwordEncoder.encode(password);
        user.getAccountSecurity().setPasswordHash(passwordHash);
        return userRepository.save(user);
    }

    private void checkIfUserExists(User user) {
        Map<String, String> duplicateFields = new HashMap<>();
        String username = user.getAccountInfo().getUsername();
        String email = user.getContactInfo().getEmail();
        String phoneNumber = user.getContactInfo().getPhoneNumber();
        String dni = user.getContactInfo().getDni();
        if (userRepository.existsByAccountInfoUsername(username)) {
            duplicateFields.put("username", "El usuario " + username + " ya está registrado.");
        }
        if (userRepository.existsByContactInfoEmail(email)) {
            duplicateFields.put("email", "El email " + email + " ya está registrado.");
        }
        if (userRepository.existsByContactInfoPhoneNumber(phoneNumber)) {
            duplicateFields.put("phoneNumber", "El número de teléfono " + phoneNumber + " ya está registrado.");
        }
        if (userRepository.existsByContactInfoDni(dni)) {
            duplicateFields.put("dni", "El DNI " + dni + " ya está registrado.");
        }
        if (!duplicateFields.isEmpty()) {
            throw new DuplicateFieldException(duplicateFields);
        }
    }
}
