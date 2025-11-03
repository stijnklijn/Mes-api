package nl.stijnklijn.mes.model;

import lombok.Data;
import nl.stijnklijn.mes.enums.PlayerState;

import java.util.List;

import static nl.stijnklijn.mes.constants.Constants.START_SCORE;

@Data
public class Player {

    private String id;
    private String name;
    private Integer score;
    private List<Answer> answers;
    private Integer bid;
    private PlayerState state;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.score = START_SCORE;
        this.state = PlayerState.AWAIT_OPPONENT;
    }

}