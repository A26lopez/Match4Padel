package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Match;
import com.match4padel.match4padel_api.models.User;

public class PlayerNotInMatchException extends RuntimeException {

    public PlayerNotInMatchException(User player) {
        super("El usuario " + player.getAccountInfo().getUsername() + " no está en este partido.");
    }
}
