package com.match4padel.match4padel_api.models;

import com.match4padel.match4padel_api.config.ReservationConfig;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "La reserva tiene que estar hecha por un usuario.")
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Elige la pista.")
    private Court court;

    @Column(nullable = false)
    @NotNull(message = "Elige la fecha.")
    private LocalDate date;

    @Column(nullable = false)
    @NotNull(message = "Elige la hora")
    private LocalTime startTime;

    @Column(nullable = false)
    private int duration = ReservationConfig.DURATION;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.CONFIRMED;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean paid = false;

}
