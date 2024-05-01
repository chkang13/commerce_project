package com.my.apigatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                // application.yml에서 필터 걸어줄 것이기 때문
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().permitAll()
                )
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) // Disable session management
                // REST API이므로 basic auth 및 csrf 보안을 사용하지 않음
                .httpBasic(basicConfigurer -> basicConfigurer.disable())
                .csrf((csrfConfigurer -> csrfConfigurer.disable()))
                .build();
    }
}
