package com.match4padel.match4padel_api.repositories;

import com.match4padel.match4padel_api.models.Match;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.models.enums.Level;
import com.match4padel.match4padel_api.models.enums.MatchStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("""
    SELECT m FROM Match m
    WHERE m.owner = :user
       OR m.player_1 = :user
       OR m.player_2 = :user
       OR m.player_3 = :user
    ORDER BY 
            CASE WHEN m.reservation.status = 'CONFIRMED' THEN 0 ELSE 1 END,
            CASE WHEN m.reservation.status = 'CONFIRMED' THEN m.reservation.date ELSE NULL END ASC,
            CASE WHEN m.reservation.status IN ('CANCELLED', 'COMPLETED') THEN m.reservation.date ELSE NULL END DESC,
            m.reservation.court.name ASC,
            m.reservation.startTime ASC
    """)
    List<Match> findByUserOrdered(User user);

    List<Match> findByOwner(User user);

    @Query("""
    SELECT m FROM Match m
    WHERE m.player_1 = :user
       OR m.player_2 = :user
       OR m.player_3 = :user
    ORDER BY 
        CASE WHEN m.reservation.status = 'CONFIRMED' THEN 0 ELSE 1 END,
        CASE WHEN m.reservation.status = 'CONFIRMED' THEN m.reservation.date ELSE NULL END ASC,
        CASE WHEN m.reservation.status IN ('CANCELLED', 'COMPLETED') THEN m.reservation.date ELSE NULL END DESC,
        m.reservation.court.name ASC,
        m.reservation.startTime ASC
    """)
    List<Match> findByJoinedOrdered(@Param("user") User user);

    Optional<Match> findByReservation(Reservation reservation);

    @Query("""
    SELECT m FROM Match m 
    WHERE m.status = :status
    ORDER BY m.reservation.date ASC, m.reservation.startTime ASC, m.reservation.court ASC
    """)
    List<Match> findByStatusOrdered(@Param("status") MatchStatus status);

    List<Match> findByLevel(Level level);

}
