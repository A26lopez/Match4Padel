package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Match;
import com.match4padel.match4padel_api.models.User;

public class AlreadyJoinedMatchException extends RuntimeException {

    public AlreadyJoinedMatchException(Match match, User user) {
        super("El usuario " + user.getId() + " ya está en el partido " + match.getId() + ".");
    }
}
