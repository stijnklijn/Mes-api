package nl.stijnklijn.mes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static nl.stijnklijn.mes.constants.Constants.WEBSOCKET_PUBLISH_BASE_PATH;
import static nl.stijnklijn.mes.constants.Constants.WEBSOCKET_SUBSCRIBE_BASE_PATH;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(WEBSOCKET_SUBSCRIBE_BASE_PATH);
        config.setApplicationDestinationPrefixes(WEBSOCKET_PUBLISH_BASE_PATH);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .addInterceptors(new CustomHandshakeInterceptor());
    }

}