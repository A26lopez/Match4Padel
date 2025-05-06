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
   

    public List<Court> getAllCourts() {
        return courtRepository.findAll();
    }

    public Court getCourtById(Long id) {
        return courtRepository.findById(id)
                .orElseThrow(() -> new CourtNotFoundException("id", id.toString()));
    }

    public List<Court> getFreeCourtsByDateAndTime(LocalDate date, LocalTime startTime) {
        LocalTime endTime = startTime.plusMinutes(ReservationConfig.MATCH_DURATION_MINUTES);
        if (endTime.isAfter(ReservationConfig.CLOSING_TIME)) {
            return List.of();
        }
        return courtRepository.findFreeCourtsByDateAndTime(date, startTime, endTime);
    }

    public List<Court> getFreeCourtsNow() {
        return getFreeCourtsByDateAndTime(LocalDate.now(), LocalTime.now());
    }
}
