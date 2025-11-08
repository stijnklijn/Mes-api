package nl.stijnklijn.mes.service;

import nl.stijnklijn.mes.context.GameContext;
import nl.stijnklijn.mes.enums.MessageType;
import nl.stijnklijn.mes.exception.InvalidDataException;
import nl.stijnklijn.mes.mapper.GameMapper;
import nl.stijnklijn.mes.model.*;
import nl.stijnklijn.mes.state.AwaitAnswersState;
import nl.stijnklijn.mes.state.AwaitBidState;
import nl.stijnklijn.mes.state.AwaitOpponentState;
import nl.stijnklijn.mes.state.helper.AssignBidHelper;
import nl.stijnklijn.mes.state.helper.EvaluateAnswerHelper;
import nl.stijnklijn.mes.state.helper.NextRoundHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static nl.stijnklijn.mes.constants.Constants.*;

@Service
public class GameService {

    private final SessionService sessionService;
    private final MessageService messageService;
    private final QuestionService questionService;
    private final GameMapper gameMapper;

    private final NextRoundHelper nextRoundHelper = new NextRoundHelper();
    private final EvaluateAnswerHelper evaluateAnswerHelper = new EvaluateAnswerHelper();
    private final AssignBidHelper assignBidHelper = new AssignBidHelper();

    private final AwaitOpponentState awaitOpponentState = new AwaitOpponentState(nextRoundHelper);
    private final AwaitAnswersState awaitAnswersState = new AwaitAnswersState(evaluateAnswerHelper, assignBidHelper);
    private final AwaitBidState awaitBidState = new AwaitBidState(nextRoundHelper, assignBidHelper);

    public GameService(SessionService sessionService, MessageService messageService,
                       QuestionService questionService, GameMapper gameMapper) {
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.questionService = questionService;
        this.gameMapper = gameMapper;
    }

    public String createGame() {
        String gameId;
        do {
            gameId = UUID.randomUUID().toString().substring(GAME_ID_START_INDEX, GAME_ID_END_INDEX).toUpperCase();
        } while (gameExists(gameId));
        GameContext ctx = new GameContext(gameId, messageService, questionService, gameMapper,
                awaitOpponentState, awaitAnswersState, awaitBidState);
        sessionService.registerGame(gameId, ctx);
        return gameId;
    }

    public boolean gameExists(String gameId) {
        return sessionService.gameExists(gameId.toUpperCase());
    }

    public boolean gameFull(String gameId) {
        return sessionService.gameFull(gameId.toUpperCase());
    }

    public boolean playerNameTaken(String gameId, String name) {
        return sessionService.playerNameTaken(gameId.toUpperCase(), name);
    }

    public synchronized void joinGame(Player player, String gameId) {
        if (player.getName().length() > MAX_NAME_LENGTH) {
            throw new InvalidDataException();
        }
        GameContext ctx = sessionService.registerPlayer(player, gameId.toUpperCase());
        ctx.handleJoin(player);
    }

    public void submitAnswers(String playerId, List<Answer> answers) {
        for (Answer answer : answers) {
            if (answer.getContent().length() > MAX_CONTENT_LENGTH) {
                throw new InvalidDataException();
            }
        }
        GameContext ctx = sessionService.getGameContext(playerId);
        ctx.handleAnswers(new Player(playerId, null), answers);
    }

    public void submitBid(String playerId, Bid bid) {
        if (!ALLOWED_BIDS.contains(bid.amount())) {
            throw new InvalidDataException();
        }
        GameContext ctx = sessionService.getGameContext(playerId);
        ctx.handleBid(new Player(playerId, null), bid);
    }

    public void chat(String playerId, ChatMessage chatMessage) {
        if (chatMessage.content().length() > MAX_CONTENT_LENGTH) {
            throw new InvalidDataException();
        }
        GameContext ctx = sessionService.getGameContext(playerId);
        ctx.broadcast(new Message(MessageType.INFO, String.format("%s zegt: %s", chatMessage.name(), chatMessage.content())));

    }

}
