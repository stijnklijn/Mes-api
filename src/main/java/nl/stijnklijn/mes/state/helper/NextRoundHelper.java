package nl.stijnklijn.mes.state.helper;

import nl.stijnklijn.mes.context.GameContext;
import nl.stijnklijn.mes.enums.MessageType;
import nl.stijnklijn.mes.enums.PlayerState;
import nl.stijnklijn.mes.model.Message;
import nl.stijnklijn.mes.model.Player;

import java.util.Timer;
import java.util.TimerTask;

import static nl.stijnklijn.mes.constants.Constants.*;

public class NextRoundHelper {

    public void nextRound(GameContext ctx) {
        resetRoundState(ctx);

        if (ctx.getGame().getRound().equals(MAX_ROUNDS)) {
            endGame(ctx);
            return;
        }

        ctx.getGame().setRound(ctx.getGame().getRound() + 1);
        ctx.getGame().getPlayer1().setState(PlayerState.ANSWER_QUESTIONS);
        ctx.getGame().getPlayer2().setState(PlayerState.ANSWER_QUESTIONS);
        ctx.getGame().setRound(ctx.getGame().getRound());
        putStake(ctx, ctx.getGame().getRound());

        if (ctx.getGame().getRound() == 1) {
            ctx.broadcast(new Message(MessageType.INFO, String.format("%s speelt tegen %s. Succes!",
                    ctx.getGame().getPlayer1().getName(), ctx.getGame().getPlayer2().getName())));
        }
        ctx.broadcast(new Message(MessageType.INFO, String.format("Ronde %d begint. Beide spelers leggen %d euro in de pot.",
                ctx.getGame().getRound(), ctx.getGame().getStake())));
        ctx.broadcastGame();
        int questionStartIndex = (ctx.getGame().getRound() - 1) * QUESTIONS_PER_ROUND;
        ctx.broadcast(new Message(MessageType.QUESTIONS, ctx.getGame().getQuestions().subList(questionStartIndex, questionStartIndex + QUESTIONS_PER_ROUND)));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ctx.getGame().getPlayer1().setState(PlayerState.SUBMIT_ANSWERS);
                ctx.getGame().getPlayer2().setState(PlayerState.SUBMIT_ANSWERS);
                ctx.broadcastGame();
            }
        }, (COUNT_DOWN + ROUND_TIME) * 1000L);
    }

    private void putStake(GameContext ctx, int round) {
        int stake = INITIAL_STAKE * (int) Math.pow(2, round - 1);
        ctx.getGame().getPlayer1().setScore(ctx.getGame().getPlayer1().getScore() - stake);
        ctx.getGame().getPlayer2().setScore(ctx.getGame().getPlayer2().getScore() - stake);
        ctx.getGame().setBank(ctx.getGame().getBank() + 2 * stake);
        ctx.getGame().setStake(stake);
    }

    private void resetRoundState(GameContext ctx) {
        ctx.getGame().resetAnswers();
        ctx.getGame().resetBids();
        ctx.getGame().setBank(0);
        ctx.getGame().setFeedbackReturned(false);
        ctx.getGame().setFirstHand(ctx.getGame().getFirstHand().equals(ctx.getGame().getPlayer1()) ?
                ctx.getGame().getPlayer2() : ctx.getGame().getPlayer1());
    }

    private void endGame(GameContext ctx) {
        Player p1 = ctx.getGame().getPlayer1();
        Player p2 = ctx.getGame().getPlayer2();
        p1.setState(PlayerState.END_GAME);
        p2.setState(PlayerState.END_GAME);
        ctx.broadcastGame();
        Player winner = p1.getScore().equals(p2.getScore()) ? null : (p1.getScore() > p2.getScore() ? p1 : p2);
        if (winner != null) {
            ctx.broadcast(new Message(MessageType.INFO,
                    String.format("%s heeft gewonnen!", winner.getName())));
        } else {
            ctx.broadcast(new Message(MessageType.INFO,
                    "Het is gelijkspel!"));
        }
    }

}
