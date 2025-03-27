package com.match4padel.match4padel_api.models;

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
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "player_1_id")
    private User player1;

    @ManyToOne
    @JoinColumn(name = "player_2_id")
    private User player2;

    @ManyToOne
    @JoinColumn(name = "player_3_id")
    private User player3;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status = MatchStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private matchLevel level;
}

enum MatchStatus {
    OPEN,
    CLOSED,
    CONFIRMED,
    FINISHED,
    CANCELLED
}

enum matchLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}



