package com.my.apigatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .securityContextRepository(new WebSessionServerSecurityContextRepository()) // 세션 대신 WebSession 사용
                // application.yml에서 필터 걸어줄 것이기 때문
                .authorizeExchange(exchanges -> exchanges
                                .anyExchange().permitAll()
                )
                // REST API이므로 basic auth 및 csrf 보안을 사용하지 않음
                .httpBasic(basicConfigurer -> basicConfigurer.disable())
                .csrf((csrfConfigurer -> csrfConfigurer.disable()))
                .build();
    }
}
