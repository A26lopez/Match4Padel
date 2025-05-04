package com.match4padel.match4padel_api.config;

import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class ReservationConfig {

    public static final int MATCH_DURATION_MINUTES = 90;
    public static final int RESERVATION_TIME_SLOT = 30;
    public static final LocalTime OPENING_TIME = LocalTime.of(9, 0);
    public static final LocalTime CLOSING_TIME = LocalTime.of(22, 0);

}
