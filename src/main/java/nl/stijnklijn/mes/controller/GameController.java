package nl.stijnklijn.mes.controller;

import lombok.extern.slf4j.Slf4j;
import nl.stijnklijn.mes.exception.GameFullException;
import nl.stijnklijn.mes.exception.NoSuchGameException;
import nl.stijnklijn.mes.exception.PlayerNameTakenException;
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

import static nl.stijnklijn.mes.constants.Constants.*;

@Slf4j
@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/rest/shared-constants")
    public ResponseEntity<SharedConstants> getSharedConstants() {
        return new ResponseEntity<>(new SharedConstants(), HttpStatus.OK);
    }

    @GetMapping("/rest" + CREATE_GAME_PATH)
    public ResponseEntity<String> createGame() {
        HttpHeaders headers = new HttpHeaders();
        String gameId = gameService.createGame();
        headers.add(GAME_ID_HEADER, gameId);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/rest" + CAN_JOIN_PATH + "/{gameId}/{playerName}")
    public ResponseEntity<String> canJoin(@PathVariable String gameId, @PathVariable String playerName) {

        if (!gameService.gameExists(gameId)) {
            throw new NoSuchGameException(gameId);
        }

        if (gameService.gameFull(gameId)) {
            throw new GameFullException(gameId);
        }

        if (gameService.playerNameTaken(gameId, playerName)) {
            throw new PlayerNameTakenException(gameId, playerName);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @MessageMapping(JOIN_GAME_PATH)
    public void joinGame(JoinRequest joinRequest, Principal principal) {
        gameService.joinGame(new Player(principal.getName(), joinRequest.name()), joinRequest.gameId());
    }

    @MessageMapping(SUBMIT_ANSWERS_PATH)
    public void submitAnswers(List<Answer> answers, Principal principal) {
        gameService.submitAnswers(principal.getName(), answers);
    }

    @MessageMapping(SUBMIT_BID_PATH)
    public void submitBid(Bid bid, Principal principal) {
        gameService.submitBid(principal.getName(), bid);
    }

    @MessageMapping(CHAT_PATH)
    public void chat(ChatMessage chatMessage, Principal principal) {
        gameService.chat(principal.getName(), chatMessage);
    }

    @MessageMapping(HEARTBEAT_PATH)
    public void keepAlive(Principal principal) {
        log.info("Ontvangen heartbeat van speler {}", principal.getName());
    }

}