package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.enums.CourtStatus;
import com.match4padel.match4padel_api.repositories.CourtRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourtService {

    @Autowired
    CourtRepository courtRepository;

    public List<Court> getAll() {
        return courtRepository.findAll();
    }

    public Court getOne(Long id) {
        return courtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Court with ID " + id + " not found"));
    }

    public List<Court> findCourtsByStatus(String status) {
        try {
            CourtStatus courtStatus = CourtStatus.valueOf(status);
            return courtRepository.findByStatus(courtStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status not found: " + status);
        }
    }

    public Court updateCourtStatus(Long id, String newStatus) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Court with id " + id + " not found"));

        try {
            CourtStatus statusEnum = CourtStatus.valueOf(newStatus.toLowerCase());
            court.setStatus(statusEnum);
            return courtRepository.save(court);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status not found: " + newStatus);
        }
    }
}
