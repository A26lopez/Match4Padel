package com.match4padel.match4padel_api.models;

import com.match4padel.match4padel_api.models.enums.Level;
import com.match4padel.match4padel_api.models.enums.MatchStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "matches")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Un partido tiene que estar asociado a una reserva.")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotBlank(message = "El partido tiene que tener un creador.")
    private User owner;

    @ManyToOne
    @JoinColumn
    private User player1;

    @ManyToOne
    @JoinColumn
    private User player2;

    @ManyToOne
    @JoinColumn
    private User player3;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status = MatchStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotBlank(message = "Elige un nivel.")
    private Level level;
}