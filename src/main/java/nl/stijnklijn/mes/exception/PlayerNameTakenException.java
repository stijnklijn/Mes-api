package nl.stijnklijn.mes.exception;

public class PlayerNameTakenException extends RuntimeException {

    public PlayerNameTakenException(String gameId, String playerName) {
        super(String.format("Er zit al een speler met naam %s in het spel met code %s.", playerName, gameId));
    }

}
