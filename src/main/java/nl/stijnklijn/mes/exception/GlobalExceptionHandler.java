package nl.stijnklijn.mes.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handleNoSuchGameException(NoSuchGameException e) {
        return getExceptionResponse(e, 404);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handleGameFullException(GameFullException e) {
        return getExceptionResponse(e, 403);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handlePlayerNameTakenException(PlayerNameTakenException e) {
        return getExceptionResponse(e, 409);
    }

    private ResponseEntity<GlobalExceptionResponse> getExceptionResponse(Exception e, int status) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(new GlobalExceptionResponse(HttpStatusCode.valueOf(status), e.getMessage()),
                HttpStatusCode.valueOf(status));
    }

}
