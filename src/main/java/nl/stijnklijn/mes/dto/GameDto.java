package nl.stijnklijn.mes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.stijnklijn.mes.model.Player;

@Data
@AllArgsConstructor
public class GameDto {

    private Player self;
    private Player opponent;
    private Integer bank;
    private Integer round;
    private Integer stake;

}
