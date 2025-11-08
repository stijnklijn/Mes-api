package nl.stijnklijn.mes.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<RestExceptionResponse> handleNoSuchGameException(NoSuchGameException e) {
        return getExceptionResponse(e, 404);
    }

    @ExceptionHandler
    public ResponseEntity<RestExceptionResponse> handleGameFullException(GameFullException e) {
        return getExceptionResponse(e, 403);
    }

    @ExceptionHandler
    public ResponseEntity<RestExceptionResponse> handlePlayerNameTakenException(PlayerNameTakenException e) {
        return getExceptionResponse(e, 409);
    }

    private ResponseEntity<RestExceptionResponse> getExceptionResponse(Exception e, int status) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(new RestExceptionResponse(HttpStatusCode.valueOf(status), e.getMessage()),
                HttpStatusCode.valueOf(status));
    }

}
