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

    List<Reservation> findByUser(User user);

    List<Reservation> findByUserAndStatus(User user, ReservationStatus status);

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
