package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.exceptions.DuplicateException;
import com.match4padel.match4padel_api.exceptions.UserNotFoundException;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.repositories.UserRepository;
import java.util.List;
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
                .orElseThrow(() -> new UserNotFoundException("id", id.toString()));
    }

    public User getByUsername(String username) {
        return userRepository.findByAccountInfoUsername(username)
                .orElseThrow(() -> new UserNotFoundException("nombre de usuario", username));
    }

    public User getByEmail(String email) {
        return userRepository.findByContactInfoEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email", email));
    }

    public List<User> search(String q) {
        return userRepository.search(q);
    }

    @Transactional
    public User create(User user) {
        String username = user.getAccountInfo().getUsername();
        validateFieldAvailability("username", username);

        String email = user.getContactInfo().getEmail();
        validateFieldAvailability("email", email);

        String phoneNumber = user.getContactInfo().getPhoneNumber();
        validateFieldAvailability("phoneNumber", phoneNumber);

        String nif = user.getContactInfo().getNif();
        validateFieldAvailability("nif", nif);

        String password = user.getAccountSecurity().getPassword();
        String passwordHash = passwordEncoder.encode(password);
        user.getAccountSecurity().setPasswordHash(passwordHash);
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User updatedUser) {
        User existingUser = getById(id);

        if (hasFieldChanged(existingUser.getAccountInfo().getUsername(), updatedUser.getAccountInfo().getUsername())) {
            String username = updatedUser.getAccountInfo().getUsername();
            validateFieldAvailability("username", username);
            existingUser.getAccountInfo().setUsername(username);
        }

        if (hasFieldChanged(existingUser.getContactInfo().getEmail(), updatedUser.getContactInfo().getEmail())) {
            String email = updatedUser.getContactInfo().getEmail();
            validateFieldAvailability("email", email);
            existingUser.getContactInfo().setEmail(email);
        }

        if (hasFieldChanged(existingUser.getContactInfo().getPhoneNumber(), updatedUser.getContactInfo().getPhoneNumber())) {
            String phoneNumber = updatedUser.getContactInfo().getPhoneNumber();
            validateFieldAvailability("phoneNumber", phoneNumber);
            existingUser.getContactInfo().setPhoneNumber(phoneNumber);
        }

        if (hasFieldChanged(existingUser.getContactInfo().getNif(), updatedUser.getContactInfo().getNif())) {
            String nif = updatedUser.getContactInfo().getNif();
            validateFieldAvailability("nif", nif);
            existingUser.getContactInfo().setNif(nif);
        }

        if (!passwordEncoder.matches(updatedUser.getAccountSecurity().getPassword(), existingUser.getAccountSecurity().getPasswordHash())) {
            String password = updatedUser.getAccountSecurity().getPassword();
            String passwordHash = passwordEncoder.encode(password);
            existingUser.getAccountSecurity().setPasswordHash(passwordHash);
        }

        existingUser.getAccountInfo().setProfilePictureUrl(updatedUser.getAccountInfo().getProfilePictureUrl());
        existingUser.getContactInfo().setFirstName(updatedUser.getContactInfo().getFirstName());
        existingUser.getContactInfo().setLastName(updatedUser.getContactInfo().getLastName());
        existingUser.getContactInfo().setBirthDate(updatedUser.getContactInfo().getBirthDate());
        existingUser.getContactInfo().setAddress(updatedUser.getContactInfo().getAddress());
        existingUser.getContactInfo().setCity(updatedUser.getContactInfo().getCity());
        existingUser.getContactInfo().setPostalCode(updatedUser.getContactInfo().getPostalCode());
        existingUser.getContactInfo().setCountry(updatedUser.getContactInfo().getCountry());
        return userRepository.save(existingUser);
    }

    private boolean hasFieldChanged(String oldValue, String newValue) {
        if (oldValue == null) {
            return newValue != null;
        }
        return !oldValue.equalsIgnoreCase(newValue);
    }

    private void validateFieldAvailability(String field, String value) {
        switch (field) {
            case "username":
                if (userRepository.existsByAccountInfoUsername(value)) {
                    throw new DuplicateException("nombre de usuario", value);
                }
                break;
            case "email":
                if (userRepository.existsByContactInfoEmail(value)) {
                    throw new DuplicateException("email", value);
                }
                break;
            case "phoneNumber":
                if (userRepository.existsByContactInfoPhoneNumber(value)) {
                    throw new DuplicateException("número de teléfono", value);
                }
                break;
            case "nif":
                if (userRepository.existsByContactInfoNif(value)) {
                    throw new DuplicateException("NIF", value);
                }
                break;
            default:
                throw new IllegalArgumentException("Campo no válido: " + field);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("id", id.toString());
        }
        userRepository.deleteById(id);
    }

}
