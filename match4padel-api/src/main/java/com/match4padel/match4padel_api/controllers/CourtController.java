package com.match4padel.match4padel_api.controllers;

import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.services.CourtService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        return ResponseEntity.ok(courtService.getAllCourts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Court> getCourtById(@PathVariable Long id) {
        return ResponseEntity.ok(courtService.getCourtById(id));
    }

    @GetMapping("/free")
    public ResponseEntity<List<Court>> getFreeCourtsByDateAndTime(@RequestParam("date") LocalDate date,
            @RequestParam("starttime") LocalTime startTime) {
        return ResponseEntity.ok(courtService.getFreeCourtsByDateAndTime(date, startTime));
    }

    @GetMapping("/free/now")
    public ResponseEntity<List<Court>> getFreeCourtsNow() {
        return ResponseEntity.ok(courtService.getFreeCourtsNow());
    }

}
