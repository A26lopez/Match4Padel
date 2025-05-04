package com.match4padel.match4padel_api.repositories;

import com.match4padel.match4padel_api.models.Payment;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByReservation(Reservation reservation);

    List<Payment> findByUser(User user);

    @Query("""
    SELECT p FROM Payment p
    WHERE p.status = 'PENDING'
    AND (p.reservation.date < :today OR (p.reservation.date = :today AND p.reservation.startTime < :now))
    """)
    List<Payment> findPastPendingPayments(@Param("today") LocalDate today, @Param("now") LocalTime now);

}
