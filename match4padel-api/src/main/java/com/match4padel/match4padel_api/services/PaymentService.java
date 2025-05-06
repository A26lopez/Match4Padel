package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.exceptions.CancelledReservationException;
import com.match4padel.match4padel_api.exceptions.PaymentNotFoundException;
import com.match4padel.match4padel_api.exceptions.PaymentsNumberException;
import com.match4padel.match4padel_api.exceptions.ReservationAlreadyCompletedException;
import com.match4padel.match4padel_api.models.Payment;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.models.enums.PaymentStatus;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import com.match4padel.match4padel_api.repositories.PaymentRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("id", id.toString()));
    }

    public List<Payment> getPaymentsByReservationId(Long reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        return paymentRepository.findByReservation(reservation);
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return paymentRepository.findByUser(user);
    }

    @Transactional
    public Payment createPayment(Payment payment) {
        Reservation reservation = reservationService.getReservationById(payment.getReservation().getId());
        User user = userService.getUserById(payment.getUser().getId());
        BigDecimal amount = calculateAmount(reservation);
        payment.setAmount(amount);
        payment.setReservation(reservation);
        payment.setUser(user);
        validatePayment(payment);
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment cancelPaymentById(Long id) {
        Payment payment = getPaymentById(id);
        payment.setStatus(PaymentStatus.CANCELLED);
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment completePaymentById(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        Reservation reservation = reservationService.getReservationById(payment.getReservation().getId());
        payment.setStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);
        BigDecimal totalPaid = calculateCompletedPayments(reservation);
        if (totalPaid.compareTo(reservation.getCourt().getPricePerMatch()) >= 0) {
            reservationService.markReservationAsPaidById(reservation.getId());
        }
        return payment;
    }

    private void validatePayment(Payment payment) {
        Reservation reservation = payment.getReservation();
        List<Payment> nonCancelledPayments = getPaymentsByReservationId(reservation.getId())
                .stream()
                .filter(p -> p.getStatus() != PaymentStatus.CANCELLED)
                .toList();

        if (nonCancelledPayments.size() >= 4) {
            throw new PaymentsNumberException();
        }
        if (reservation.getStatus() == ReservationStatus.COMPLETED) {
            throw new ReservationAlreadyCompletedException(reservation);
        }
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new CancelledReservationException();
        }
    }

    private BigDecimal calculateAmount(Reservation reservation) {
        BigDecimal courtPrice = reservation.getCourt().getPricePerMatch();
        BigDecimal amount = reservation.isMatch()
                ? courtPrice.divide(BigDecimal.valueOf(4), 2, RoundingMode.HALF_UP)
                : courtPrice;
        return amount;
    }

    private BigDecimal calculateCompletedPayments(Reservation reservation) {
        List<Payment> paymentsByReservation = getPaymentsByReservationId(reservation.getId());
        BigDecimal totalPaid = paymentsByReservation.stream()
                .filter(p -> p.getStatus() == PaymentStatus.COMPLETED)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPaid;
    }
    
    
}
