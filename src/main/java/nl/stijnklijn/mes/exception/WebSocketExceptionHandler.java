package nl.stijnklijn.mes.exception;

import lombok.extern.slf4j.Slf4j;
import nl.stijnklijn.mes.enums.MessageType;
import nl.stijnklijn.mes.model.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

import static nl.stijnklijn.mes.constants.Constants.WEBSOCKET_SUBSCRIBE_BASE_PATH;

@Slf4j
@ControllerAdvice
public class WebSocketExceptionHandler {

    @MessageExceptionHandler
    @SendToUser(WEBSOCKET_SUBSCRIBE_BASE_PATH)
    public Message handleInvalidDataException(InvalidDataException e) {
        log.warn(e.getMessage());
        return new Message(MessageType.ERROR, e.getMessage());
    }

}
