package com.match4padel.match4padel_api.repositories;

import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByDate(LocalDate date);

    List<Reservation> findByUser(User user);

    List<Reservation> findByCourt(Court court);

    @Query("""
    SELECT r FROM Reservation r
    WHERE r.date = :date
    AND r.startTime <= :time
    AND r.endTime > :time
    AND r.status = :status
    """)
    List<Reservation> findByDateTimeAndStatus(
            @Param("date") LocalDate date,
            @Param("time") LocalTime time,
            @Param("status") ReservationStatus status
    );

    
}
