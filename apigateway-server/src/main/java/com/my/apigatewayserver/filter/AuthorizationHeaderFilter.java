package com.my.apigatewayserver.filter;

import com.my.apigatewayserver.Token.RedisUtilService;
import com.my.apigatewayserver.Token.TokenValid;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    private final RedissonClient redissonClient;
    private final TokenValid tokenValid;

    public AuthorizationHeaderFilter(RedissonClient redissonClient, TokenValid tokenValid) {
        super(Config.class);
        this.redissonClient = redissonClient;
        this.tokenValid = tokenValid;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // JWT 토큰 판별
            String token = extractJwtTokenFromRequest(exchange);

            int authCode = tokenValid.validateToken(token);

            if (authCode == 401) {
                return onError(exchange, "Expired Token", HttpStatus.UNAUTHORIZED);
            } else if (authCode == 403) {
                return onError(exchange, "No authorization header", HttpStatus.FORBIDDEN);
            }

            // 레디스 블랙리스트 확인
            if (redissonClient.getKeys().countExists(token) > 0) {
                return onError(exchange, "Logout Token", HttpStatus.UNAUTHORIZED);
            }
            else {
            String memberId = tokenValid.getMemberId(token);
            exchange.getRequest().mutate().header("memberId", memberId);
            return chain.filter(exchange);
            }
        };
    }

    private String extractJwtTokenFromRequest(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String authorizationHeader = headers.get(HttpHeaders.AUTHORIZATION).get(0);
        String token = authorizationHeader.substring(7);

        return token;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }
}
