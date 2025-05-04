package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.config.ReservationConfig;
import com.match4padel.match4padel_api.exceptions.CourtNotFoundException;
import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.repositories.CourtRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourtService {

    @Autowired
    CourtRepository courtRepository;
   

    public List<Court> getAll() {
        return courtRepository.findAll();
    }

    public Court getById(Long id) {
        return courtRepository.findById(id)
                .orElseThrow(() -> new CourtNotFoundException("id", id.toString()));
    }

    public List<Court> getFreeByDateAndTime(LocalDate date, LocalTime startTime) {
        LocalTime endTime = startTime.plusMinutes(ReservationConfig.MATCH_DURATION_MINUTES);
        if (endTime.isAfter(ReservationConfig.CLOSING_TIME)) {
            return List.of();
        }
        return courtRepository.findFreeCourtsByDateAndTime(date, startTime, endTime);
    }

    public List<Court> getFreeNow() {
        return getFreeByDateAndTime(LocalDate.now(), LocalTime.now());
    }
}
