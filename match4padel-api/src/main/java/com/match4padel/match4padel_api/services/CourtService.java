package com.match4padel.match4padel_api.services;


import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.enums.CourtStatus;
import com.match4padel.match4padel_api.repositories.CourtRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourtService {

    @Autowired
    CourtRepository courtRepository;
}
