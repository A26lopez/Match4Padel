package com.match4padel.match4padel_api.controllers;

import com.match4padel.match4padel_api.models.Payment;
import com.match4padel.match4padel_api.services.PaymentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(paymentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<Payment>> getByReservationId(@PathVariable Long reservationId) {
        return ResponseEntity.ok(paymentService.getByReservationId(reservationId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.getByUserId(userId));
    }
}
