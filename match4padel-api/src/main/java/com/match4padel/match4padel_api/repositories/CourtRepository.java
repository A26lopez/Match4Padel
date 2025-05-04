package com.match4padel.match4padel_api.repositories;

import com.match4padel.match4padel_api.models.Court;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

    @Query("""
    SELECT c FROM Court c
    WHERE c.id NOT IN (
        SELECT r.court.id FROM Reservation r
        WHERE r.date = :date
        AND r.startTime < :endTime
        AND r.endTime > :startTime
        AND r.status = 'CONFIRMED'
    )
    AND (
        :date > CURRENT_DATE OR
        (:date = CURRENT_DATE AND :startTime >= CURRENT_TIME)
    )
""")
    List<Court> findFreeCourtsByDateAndTime(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
