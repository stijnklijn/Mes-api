package nl.stijnklijn.mes.state.helper;

import nl.stijnklijn.mes.context.GameContext;
import nl.stijnklijn.mes.enums.MessageType;
import nl.stijnklijn.mes.enums.PlayerState;
import nl.stijnklijn.mes.model.Message;
import nl.stijnklijn.mes.model.Player;

public class AssignBidHelper {

    public void assignBid(GameContext ctx, Player player) {
        ctx.getGame().getPlayer1().setState(PlayerState.AWAIT_BID);
        ctx.getGame().getPlayer2().setState(PlayerState.AWAIT_BID);
        player.setState(PlayerState.DO_BID);
        ctx.broadcast(new Message(MessageType.INFO, String.format("%s mag nu een bod doen.", player.getName())));
        ctx.broadcastGame();
    }

}
