package nl.stijnklijn.mes.event;

import nl.stijnklijn.mes.model.Bid;
import nl.stijnklijn.mes.model.Player;

public class BidSubmittedEvent extends Event {

    public BidSubmittedEvent(Player player, Bid bid) {
        super(player, bid);
    }

}
