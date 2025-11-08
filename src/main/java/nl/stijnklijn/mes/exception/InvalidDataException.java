package nl.stijnklijn.mes.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException() {
        super("Server heeft ongeldige data ontvangen");
    }

}
