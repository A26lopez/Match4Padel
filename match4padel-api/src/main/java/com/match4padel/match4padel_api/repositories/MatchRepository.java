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
    """)
    List<Match> findByUser(User user);

    List<Match> findByOwner(User user);

    @Query("""
    SELECT m FROM Match m
    WHERE m.player_1 = :user
       OR m.player_2 = :user
       OR m.player_3 = :user
    """)
    List<Match> findByJoined(@Param("user") User user);
    
    Optional<Match> findByReservation(Reservation reservation);

    List<Match> findByStatus(MatchStatus status);

    List<Match> findByLevel(Level level);

}
