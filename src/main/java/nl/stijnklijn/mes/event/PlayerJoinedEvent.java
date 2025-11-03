package nl.stijnklijn.mes.event;

import nl.stijnklijn.mes.model.Player;

public class PlayerJoinedEvent extends Event {

    public PlayerJoinedEvent(Player player, String sessionId) {
        super(player, sessionId);
    }

}
