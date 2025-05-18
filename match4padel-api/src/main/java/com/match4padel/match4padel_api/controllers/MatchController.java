package com.match4padel.match4padel_api.controllers;

import com.match4padel.match4padel_api.models.Court;
import com.match4padel.match4padel_api.models.Match;
import com.match4padel.match4padel_api.models.Reservation;
import com.match4padel.match4padel_api.models.enums.Level;
import com.match4padel.match4padel_api.models.enums.MatchStatus;
import com.match4padel.match4padel_api.services.CourtService;
import com.match4padel.match4padel_api.services.MatchService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        return ResponseEntity.ok(matchService.getAllMatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getMatchById(id));
    }

    @PostMapping
    public ResponseEntity<Match> createMatch(@Valid @RequestBody Match match) {
        return ResponseEntity.ok(matchService.createMatch(match));
    }

    @GetMapping("/user/{id}")
    public List<Match> getMatchesByUserAndRole(
            @PathVariable Long id,
            @RequestParam(required = false, value = "role") String role) {

        if ("owner".equalsIgnoreCase(role)) {
            return matchService.getOwnMatchesByUserId(id);
        } else if ("player".equalsIgnoreCase(role)) {
            return matchService.getJoinedMatchesByUserId(id);
        }
        return matchService.getAllMatchesByUserId(id);
    }

    @GetMapping("/status/{status}")
    public List<Match> getMatchesByStatus(@PathVariable MatchStatus status) {
        return matchService.getMatchesByState(status);
    }

    @GetMapping("/level/{level}")
    public List<Match> getMatchesByLevel(@PathVariable Level level) {
        return matchService.getMatchesByLevel(level);
    }

    @PostMapping("/{matchId}/cancel")
    public Match cancelMatch(@PathVariable Long matchId) {
        return matchService.cancelMatchById(matchId);
    }

    @PostMapping("/{matchId}/add/{userId}")
    public Match addPlayer(@PathVariable Long matchId, @PathVariable Long userId) {
        return matchService.addPlayerByMatchIdAndUserId(matchId, userId);
    }

    @PostMapping("/{matchId}/remove/{userId}")
    public Match removePlayer(@PathVariable Long matchId, @PathVariable Long userId) {
        return matchService.removePlayerByMatchIdAndUserId(matchId, userId);
    }
}
