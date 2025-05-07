package com.match4padel.match4padel_api.controllers;

import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import com.match4padel.match4padel_api.services.ReservationService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping("/court/{courtId}")
    public ResponseEntity<List<Reservation>> getReservationsByCourtId(@PathVariable Long courtId) {
        return ResponseEntity.ok(reservationService.getReservationsByCourtId(courtId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserIdAndStatus(@PathVariable Long userId,
            @RequestParam(value = "status", required = false) ReservationStatus status) {
        if (status == null) {
            return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
        } else {
            return ResponseEntity.ok(reservationService.getReservationsByUserIdAndStatus(userId, status));
        }
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Reservation>> getReservationsByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(reservationService.getReservationsByDate(date));
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.createReservation(reservation));
    }

    @GetMapping("/court/{courtId}/date/{date}")
    public ResponseEntity<List<Reservation>> getReservationsByCourtIdAndDate(@PathVariable Long courtId,
            @PathVariable LocalDate date) {
        return ResponseEntity.ok(reservationService.getReservationsByCourtIdAndDate(courtId, date));
    }

    @GetMapping("/court/{courtId}/free-hours")
    public ResponseEntity<List<LocalTime>> getFreeHoursByCourtIdAndDate(@PathVariable Long courtId,
            @RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(reservationService.getFreeHoursByCourtIdAndDate(courtId, date));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Reservation> cancelReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.cancelReservationById(id));
    }
}
