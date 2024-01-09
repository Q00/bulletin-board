package com.example.jpa.common.guard;

import com.example.jpa.common.exception.AuthFailException;
import com.example.jpa.common.exception.BizException;
import com.example.jpa.user.entity.User;
import com.example.jpa.common.model.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    public JwtTokenProvider(@Value("${spring.security.jwt.secret}") String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(User user) {
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = Jwts.builder().setSubject(user.getEmail())
                .claim("auth", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder().setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder().grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("validateToken error : {}", e.getMessage());
            throw new AuthFailException("유효하지 않은 토큰입니다.");
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        if (claims.get("auth") == null) {
            throw new AuthFailException("권한 정보가 없습니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(",")).map(SimpleGrantedAuthority::new).toList();

        UserDetails principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
