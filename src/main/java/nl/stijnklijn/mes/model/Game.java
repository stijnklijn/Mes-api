package nl.stijnklijn.mes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Game {

    private List<Question> questions;
    private Player player1;
    private Player player2;
    private Integer bank;
    private Integer round;
    private Integer stake;
    private Player firstHand;
    private boolean feedbackReturned;

    public boolean bothPlayersAnswered() {
        return player1.getAnswers() != null && player2.getAnswers() != null;
    }

    public boolean bothPlayersBid() {
        return player1.getBid() != null && player2.getBid() != null;
    }

    public void resetAnswers() {
        if (player1 == null || player2 == null) {
            return;
        }
        player1.setAnswers(null);
        player2.setAnswers(null);
    }

    public void resetBids() {
        if (player1 == null || player2 == null) {
            return;
        }
        player1.setBid(null);
        player2.setBid(null);
    }

}
