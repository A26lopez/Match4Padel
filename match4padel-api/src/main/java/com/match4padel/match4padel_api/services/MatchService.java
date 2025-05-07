package com.match4padel.match4padel_api.services;

import com.match4padel.match4padel_api.exceptions.MatchNotFoundException;
import com.match4padel.match4padel_api.models.Match;
import com.match4padel.match4padel_api.models.User;
import com.match4padel.match4padel_api.repositories.MatchRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    @Autowired
    MatchRepository matchRepository;
    
    @Autowired
    UserService userService;

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match getMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException("id", id.toString()));
    }

    public List<Match> getOwnMatchesByUserId(Long userId){
        User user = userService.getUserById(userId);
        return matchRepository.findByOwner(user);
    }
    
    public List<Match> getPaticipatingMatchesByUserId(Long UserId){
        User user = userService.getUserById(UserId);
        return matchRepository.findByPlayer1OrPlayer2OrPlayer3(user, user, user);
    }
    
//    getmatchesbyusergetmatchesbycourt
//    getmatches bystate
//    filtrar por nivel crear partido añadirusuario
//    quitarusuario cancelarpartido
                                            
}
