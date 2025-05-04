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
@RequestMapping("reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.create(reservation));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Long userId,
            @RequestParam(value = "reservations", required = false) ReservationStatus status) {
        if (status == null) {
            return ResponseEntity.ok(reservationService.getByUserId(userId));
        } else {
            return ResponseEntity.ok(reservationService.getByUserIdAndStatus(userId, status));
        }
    }

    @GetMapping("/court/{courtId}")
    public ResponseEntity<List<Reservation>> getReservationsByCourtId(@PathVariable Long courtId) {
        return ResponseEntity.ok(reservationService.getByCourtId(courtId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Reservation>> getReservationsByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(reservationService.getByDate(date));
    }

    @GetMapping("/court/{courtId}/date/{date}")
    public ResponseEntity<List<Reservation>> getReservationsByCourtIdAndDate(@PathVariable Long courtId,
            @PathVariable LocalDate date) {
        return ResponseEntity.ok(reservationService.getByCourtIdAndDate(courtId, date));
    }

    @GetMapping("/court/{courtId}/free-hours")
    public ResponseEntity<List<LocalTime>> getFreeHoursByCourt(@PathVariable Long courtId,
            @RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(reservationService.getFreeHoursByCourtIdAndDate(courtId, date));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Reservation> cancelReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }
}
