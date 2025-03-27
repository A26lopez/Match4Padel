package com.match4padel.match4padel_api.repositories;

import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.enums.CourtStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

    List<Court> findByStatus(CourtStatus status);

}
