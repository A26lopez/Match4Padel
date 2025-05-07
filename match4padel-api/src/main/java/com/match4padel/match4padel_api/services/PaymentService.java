package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.exceptions.CancelledReservationException;
import com.match4padel.match4padel_api.exceptions.NotPendingPaymentException;
import com.match4padel.match4padel_api.exceptions.PaymentNotFoundException;
import com.match4padel.match4padel_api.exceptions.PaymentsNumberException;
import com.match4padel.match4padel_api.exceptions.ReservationAlreadyCompletedException;
import com.match4padel.match4padel_api.exceptions.ReservationNotFoundException;
import com.match4padel.match4padel_api.exceptions.UserNotFoundException;
import com.match4padel.match4padel_api.models.Payment;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.models.enums.PaymentMethod;
import com.match4padel.match4padel_api.models.enums.PaymentStatus;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import com.match4padel.match4padel_api.repositories.PaymentRepository;
import com.match4padel.match4padel_api.repositories.ReservationRepository;
import com.match4padel.match4padel_api.repositories.UserRepository;
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
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository UserRepository;

    @Autowired
    ReservationRepository reservationRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("id", id.toString()));
    }

    public List<Payment> getPaymentsByReservationId(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("id", reservationId.toString()));
        return paymentRepository.findByReservation(reservation);
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        User user = UserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("id", userId.toString()));
        return paymentRepository.findByUser(user);
    }

    @Transactional
    public Payment createPayment(Payment payment) {
        Reservation reservation = payment.getReservation();
        User user = payment.getUser();
        
        BigDecimal amount = calculateAmount(reservation);
        payment.setAmount(amount);
        payment.setReservation(reservation);
        payment.setUser(user);
        validatePayment(payment);
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment completePaymentByIdAndMethod(Long paymentId, PaymentMethod method) {
        Payment payment = getPaymentById(paymentId);
        Reservation reservation = payment.getReservation();
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new NotPendingPaymentException(payment);
        }
        if (method != PaymentMethod.CASH) {
            payment.setStatus(PaymentStatus.COMPLETED);
        }
        validatePayment(payment);
        payment.setMethod(method);
        paymentRepository.save(payment);

        BigDecimal totalPaid = calculateCompletedPayments(reservation);
        BigDecimal pricePerMatch = reservation.getCourt().getPricePerMatch();
        reservation.setPaid(totalPaid.compareTo(pricePerMatch) >= 0);
        reservationRepository.save(reservation);
        return payment;
    }

    @Transactional
    public List<Payment> cancelPaymentsByReservationId(Long reservationId) {
        List<Payment> payments = getPaymentsByReservationId(reservationId);
        for (Payment p : payments) {
            cancelPaymentById(p.getId());
        }
        return payments;
    }

    @Transactional
    public Payment cancelPaymentById(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        payment.setStatus(payment.getStatus() == PaymentStatus.COMPLETED
                ? PaymentStatus.REFUNDED
                : PaymentStatus.CANCELLED);
        paymentRepository.save(payment);

        Reservation reservation = payment.getReservation();

        if (reservation.getStatus() != ReservationStatus.CANCELLED) {
            BigDecimal totalPaid = calculateCompletedPayments(reservation);
            BigDecimal pricePerMatch = reservation.getCourt().getPricePerMatch();
            reservation.setPaid(totalPaid.compareTo(pricePerMatch) >= 0);
            reservationRepository.save(reservation);
        }
        return payment;
    }

    @Transactional
    private void validatePayment(Payment payment) {
        Reservation reservation = payment.getReservation();
        List<Payment> nonCancelledPayments = paymentRepository.findNonCancelledPaymentsByReservation(reservation);

        if (nonCancelledPayments.size() > 4) {
            throw new PaymentsNumberException();
        }
        if (reservation.getStatus() == ReservationStatus.COMPLETED) {
            throw new ReservationAlreadyCompletedException(reservation);
        }
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new CancelledReservationException();
        }
    }

    @Transactional
    private BigDecimal calculateAmount(Reservation reservation) {
        BigDecimal courtPrice = reservation.getCourt().getPricePerMatch();
        BigDecimal amount = reservation.isMatch()
                ? courtPrice.divide(BigDecimal.valueOf(4), 2, RoundingMode.HALF_UP)
                : courtPrice;
        return amount;
    }

    @Transactional
    private BigDecimal calculateCompletedPayments(Reservation reservation) {
        List<Payment> paymentsByReservation = getPaymentsByReservationId(reservation.getId());
        BigDecimal totalPaid = paymentsByReservation.stream()
                .filter(p -> p.getStatus() == PaymentStatus.COMPLETED || (p.getStatus() == PaymentStatus.PENDING && p.getMethod() == PaymentMethod.CASH))
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPaid;
    }

    @Transactional
    public void markCashPaymentsAsCompleted() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        List<Payment> pendingCashPayments = paymentRepository.findPendingCashPaymentsOfFinishedReservations(today, now);
        for (Payment p : pendingCashPayments) {
            p.setStatus(PaymentStatus.COMPLETED);
            paymentRepository.save(p);
        }
    }
}
