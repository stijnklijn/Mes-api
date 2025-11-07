package nl.stijnklijn.mes.exception;

public class GameFullException extends RuntimeException {

    public GameFullException(String gameId) {
        super(String.format("Het spel met code %s is vol.", gameId));
    }

}
