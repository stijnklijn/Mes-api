package nl.stijnklijn.mes.event;

import nl.stijnklijn.mes.model.Answer;
import nl.stijnklijn.mes.model.Player;

import java.util.List;

public class AnswersSubmittedEvent extends Event {

    public AnswersSubmittedEvent(Player player, List<Answer> answers) {
        super(player, answers);
    }

}
