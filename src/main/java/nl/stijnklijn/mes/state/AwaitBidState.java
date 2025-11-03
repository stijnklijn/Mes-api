package nl.stijnklijn.mes.state;

import nl.stijnklijn.mes.context.GameContext;
import nl.stijnklijn.mes.enums.MessageType;
import nl.stijnklijn.mes.event.BidSubmittedEvent;
import nl.stijnklijn.mes.model.Answer;
import nl.stijnklijn.mes.model.Bid;
import nl.stijnklijn.mes.model.Message;
import nl.stijnklijn.mes.model.Player;
import nl.stijnklijn.mes.state.helper.AssignBidHelper;
import nl.stijnklijn.mes.state.helper.NextRoundHelper;

public class AwaitBidState implements State<BidSubmittedEvent> {

    private final NextRoundHelper nextRoundHelper;
    private final AssignBidHelper assignBidHelper;

    public AwaitBidState(NextRoundHelper nextRoundHelper, AssignBidHelper assignBidHelper) {
        this.nextRoundHelper = nextRoundHelper;
        this.assignBidHelper = assignBidHelper;
    }

    @Override
    public State<?> handle(BidSubmittedEvent e, GameContext ctx) {

        Bid bid = (Bid)e.getPayload();

        Player self = ctx.getSelf(e.getPlayer().getId());
        Player opponent = ctx.getOpponent(e.getPlayer().getId());

        if (bid.getAmount() == 0) {
            ctx.broadcast(new Message(MessageType.INFO, String.format("%s heeft gepast. De pot gaat naar %s!",
                    self.getName(), opponent.getName())));
            payoutBank(ctx, opponent);
            returnFeedback(ctx);

            nextRoundHelper.nextRound(ctx);
            return ctx.getAwaitAnswersState();
        }

        processBid(ctx, bid, self);
        ctx.broadcast(new Message(MessageType.INFO, String.format("%s heeft %d euro geboden.",
                self.getName(), bid.getAmount())));

        if (ctx.getGame().bothPlayersBid()) {
            if (ctx.getGame().isFeedbackReturned()) {
                resolveBids(ctx);
                return ctx.getAwaitAnswersState();
            } else {
                ctx.getGame().resetBids();
                returnFeedback(ctx);
                assignBidHelper.assignBid(ctx, ctx.getGame().getFirstHand());
            }
        } else {
            assignBidHelper.assignBid(ctx, opponent);
        }
        return this;
    }

    private void processBid(GameContext ctx, Bid bid, Player player) {
        player.setBid(bid.getAmount());
        player.setScore(player.getScore() - bid.getAmount());
        ctx.getGame().setBank(ctx.getGame().getBank() + bid.getAmount());
    }

    private void resolveBids(GameContext ctx) {
        Player p1 = ctx.getGame().getPlayer1();
        Player p2 = ctx.getGame().getPlayer2();
        long p1Correct = p1.getAnswers().stream().filter(Answer::getCorrect).count();
        long p2Correct = p2.getAnswers().stream().filter(Answer::getCorrect).count();
        Player winner = p1Correct == p2Correct ? null : (p1Correct > p2Correct ? p1 : p2);
        payoutBank(ctx, winner);

        String resolveBidsMessage = String.format("%s heeft %d %s goed en %s heeft %d %s goed.",
                p1.getName(), p1Correct, p1Correct == 1 ? "vraag" : "vragen",
                p2.getName(), p2Correct, p2Correct == 1 ? "vraag" : "vragen");

        if (winner != null) {
            ctx.broadcast(new Message(MessageType.INFO,
                    String.format("%s De pot gaat naar %s! ", resolveBidsMessage, winner.getName())));
        } else {
            ctx.broadcast(new Message(MessageType.INFO,
                    String.format("%s De pot wordt verdeeld.", resolveBidsMessage)));
        }
        nextRoundHelper.nextRound(ctx);
    }

    private void payoutBank(GameContext ctx, Player winner) {
        if (winner != null) {
            winner.setScore(winner.getScore() + ctx.getGame().getBank());
        } else {
            ctx.getGame().getPlayer1().setScore(ctx.getGame().getPlayer1().getScore() + ctx.getGame().getBank() / 2);
            ctx.getGame().getPlayer2().setScore(ctx.getGame().getPlayer2().getScore() + ctx.getGame().getBank() / 2);
        }
    }

    private void returnFeedback(GameContext ctx) {
        ctx.sendTo(ctx.getGame().getPlayer1(), new Message(MessageType.FEEDBACK, ctx.getGame().getPlayer1().getAnswers()));
        ctx.sendTo(ctx.getGame().getPlayer2(), new Message(MessageType.FEEDBACK, ctx.getGame().getPlayer2().getAnswers()));
        ctx.getGame().setFeedbackReturned(true);
    }

}
