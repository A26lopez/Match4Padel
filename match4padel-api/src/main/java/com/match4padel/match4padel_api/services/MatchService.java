package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.exceptions.AlreadyJoinedMatchException;
import com.match4padel.match4padel_api.exceptions.CompletedMatchException;
import com.match4padel.match4padel_api.exceptions.MatchAlreadyCancelledException;
import com.match4padel.match4padel_api.exceptions.MatchNotFoundException;
import com.match4padel.match4padel_api.exceptions.PlayerNotInMatchException;
import com.match4padel.match4padel_api.exceptions.ReservationNotAvailableException;
import com.match4padel.match4padel_api.models.Match;
import com.match4padel.match4padel_api.models.Payment;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.models.enums.Level;
import com.match4padel.match4padel_api.models.enums.MatchStatus;
import com.match4padel.match4padel_api.models.enums.PaymentStatus;
import com.match4padel.match4padel_api.models.enums.ReservationStatus;
import com.match4padel.match4padel_api.repositories.MatchRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    UserService userService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    PaymentService paymentService;

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match getMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException("id", id.toString()));
    }

    public List<Match> getAllMatchesByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return matchRepository.findByUserOrdered(user);
    }

    public List<Match> getOwnMatchesByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return matchRepository.findByOwner(user);
    }

    public List<Match> getJoinedMatchesByUserId(Long UserId) {
        User user = userService.getUserById(UserId);
        return matchRepository.findByJoinedOrdered(user);
    }

    public List<Match> getMatchesByStatus(MatchStatus status) {
        return matchRepository.findByStatusOrdered(status);
    }

    public List<Match> getMatchesByLevel(Level level) {
        return matchRepository.findByLevel(level);
    }

    @Transactional
    public Match createMatch(Match match) {
        User owner = userService.getUserById(match.getOwner().getId());
        Reservation reservation = match.getReservation();
        reservation.setUser(owner);
        reservation.setMatch(true);
        reservationService.createReservation(reservation);
        match.setReservation(reservation);
        match.setOwner(owner);
        return matchRepository.save(match);
    }

    @Transactional
    public Match cancelMatchById(Long matchId) {
        Match match = getMatchById(matchId);
        Reservation reservation = reservationService.getReservationById(match.getReservation().getId());
        match.setStatus(MatchStatus.CLOSED);
        reservationService.cancelReservationById(reservation.getId());
        return matchRepository.save(match);
    }

    @Transactional
    public Match addPlayerByMatchIdAndUserId(Long matchId, Long userId) {
        Match match = getMatchById(matchId);
        User player = userService.getUserById(userId);
        Reservation reservation = match.getReservation();
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new ReservationNotAvailableException(reservation);
        }
        if (matchContainsUser(match, player)) {
            throw new AlreadyJoinedMatchException(match, player);
        }
        boolean addedPlayer = addPlayerToMatch(match, player);
        if (match.getStatus() != MatchStatus.OPEN || !addedPlayer) {
            throw new CompletedMatchException(match);
        }

        if (matchIsFull(match)) {
            match.setStatus(MatchStatus.CLOSED);
        }

        Payment payment = new Payment();
        payment.setUser(player);
        payment.setReservation(match.getReservation());
        paymentService.createPayment(payment);

        return matchRepository.save(match);
    }

    @Transactional
    public Match removePlayerByMatchIdAndUserId(Long matchId, Long userId) {
        Match match = getMatchById(matchId);
        Reservation reservation = match.getReservation();
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new MatchAlreadyCancelledException();
        }
        if (reservation.getStatus() == ReservationStatus.COMPLETED) {
            throw new MatchAlreadyCancelledException();
        }
        User player = userService.getUserById(userId);
        List<Payment> payments = paymentService.getPaymentsByReservationId(reservation.getId());
        for (Payment p : payments) {
            if (p.getUser() == player) {
                paymentService.cancelPaymentById(p.getId());
            }
        }

        boolean removedPlayer = removePlayerToMatch(match, player);
        if (!removedPlayer) {
            throw new PlayerNotInMatchException(player);
        }
        match.setStatus(MatchStatus.OPEN);
        return matchRepository.save(match);
    }

    private boolean matchContainsUser(Match match, User user) {
        return user.equals(match.getPlayer_1())
                || user.equals(match.getPlayer_2())
                || user.equals(match.getPlayer_3());
    }

    private boolean addPlayerToMatch(Match match, User user) {
        if (match.getPlayer_1() == null) {
            match.setPlayer_1(user);
            return true;
        } else if (match.getPlayer_2() == null) {
            match.setPlayer_2(user);
            return true;
        } else if (match.getPlayer_3() == null) {
            match.setPlayer_3(user);
            return true;
        }
        return false;
    }

    private boolean removePlayerToMatch(Match match, User user) {
        if (match.getPlayer_1() == user) {
            match.setPlayer_1(null);
            return true;
        } else if (match.getPlayer_2() == user) {
            match.setPlayer_2(null);
            return true;
        } else if (match.getPlayer_3() == user) {
            match.setPlayer_3(null);
            return true;
        }
        return false;
    }

    private boolean matchIsFull(Match match) {
        return match.getPlayer_1() != null
                && match.getPlayer_2() != null
                && match.getPlayer_3() != null;
    }

}
