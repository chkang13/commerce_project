package com.my.orderservice.security;

import com.my.coreservice.global.common.BaseException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.my.coreservice.global.common.BaseResponseStatus.AUTHORITY_INVALID;


@Slf4j
@Component
public class TokenProvider {

    private final Key key;

    // secret 값 가져와서 key 암호화
    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDTO createAccessToken(Authentication authentication) {

        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        String accessToken =  Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(
                        new Date(now + 86400000)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    public Authentication getAuthentication(String token) {

        Claims claims = getClaimsFromToken(token);

        if (claims.get("auth") == null) {
            throw new BaseException(AUTHORITY_INVALID);
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication return
        // UserDetails: interface, User: UserDetails를 구현한 class
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }

        return false;
    }

    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Long getExpiredTime(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }


}
