package com.match4padel.match4padel_api.controllers;

import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.services.CourtService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courts")
public class CourtController {

    @Autowired
    private CourtService courtService;

    @GetMapping("/all")
    public ResponseEntity<List<Court>> getAllCourts() {
        List<Court> courts = courtService.getAll();
        return ResponseEntity.ok(courts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneCourt(@PathVariable Long id) {
        Court court = courtService.getOne(id);
        return ResponseEntity.ok(court);
    }

    @GetMapping
    public ResponseEntity<List<Court>> getCourtsByStatus(@RequestParam String status) {
        List<Court> courts = courtService.findCourtsByStatus(status);
        return ResponseEntity.ok(courts);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateCourtStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
            String newStatus = body.get("status");
            Court updatedCourt = courtService.updateCourtStatus(id, newStatus);
            return ResponseEntity.ok(updatedCourt);
    }

    
}
