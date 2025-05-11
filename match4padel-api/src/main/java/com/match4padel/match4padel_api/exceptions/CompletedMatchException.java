package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Match;

public class CompletedMatchException extends RuntimeException {

    public CompletedMatchException(Match match) {
        super("El partido " + match.getId() + " está completo.");
    }
}
