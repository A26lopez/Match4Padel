package com.match4padel.match4padel_api.controllers;

import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public List<User> search(@RequestParam String q) {
        return userService.search(q);
    }
   
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
            User createdUser = userService.create(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
