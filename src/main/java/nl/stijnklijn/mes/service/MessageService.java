package nl.stijnklijn.mes.service;

import nl.stijnklijn.mes.model.Message;
import nl.stijnklijn.mes.model.Player;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final SessionService sessionService;

    public MessageService(SimpMessagingTemplate messagingTemplate, SessionService sessionService) {
        this.sessionService = sessionService;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendTo(Player player, Message message) {
        messagingTemplate.convertAndSendToUser(player.getId(), "/queue", message);
    }

    public void broadcast(String gameId, Message message) {
        sessionService.getPlayers(gameId).forEach(player ->
                messagingTemplate.convertAndSendToUser(player.getId(), "/queue", message));
    }

}
