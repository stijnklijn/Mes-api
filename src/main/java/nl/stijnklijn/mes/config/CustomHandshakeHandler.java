package nl.stijnklijn.mes.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

import static nl.stijnklijn.mes.constants.Constants.PLAYER_ID_QUERY_STRING;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(
            ServerHttpRequest request,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        final String username = (String) attributes.get(PLAYER_ID_QUERY_STRING);

        return () -> username;
    }

}
