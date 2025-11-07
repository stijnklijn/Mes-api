package nl.stijnklijn.mes.service;

import lombok.extern.slf4j.Slf4j;
import nl.stijnklijn.mes.context.GameContext;
import nl.stijnklijn.mes.enums.MessageType;
import nl.stijnklijn.mes.exception.GameFullException;
import nl.stijnklijn.mes.exception.PlayerNameTakenException;
import nl.stijnklijn.mes.model.Message;
import nl.stijnklijn.mes.model.Player;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static nl.stijnklijn.mes.constants.Constants.PLAYERS_PER_GAME;

@Slf4j
@Service
public class SessionService {

    private final Map<String, GameContext> gameIdToGameContext = new ConcurrentHashMap<>();
    private final Map<String, List<Player>> gameIdToPlayers = new ConcurrentHashMap<>();
    private final Map<String, GameContext> playerIdToGameContext = new ConcurrentHashMap<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        String playerId = event.getUser().getName();
        log.info("Speler {} is verbonden.", playerId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String playerId = event.getUser().getName();
        GameContext ctx = playerIdToGameContext.get(playerId);
        log.info("Speler {} heeft spel {} verlaten.", playerId, ctx.getGameId());
        playerIdToGameContext.remove(playerId);
        List<Player> players = gameIdToPlayers.get(ctx.getGameId());
        players.removeIf(p -> p.getId().equals(playerId));
        if (players.isEmpty()) {
            gameIdToGameContext.remove(ctx.getGameId());
            gameIdToPlayers.remove(ctx.getGameId());
            playerIdToGameContext.remove(playerId);
            log.info("Spel {} is afgesloten.", ctx.getGameId());
        } else {
            ctx.sendTo(players.getFirst(), new Message(MessageType.ERROR, "De tegenstander heeft het spel verlaten."));
        }
    }

    public void registerGame(String gameId, GameContext ctx) {
        gameIdToGameContext.put(gameId, ctx);
        gameIdToPlayers.put(gameId, new ArrayList<>());
        log.info("Spel {} is gestart.", gameId);
    }

    public GameContext registerPlayer(Player player, String gameId) {
        GameContext ctx = gameIdToGameContext.get(gameId);

        if (gameFull(gameId)) {
            throw new GameFullException(gameId);
        }

        if (playerNameTaken(gameId, player.getName())) {
            throw new PlayerNameTakenException(player.getId(), gameId);
        }

        List<Player> players = gameIdToPlayers.get(gameId);
        players.add(player);
        playerIdToGameContext.put(player.getId(), ctx);
        log.info("Speler {} heeft deelgenomen aan spel {}.", player.getId(), gameId);
        return ctx;
    }

    public boolean gameExists(String gameId) {
        return gameIdToGameContext.containsKey(gameId);
    }

    public boolean gameFull(String gameId) {
        List<Player> players = gameIdToPlayers.get(gameId);
        if (players == null) return false;
        return players.size() == PLAYERS_PER_GAME;
    }

    public boolean playerNameTaken(String gameId, String name) {
        List<Player> players = gameIdToPlayers.get(gameId);
        if (players == null) return false;
        return players.size() == 1 && players.getFirst().getName().equalsIgnoreCase(name);
    }

    public GameContext getGameContext(String playerId) {
        return playerIdToGameContext.get(playerId);
    }

    public List<Player> getPlayers(String gameId) {
        return gameIdToPlayers.get(gameId);
    }

}
