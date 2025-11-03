package nl.stijnklijn.mes.state;

import nl.stijnklijn.mes.context.GameContext;
import nl.stijnklijn.mes.event.AnswersSubmittedEvent;
import nl.stijnklijn.mes.model.Answer;
import nl.stijnklijn.mes.state.helper.AssignBidHelper;

import java.util.List;
import java.util.stream.Collectors;

public class AwaitAnswersState implements State<AnswersSubmittedEvent> {

    private final AssignBidHelper assignBidHelper;

    public AwaitAnswersState(AssignBidHelper assignBidHelper) {
        this.assignBidHelper = assignBidHelper;
    }

    @Override
    public State<?> handle(AnswersSubmittedEvent e, GameContext ctx) {

        List<Answer> answers = (List<Answer>) e.getPayload();

        answers.forEach(a -> ctx.getGame().getQuestions().stream()
                .filter(q -> q.getId().equals(a.getId()))
                .findFirst()
                .ifPresent(q -> {
                    a.setCorrectAnswers(q.getCorrectAnswers());
                    a.setCorrect(q.getCorrectAnswers().stream()
                            .map(String::toLowerCase)
                            .collect(Collectors.toSet())
                            .contains(a.getContent().toLowerCase()));
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
