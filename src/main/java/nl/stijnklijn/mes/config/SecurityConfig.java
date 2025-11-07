package nl.stijnklijn.mes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

import static nl.stijnklijn.mes.constants.Constants.ERROR_HEADER;
import static nl.stijnklijn.mes.constants.Constants.GAME_ID_HEADER;

@Configuration
public class SecurityConfig {

    @Value("${mes.client.urls}")
    private String[] CLIENT_URLS;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(customizer -> customizer.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(CLIENT_URLS));
                    config.setAllowedMethods(List.of("GET", "OPTIONS"));
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(List.of(GAME_ID_HEADER, ERROR_HEADER));
                    return config;
                }))
                .authorizeHttpRequests(request -> request
                        .anyRequest().permitAll())
                .build();
    }

}
