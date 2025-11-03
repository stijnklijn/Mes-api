package nl.stijnklijn.mes.state;

import nl.stijnklijn.mes.context.GameContext;
import nl.stijnklijn.mes.enums.MessageType;
import nl.stijnklijn.mes.event.PlayerJoinedEvent;
import nl.stijnklijn.mes.model.Game;
import nl.stijnklijn.mes.model.Message;
import nl.stijnklijn.mes.model.Player;
import nl.stijnklijn.mes.state.helper.NextRoundHelper;

public class AwaitOpponentState implements State<PlayerJoinedEvent> {

    private final NextRoundHelper nextRoundHelper;

    public AwaitOpponentState(NextRoundHelper nextRoundHelper) {
        this.nextRoundHelper = nextRoundHelper;
    }

    @Override
    public State<?> handle(PlayerJoinedEvent e, GameContext ctx) {

        Player player = e.getPlayer();

        if (ctx.getGame() != null) {
            ctx.getGame().setPlayer2(player);
            ctx.getGame().setFirstHand(Math.random() < 0.5 ? ctx.getGame().getPlayer1() : ctx.getGame().getPlayer2());
            nextRoundHelper.nextRound(ctx);
            return ctx.getAwaitAnswersState();
        } else {
            ctx.setGame(new Game(ctx.getQuestions(), player, null, 0, 0, 0, null, false));
            ctx.sendTo(player, new Message(MessageType.INFO, "Nodig de tegenstander uit met de spelcode onderin het scherm."));
            ctx.sendTo(player,
                    new Message(MessageType.GAME_STATE, ctx.toGameDto(player.getId(), ctx.getGame())));
            return this;
        }
    }

}
