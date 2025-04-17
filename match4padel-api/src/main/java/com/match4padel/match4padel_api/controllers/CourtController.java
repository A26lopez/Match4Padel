package com.match4padel.match4padel_api.controllers;

import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.enums.CourtStatus;
import com.match4padel.match4padel_api.services.CourtService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courts")
public class CourtController {

    @Autowired
    private CourtService courtService;

    @GetMapping
    public ResponseEntity<List<Court>> getAllCourts() {
        return ResponseEntity.ok(courtService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Court> getCourtById(@PathVariable Long id) {
        return ResponseEntity.ok(courtService.getById(id));
    }

    @GetMapping("/free/now")
    public ResponseEntity<List<Court>> getFreeNow() {
        return ResponseEntity.ok(courtService.getFreeNow());
    }

    @GetMapping("/free")
    public ResponseEntity<List<Court>> getAvailableCourts(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time) {
        List<Court> availableCourts = courtService.getFreeByDateAndTime(date, time);
        return ResponseEntity.ok(availableCourts);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Court> changeStatus(
            @PathVariable Long id,
            @RequestParam CourtStatus status) {
        Court court = courtService.changeStatus(id, status);
        return ResponseEntity.ok(court);
    }
}
