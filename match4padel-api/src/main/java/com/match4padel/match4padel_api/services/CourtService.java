package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.exceptions.CourtNotFoundException;
import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.enums.CourtStatus;
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

    public List<Court> getFreeNow() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        return courtRepository.findFreeCourtsByDateAndTimeRange(today, now, now.plusMinutes(90));
    }

    public List<Court> getFreeByDateAndTime(LocalDate date, LocalTime time, int duration) {
        return courtRepository.findFreeCourtsByDateAndTimeRange(date, time, time.plusMinutes(duration));
    }

    public boolean isCourtFreeByDateAndTime(Long courtId, LocalDate date, LocalTime startTime, int duration) {
        Court court = getById(courtId);
        List<Court> freeCourts = getFreeByDateAndTime(date, startTime, duration);
        return freeCourts.contains(court);
    }

    public Court changeStatus(Long courtId, CourtStatus status) {
        Court court = getById(courtId);
        court.setCourtStatus(status);
        return courtRepository.save(court);
    }
}
