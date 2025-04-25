package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.exceptions.CourtNotAvailableException;
import com.match4padel.match4padel_api.exceptions.CourtOccupiedException;
import com.match4padel.match4padel_api.exceptions.ReservationNotFoundException;
import com.match4padel.match4padel_api.exceptions.UserNotActiveException;
import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.models.enums.CourtStatus;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import com.match4padel.match4padel_api.repositories.ReservationRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourtService courtService;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("id", id.toString()));
    }

    public List<Reservation> getByDate(LocalDate date) {
        return reservationRepository.findByDate(date);
    }

    public List<Reservation> getByUserId(Long userId) {
        User user = userService.getById(userId);
        return reservationRepository.findByUser(user);
    }

    public List<Reservation> getByCourtId(Long courtId) {
        Court court = courtService.getById(courtId);
        return reservationRepository.findByCourt(court);
    }

    public List<Reservation> getByUserIdUpcoming(Long userId) {
        User user = userService.getById(userId);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    public List<Reservation> getByUserIdHistory(Long userId) {
        User user = userService.getById(userId);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.COMPLETED)
                .collect(Collectors.toList());
    }

    public Reservation getByCourtDateAndTimeRange(Court court, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return reservationRepository.findByCourtIdAndDateAndTimeRange(court.getId(), date, startTime, endTime)
                .orElseThrow(() -> new ReservationNotFoundException(court.getName(), date, startTime, endTime));
    }

    @Transactional
    public Reservation create(Reservation reservation) {
        LocalDate date = reservation.getDate();
        LocalTime startTime = reservation.getStartTime();
        int duration = reservation.getDuration();
        LocalTime endTime = reservation.getStartTime().plusMinutes(reservation.getDuration());
        reservation.setEndTime(endTime);
        User user = userService.getById(reservation.getUser().getId());
        Court court = courtService.getById(reservation.getCourt().getId());
        if (!user.getAccountInfo().getIsActive()) {
            throw new UserNotActiveException(user);
        }
        reservation.setUser(user);
        if (court.getCourtStatus() == CourtStatus.MAINTENANCE) {
            throw new CourtNotAvailableException(court);
        }
        if (!courtService.isCourtFreeByDateAndTime(court.getId(), date, startTime, duration)) {
            Reservation occupiedReservation = getByCourtDateAndTimeRange(court, date, startTime, endTime);
            throw new CourtOccupiedException(occupiedReservation);
        }
        reservation.setCourt(court);

        return reservationRepository.save(reservation);
    }

}
