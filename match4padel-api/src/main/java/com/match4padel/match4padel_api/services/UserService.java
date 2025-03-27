package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.models.AccountInfo;
import com.match4padel.match4padel_api.models.AccountSecurity;
import com.match4padel.match4padel_api.models.ContactInfo;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(User newUser) {
        System.out.println("ContactInfo: " + newUser.getContactInfo());
        System.out.println("AccountInfo: " + newUser.getAccountInfo());
        System.out.println("AccountSecurity: " + newUser.getAccountSecurity());
        User user = new User();
        user.setLevel(newUser.getLevel());
        user.setAccountInfo(newUser.getAccountInfo());
        user.setAccountSecurity(newUser.getAccountSecurity());
        user.setContactInfo(newUser.getContactInfo());
        return userRepository.save(user);
    }

}
