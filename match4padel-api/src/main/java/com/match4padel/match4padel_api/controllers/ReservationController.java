package com.match4padel.match4padel_api.controllers;

import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.services.ReservationService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAll() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getById(id));

    }

    @GetMapping("/by-court/{courtId}")
    public ResponseEntity<List<Reservation>> getByCourtId(@PathVariable Long courtId) {
        return ResponseEntity.ok(reservationService.getByCourtId(courtId));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Reservation>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getByUserId(userId));
    }

    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<Reservation>> getByDate(@PathVariable String date) {
        return ResponseEntity.ok(reservationService.getByDate(LocalDate.parse(date)));
    }

    @GetMapping("/free-hours/{date}")
    public ResponseEntity<Map<LocalTime, List<Long>>> getFreeHoursByDate(
            @PathVariable("date") LocalDate date) {
        Map<LocalTime, List<Long>> availableHours = reservationService.getFreeHoursByDateAndDuration(date);
        return ResponseEntity.ok(availableHours);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody @Valid Reservation reservation) {
        return ResponseEntity.ok(reservationService.create(reservation));
    }


}
