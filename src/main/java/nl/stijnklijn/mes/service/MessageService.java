package nl.stijnklijn.mes.service;

import nl.stijnklijn.mes.model.Message;
import nl.stijnklijn.mes.model.Player;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static nl.stijnklijn.mes.constants.Constants.WEBSOCKET_SUBSCRIBE_BASE_PATH;

@Service
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final SessionService sessionService;

    public MessageService(SimpMessagingTemplate messagingTemplate, SessionService sessionService) {
        this.sessionService = sessionService;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendTo(Player player, Message message) {
        messagingTemplate.convertAndSendToUser(player.getId(), WEBSOCKET_SUBSCRIBE_BASE_PATH, message);
    }

    public void broadcast(String gameId, Message message) {
        sessionService.getPlayers(gameId).forEach(player ->
                messagingTemplate.convertAndSendToUser(player.getId(), WEBSOCKET_SUBSCRIBE_BASE_PATH, message));
    }

}
