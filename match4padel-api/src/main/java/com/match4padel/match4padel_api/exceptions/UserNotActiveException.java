package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.User;


public class UserNotActiveException extends RuntimeException{
    public UserNotActiveException(User user) {
        super("El usuario " + user.getAccountInfo().getUsername() + " está inactivo.");
    }
}
