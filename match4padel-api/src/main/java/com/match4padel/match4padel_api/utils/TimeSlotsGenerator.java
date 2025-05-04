package com.match4padel.match4padel_api.utils;

import com.match4padel.match4padel_api.config.ReservationConfig;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotsGenerator {
    
    public static final List<LocalTime> VALID_TIME_SLOTS = generateTimeSlots();

    public static List<LocalTime> generateTimeSlots() {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime slot = ReservationConfig.OPENING_TIME;
        LocalTime latestStartTime = ReservationConfig.CLOSING_TIME.minusMinutes(ReservationConfig.MATCH_DURATION_MINUTES);

        while (!slot.isAfter(latestStartTime)) {
            slots.add(slot);
            slot = slot.plusMinutes(ReservationConfig.RESERVATION_TIME_SLOT);
        }

        return slots;
    }

}
