package nl.stijnklijn.mes.mapper;

import nl.stijnklijn.mes.dto.GameDto;
import nl.stijnklijn.mes.model.Game;
import nl.stijnklijn.mes.model.Player;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameDto toGameDto(String playerId, Game game) {
        boolean isPlayer1 = game.getPlayer1().getId().equals(playerId);

        Player self = isPlayer1 ? game.getPlayer1() : game.getPlayer2();
        Player opponent = isPlayer1 ? game.getPlayer2() : game.getPlayer1();

        return new GameDto(self, opponent, game.getBank(), game.getRound(), game.getStake());
    }

}
