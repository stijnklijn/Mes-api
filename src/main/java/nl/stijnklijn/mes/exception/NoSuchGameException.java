package nl.stijnklijn.mes.exception;

public class NoSuchGameException extends RuntimeException {

    public NoSuchGameException(String gameId) {
        super(String.format("Er bestaat geen spel met code %s.", gameId));
    }

}
