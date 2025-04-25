package com.match4padel.match4padel_api.repositories;

import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.enums.CourtStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

    List<Court> findByCourtStatus(CourtStatus courtStatus);

    @Query("""
    SELECT c FROM Court c 
    WHERE c.id NOT IN (
        SELECT r.court.id 
        FROM Reservation r 
        WHERE r.date = :date 
        AND r.status = 'CONFIRMED'
        AND (:startTime < r.endTime AND :endTime > r.startTime)
    ) 
    AND c.courtStatus = 'AVAILABLE'
""")
    List<Court> findFreeCourtsByDateAndTimeRange(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

}
