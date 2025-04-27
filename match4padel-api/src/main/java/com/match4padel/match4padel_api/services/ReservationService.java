package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.config.ReservationConfig;
import com.match4padel.match4padel_api.exceptions.CourtOccupiedException;
import com.match4padel.match4padel_api.exceptions.ReservationNotFoundException;
import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import com.match4padel.match4padel_api.repositories.ReservationRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CourtService courtService;

    @Autowired
    UserService userService;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("id", id.toString()));
    }

    public List<Reservation> getByCourtId(Long courtId) {
        Court court = courtService.getById(courtId);
        return reservationRepository.findByCourt(court);
    }

    public List<Reservation> getByUserId(Long usertId) {
        User user = userService.getById(usertId);
        return reservationRepository.findByUser(user);
    }

    public List<Reservation> getByUserIdAndStatus(Long userId, ReservationStatus status) {
        User user = userService.getById(userId);
        return reservationRepository.findByUserAndStatus(user, status);
    }

    public List<Reservation> getByDate(LocalDate date) {
        return reservationRepository.findByDate(date);
    }

    public Reservation getByCourtIdDateAndTime(Long courtId, LocalDate date, LocalTime startTime) {
        Court court = courtService.getById(courtId);
        LocalTime endTime = startTime.plusMinutes(ReservationConfig.DURATION);
        return reservationRepository.findByCourtAndDateAndStartTimeAndEndTime(court, date, startTime, endTime)
                .orElseThrow(() -> new ReservationNotFoundException(court.getName(), date, startTime, endTime));
    }
    
    public List<Reservation> getConflictingReservations(Long courtId, LocalDate date, LocalTime startTime){
        Court court = courtService.getById(courtId);
        LocalTime endTime = startTime.plusMinutes(ReservationConfig.DURATION);
        return reservationRepository.findConflictingReservations(court, date, startTime, endTime);
    }

    public List<Court> getFreeCourtsByDateAndTime(LocalDate date, LocalTime startTime) {
        LocalTime endTime = startTime.plusMinutes(ReservationConfig.DURATION);
        return reservationRepository.findFreeCourtsByDateAndTime(date, startTime, endTime);
    }

    public Map<LocalTime, List<Long>> getFreeHoursByDateAndDuration(LocalDate date) {
        Map<LocalTime, List<Long>> availableHours = new HashMap<>();

        for (int hour = 9; hour <= 21; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                LocalTime startTime = LocalTime.of(hour, minute);

                List<Court> freeCourts = getFreeCourtsByDateAndTime(date, startTime);

                if (!freeCourts.isEmpty()) {
                    List<Long> courtIds = freeCourts.stream()
                            .map(Court::getId)
                            .collect(Collectors.toList());
                    availableHours.put(startTime, courtIds);
                }
            }
        }
        return availableHours;
    }

    @Transactional
    public Reservation create(Reservation reservation) {

        LocalDate date = reservation.getDate();
        LocalTime startTime = reservation.getStartTime();
        LocalTime endTime = startTime.plusMinutes(reservation.getDuration());
        reservation.setEndTime(endTime);

        User user = userService.getById(reservation.getUser().getId());
        Court court = courtService.getById(reservation.getCourt().getId());

        reservation.setUser(user);
        reservation.setCourt(court);
        

        if (!getFreeCourtsByDateAndTime(date, startTime).contains(court)) {
            List<Reservation> conflictingReservations = getConflictingReservations(court.getId(), date, startTime);
            throw new CourtOccupiedException(conflictingReservations);
        }
        return reservationRepository.save(reservation);
    }

}
