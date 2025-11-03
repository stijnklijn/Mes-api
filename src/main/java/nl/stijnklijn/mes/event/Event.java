package nl.stijnklijn.mes.event;

import lombok.Data;
import nl.stijnklijn.mes.model.Player;

@Data
public abstract class Event {

    private final Player player;
    private final Object payload;

    public Event(Player player, Object payload) {
        this.player = player;
        this.payload = payload;
    }

}
