package com.match4padel.match4padel_api.schedulers;

import com.match4padel.match4padel_api.services.PaymentService;
import com.match4padel.match4padel_api.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationScheduler {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private PaymentService paymentService;

    @Scheduled(cron = "0 * * * * *")
    public void updateReservationsAndPayments() {
        reservationService.markPastReservationsAsCompleted();
        reservationService.markUnpaidReservationsAsCancelled();
        paymentService.markCashPaymentsAsCompleted();
        
    }
}
