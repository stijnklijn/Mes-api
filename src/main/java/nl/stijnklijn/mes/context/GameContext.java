package nl.stijnklijn.mes.context;

import lombok.Getter;
import lombok.Setter;
import nl.stijnklijn.mes.dto.GameDto;
import nl.stijnklijn.mes.enums.MessageType;
import nl.stijnklijn.mes.event.AnswersSubmittedEvent;
import nl.stijnklijn.mes.event.BidSubmittedEvent;
import nl.stijnklijn.mes.event.PlayerJoinedEvent;
import nl.stijnklijn.mes.mapper.GameMapper;
import nl.stijnklijn.mes.model.*;
import nl.stijnklijn.mes.service.MessageService;
import nl.stijnklijn.mes.service.QuestionService;
import nl.stijnklijn.mes.state.AwaitAnswersState;
import nl.stijnklijn.mes.state.AwaitBidState;
import nl.stijnklijn.mes.state.AwaitOpponentState;
import nl.stijnklijn.mes.state.State;

import java.util.List;

public class GameContext {

    @Getter
    private final String gameId;

    private final MessageService messageService;
    private final QuestionService questionService;
    private final GameMapper gameMapper;

    @Getter
    private final AwaitOpponentState awaitOpponentState;
    @Getter
    private final AwaitAnswersState awaitAnswersState;
    @Getter
    private final AwaitBidState awaitBidState;

    private State state;

    @Getter
    @Setter
    private Game game;

    public GameContext(String gameId, MessageService messageService, QuestionService questionService,
                       GameMapper gameMapper, AwaitOpponentState awaitOpponentState,
                       AwaitAnswersState awaitAnswersState, AwaitBidState awaitBidState) {
        this.gameId = gameId;
        this.messageService = messageService;
        this.questionService = questionService;
        this.gameMapper = gameMapper;
        this.awaitOpponentState = awaitOpponentState;
        this.awaitAnswersState = awaitAnswersState;
        this.awaitBidState = awaitBidState;
        state = awaitOpponentState;
    }

    public void handleJoin(Player player) {
        state = state.handle(new PlayerJoinedEvent(player, null), this);
    }

    public synchronized  void handleAnswers(Player player, List<Answer> answers) {
        state = state.handle(new AnswersSubmittedEvent(player, answers), this);
    }

    public synchronized void handleBid(Player player, Bid bid) {
        state = state.handle(new BidSubmittedEvent(player, bid), this);
    }

    public List<Question> getQuestions() {
        return questionService.getQuestions();
    }

    public void sendTo(Player player, Message message) {
        messageService.sendTo(player, message);
    }

    public void broadcast(Message message) {
        messageService.broadcast(gameId, message);
    }

    public void broadcastGame() {
        messageService.sendTo(game.getPlayer1(),
                new Message(MessageType.GAME_STATE, gameMapper.toGameDto(game.getPlayer1().getId(), game)));
        messageService.sendTo(game.getPlayer2(),
                new Message(MessageType.GAME_STATE, gameMapper.toGameDto(game.getPlayer2().getId(), game)));
    }

    public GameDto toGameDto(String player, Game game) {
        return gameMapper.toGameDto(player, game);
    }

    public Player getSelf(String playerId) {
        if (playerId.equals(game.getPlayer1().getId())) {
            return game.getPlayer1();
        } else {
            return game.getPlayer2();
        }
    }

    public Player getOpponent(String playerId) {
        if (playerId.equals(game.getPlayer1().getId())) {
            return game.getPlayer2();
        } else {
            return game.getPlayer1();
        }
    }

}
