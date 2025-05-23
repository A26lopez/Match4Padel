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

    List<Reservation> findByCourt(Court court);

    @Query("""
    SELECT r FROM Reservation r
    WHERE r.user = :user
    ORDER BY 
        CASE WHEN r.status = 'CONFIRMED' THEN 0 ELSE 1 END,
        CASE WHEN r.status = 'CONFIRMED' THEN r.date ELSE NULL END ASC,
        CASE WHEN r.status IN ('CANCELLED', 'COMPLETED') THEN r.date ELSE NULL END DESC,
        r.startTime ASC,
        r.court.name ASC
""")
    List<Reservation> findByUserOrdered(@Param("user") User user);

    @Query("""
    SELECT r FROM Reservation r 
    WHERE r.user = :user AND r.status = :status
    ORDER BY 
        CASE WHEN r.status = 'CONFIRMED' THEN 0 ELSE 1 END,
        CASE WHEN r.status = 'CONFIRMED' THEN r.date ELSE NULL END ASC,
        CASE WHEN r.status IN ('CANCELLED', 'COMPLETED') THEN r.date ELSE NULL END DESC,
        r.court.name ASC,
        r.startTime ASC
""")
    List<Reservation> findByUserAndStatusOrdered(User user, ReservationStatus status);

    List<Reservation> findByDate(LocalDate date);

    List<Reservation> findByCourtAndDate(Court court, LocalDate date);

    @Query("""
    SELECT r FROM Reservation r
    WHERE r.status = 'CONFIRMED'
    AND r.isPaid = true
    AND (r.date < :today OR (r.date = :today AND r.endTime < :now))
""")
    List<Reservation> findFinishedReservations(@Param("today") LocalDate today, @Param("now") LocalTime now);

    @Query("""
    SELECT r FROM Reservation r
    WHERE r.status = 'CONFIRMED'
    AND r.isPaid = false
    AND (r.date < :today OR (r.date = :today AND r.startTime < :now))
""")
    List<Reservation> findStartedUnpaidReservations(@Param("today") LocalDate today, @Param("now") LocalTime now);

    @Query("""
    SELECT r FROM Reservation r WHERE r.court = :court
    AND r.date = :date
    AND r.status = 'CONFIRMED'
    AND ((r.startTime < :endTime AND r.endTime > :startTime))
    """)
    List<Reservation> findConflictingReservations(Court court, LocalDate date, LocalTime startTime, LocalTime endTime);

}
