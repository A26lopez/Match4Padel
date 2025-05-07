package com.match4padel.match4padel_api.repositories;

import com.match4padel.match4padel_api.models.Match;
import com.match4padel.match4padel_api.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByOwner(User user);

    List<Match> findByPlayer1OrPlayer2OrPlayer3(User player1, User player2, User player3);
}
