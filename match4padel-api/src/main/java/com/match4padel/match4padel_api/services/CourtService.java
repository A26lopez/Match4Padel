package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.exceptions.UserNotFoundException;
import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.enums.CourtStatus;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import com.match4padel.match4padel_api.repositories.CourtRepository;
import com.match4padel.match4padel_api.repositories.ReservationRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourtService {

    @Autowired
    CourtRepository courtRepository;

    @Autowired
    ReservationRepository reservationRepository;

    public List<Court> getAll() {
        return courtRepository.findAll();
    }

    public Court getById(Long id) {
        return courtRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("id", id.toString()));
    }

    public List<Court> getFreeNow() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        return courtRepository.findFreeCourtsByDateAndTime(today, now);
    }

    public List<Court> getFreeByDateAndTime(LocalDate date, LocalTime time) {
        return courtRepository.findFreeCourtsByDateAndTime(date, time);
    }

    public boolean isCourtFree(Long courtId, LocalDate date, LocalTime startTime) {
        Court court = getById(courtId);
        List<Court> freeCourts = getFreeByDateAndTime(date, startTime);
        return freeCourts.contains(court);
    }

    public Court changeStatus(Long courtId, CourtStatus status) {
        Court court = getById(courtId);
        court.setCourtStatus(status);
        return courtRepository.save(court);
    }
}
