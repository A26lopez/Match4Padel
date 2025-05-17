package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.config.ReservationConfig;
import com.match4padel.match4padel_api.exceptions.ClubClosedException;
import com.match4padel.match4padel_api.exceptions.CourtOccupiedException;
import com.match4padel.match4padel_api.exceptions.MatchNotFoundException;
import com.match4padel.match4padel_api.exceptions.PastDateTimeException;
import com.match4padel.match4padel_api.exceptions.ReservationAlreadyCompletedException;
import com.match4padel.match4padel_api.exceptions.ReservationNotFoundException;
import com.match4padel.match4padel_api.exceptions.ReservationTimeNotValidException;
import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.Match;
import com.match4padel.match4padel_api.models.Payment;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.models.enums.MatchStatus;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import com.match4padel.match4padel_api.repositories.MatchRepository;
import com.match4padel.match4padel_api.repositories.ReservationRepository;
import com.match4padel.match4padel_api.utils.TimeSlotsGenerator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    PaymentService paymentService;

    @Autowired
    MatchRepository matchRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("id", id.toString()));
    }

    public List<Reservation> getReservationsByCourtId(Long courtId) {
        Court court = courtService.getCourtById(courtId);
        return reservationRepository.findByCourt(court);
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return reservationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<Reservation> getReservationsByUserIdAndStatus(Long userId, ReservationStatus status) {
        User user = userService.getUserById(userId);
        return reservationRepository.findByUserAndStatusOrderByCreatedAtDesc(user, status);
    }

    public List<Reservation> getReservationsByDate(LocalDate date) {
        return reservationRepository.findByDate(date);
    }

    @Transactional
    public Reservation createReservation(Reservation reservation) {
        Court court = courtService.getCourtById(reservation.getCourt().getId());
        User user = userService.getUserById(reservation.getUser().getId());
        reservation.setCourt(court);
        reservation.setUser(user);
        reservation.setEndTime(reservation.getStartTime().plusMinutes(ReservationConfig.MATCH_DURATION_MINUTES));
        validateReservation(reservation);
        reservationRepository.save(reservation);
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setReservation(reservation);
        paymentService.createPayment(payment);
        return reservation;
    }

    public List<Reservation> getReservationsByCourtIdAndDate(Long courtId, LocalDate date) {
        Court court = courtService.getCourtById(courtId);
        return reservationRepository.findByCourtAndDate(court, date);

    }

    public List<LocalTime> getFreeHoursByCourtIdAndDate(Long courtId, LocalDate date) {
        List<Reservation> reservations = getReservationsByCourtIdAndDate(courtId, date);
        List<LocalTime> allSlots = new ArrayList<>(TimeSlotsGenerator.VALID_TIME_SLOTS);

        for (Reservation r : reservations) {
            allSlots.removeIf(slot -> isInRange(slot, r) && !r.getStatus().equals(ReservationStatus.CANCELLED));
        }
        if (date.equals(LocalDate.now())) {
            LocalTime now = LocalTime.now();
            allSlots = allSlots.stream()
                    .filter(slot -> !slot.isBefore(now))
                    .collect(Collectors.toList());
        }

        return allSlots;
    }

    private boolean isInRange(LocalTime slot, Reservation reservation) {
        LocalTime slotEnd = slot.plusMinutes(reservation.getDuration());
        return slot.isBefore(reservation.getEndTime()) && slotEnd.isAfter(reservation.getStartTime());
    }

    private void validateReservation(Reservation reservation) {
        Court court = reservation.getCourt();
        LocalDate date = reservation.getDate();
        LocalTime startTime = reservation.getStartTime();
        LocalTime endTime = reservation.getEndTime();
        if (date.isBefore(LocalDate.now()) || (date.equals(LocalDate.now()) && startTime.isBefore(LocalTime.now()))) {
            throw new PastDateTimeException();
        }
        if (startTime.isBefore(ReservationConfig.OPENING_TIME) || endTime.isAfter(ReservationConfig.CLOSING_TIME)) {
            throw new ClubClosedException(ReservationConfig.OPENING_TIME, ReservationConfig.CLOSING_TIME);
        }
        if (!TimeSlotsGenerator.VALID_TIME_SLOTS.contains(startTime)) {
            throw new ReservationTimeNotValidException(startTime);
        }
        if (!reservationRepository.findConflictingReservations(court, date, startTime, endTime).isEmpty()) {
            throw new CourtOccupiedException(reservation, getFreeHoursByCourtIdAndDate(court.getId(), date));
        }
    }

    @Transactional
    public Reservation cancelReservationById(Long id) {
        Reservation reservation = getReservationById(id);
        if (reservation.getStatus() == ReservationStatus.COMPLETED) {
            throw new ReservationAlreadyCompletedException(reservation);
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setPaid(false);
        reservationRepository.save(reservation);
        paymentService.cancelPaymentsByReservationId(id);
        if (reservation.isMatch()) {
            Match match = matchRepository.findByReservation(reservation)
                    .orElseThrow(() -> new MatchNotFoundException("reservation", reservation.getId().toString()));
            match.setStatus(MatchStatus.CLOSED);
            matchRepository.save(match);
        }
        return reservation;
    }

    @Transactional
    public void markPastReservationsAsCompleted() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        List<Reservation> pendingReservations = reservationRepository.findFinishedReservations(today, now);
        for (Reservation r : pendingReservations) {
            r.setStatus(ReservationStatus.COMPLETED);
            reservationRepository.save(r);
        }
    }

    @Transactional
    public void markUnpaidReservationsAsCancelled() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        List<Reservation> unpaidReservations = reservationRepository.findStartedUnpaidReservations(today, now);
        for (Reservation r : unpaidReservations) {
            cancelReservationById(r.getId());
        }
    }

}
