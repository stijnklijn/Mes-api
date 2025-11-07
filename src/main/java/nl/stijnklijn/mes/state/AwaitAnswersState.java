package nl.stijnklijn.mes.state;

import nl.stijnklijn.mes.context.GameContext;
import nl.stijnklijn.mes.event.AnswersSubmittedEvent;
import nl.stijnklijn.mes.model.Answer;
import nl.stijnklijn.mes.state.helper.AssignBidHelper;
import nl.stijnklijn.mes.state.helper.EvaluateAnswerHelper;

import java.util.List;

public class AwaitAnswersState implements State<AnswersSubmittedEvent> {

    private final EvaluateAnswerHelper evaluateAnswerHelper;
    private final AssignBidHelper assignBidHelper;

    public AwaitAnswersState(EvaluateAnswerHelper evaluateAnswerHelper, AssignBidHelper assignBidHelper) {
        this.evaluateAnswerHelper = evaluateAnswerHelper;
        this.assignBidHelper = assignBidHelper;
    }

    @Override
    public State<?> handle(AnswersSubmittedEvent e, GameContext ctx) {

        List<Answer> answers = (List<Answer>) e.getPayload();

        answers.forEach(a -> ctx.getGame().getQuestions().stream()
                .filter(q -> q.id().equals(a.getId()))
                .findFirst()
                .ifPresent(q -> {
                    evaluateAnswerHelper.evaluateAnswer(q, a);
                }));

        ctx.getSelf(e.getPlayer().getId()).setAnswers(answers);

        if (ctx.getGame().bothPlayersAnswered()) {
            assignBidHelper.assignBid(ctx, ctx.getGame().getFirstHand());
            return ctx.getAwaitBidState();
        } else {
            return this;
        }
    }

}
