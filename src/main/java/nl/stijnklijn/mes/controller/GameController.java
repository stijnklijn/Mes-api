package nl.stijnklijn.mes.controller;

import nl.stijnklijn.mes.model.*;
import nl.stijnklijn.mes.service.GameService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class GameController {

   private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/rest/create-game")
    public ResponseEntity<String> createGame() {
        HttpHeaders headers = new HttpHeaders();
        String gameId = gameService.createGame();
        headers.add("Game-Id", gameId);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/rest/can-join/{gameId}/{playerName}")
    public ResponseEntity<String> canJoin(@PathVariable String gameId, @PathVariable String playerName) {
        String errorMessage = null;

        if (!gameService.gameExists(gameId)) {
            errorMessage = String.format("Er bestaat geen spel met code %s.", gameId);
        }

        if (gameService.gameFull(gameId)) {
            errorMessage = String.format("Het spel met code %s is vol.", gameId);
        }

        if (gameService.playerNameTaken(gameId, playerName)) {
            errorMessage = String.format("Er zit al een speler met naam %s in spel %s.", playerName, gameId);
        }

        if (errorMessage != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error", errorMessage);
            return new ResponseEntity<>(headers, HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @MessageMapping("/join-game")
    public void joinGame(JoinRequest joinRequest, Principal principal) {
        gameService.joinGame(new Player(principal.getName(), joinRequest.getName()), joinRequest.getGameId());
    }

    @MessageMapping("/submit-answers")
    public void submitAnswers(List<Answer> answers, Principal principal) {
        gameService.submitAnswers(principal.getName(), answers);
    }

    @MessageMapping("/submit-bid")
    public void submitBid(Bid bid, Principal principal) {
        gameService.submitBid(principal.getName(), bid);
    }

    @MessageMapping("/chat")
    public void chat(ChatMessage chatMessage, Principal principal) {
        gameService.chat(principal.getName(), chatMessage);
    }

}