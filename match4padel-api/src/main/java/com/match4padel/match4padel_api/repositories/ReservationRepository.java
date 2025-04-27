package com.match4padel.match4padel_api.repositories;

import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCourt(Court court);

    List<Reservation> findByUser(User user);

    List<Reservation> findByUserAndStatus(User user, ReservationStatus status);

    List<Reservation> findByDate(LocalDate date);

    Optional<Reservation> findByCourtAndDateAndStartTimeAndEndTime(Court court, LocalDate date, LocalTime startTime, LocalTime endTime);

    @Query("""
    SELECT c FROM Court c
    WHERE c.id NOT IN (
        SELECT r.court.id FROM Reservation r
        WHERE r.date = :date
        AND r.startTime < :endTime
        AND r.endTime > :startTime
    )
""")
    List<Court> findFreeCourtsByDateAndTime(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    @Query("SELECT r FROM Reservation r WHERE r.court = :court "
            + "AND r.date = :date "
            + "AND ((r.startTime < :endTime AND r.endTime > :startTime))")
   List<Reservation> findConflictingReservations(Court court, LocalDate date, LocalTime startTime, LocalTime endTime);

}
