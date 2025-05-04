package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.exceptions.PaymentNotFoundException;
import com.match4padel.match4padel_api.models.Payment;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.models.enums.PaymentStatus;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import com.match4padel.match4padel_api.repositories.PaymentRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Payment getById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("id", id.toString()));
    }

    public List<Payment> getByReservationId(Long reservationId) {
        Reservation reservation = reservationService.getById(reservationId);
        return paymentRepository.findByReservation(reservation);
    }

    public List<Payment> getByUserId(Long userId) {
        User user = userService.getById(userId);
        return paymentRepository.findByUser(user);
    }

    @Transactional
    public Payment create(Payment payment) {
        Reservation reservation = reservationService.getById(payment.getReservation().getId());
        User user = userService.getById(payment.getUser().getId());
        BigDecimal courtPrice = reservation.getCourt().getPricePerMatch();
        BigDecimal amount = reservation.isMatch()
                ? courtPrice.divide(BigDecimal.valueOf(4), 2, RoundingMode.HALF_UP)
                : courtPrice;
        payment.setAmount(amount);
        payment.setReservation(reservation);
        payment.setUser(user);
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment cancelById(Long id) {
        Payment payment = getById(id);
        if (payment.getStatus() == PaymentStatus.PENDING) {
            payment.setStatus(PaymentStatus.CANCELLED);
        }
        return paymentRepository.save(payment);
    }

    @Transactional
    public List<Payment> cancelPaymentsByReservationId(Long reservationId) {
        List<Payment> payments = getByReservationId(reservationId);
        for (Payment p : payments) {
            cancelById(p.getId());
        }
        return payments;
    }

    @Transactional
    public void markPendingPaymentsAsCompleted() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        List<Payment> pendingPayments = paymentRepository.findPastPendingPayments(today, now);
        for (Payment p : pendingPayments) {
            p.setStatus(PaymentStatus.COMPLETED);
            paymentRepository.save(p);
        }
    }
}
