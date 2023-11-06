package com.kakao.borrowme._core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kakao.borrowme.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class JWTProvider {
    public static Long ACCESS_EXP;
    public static String TOKEN_PREFIX;
    public static String HEADER;
    public static String SECRET;

    public JWTProvider(
            @Value("${security.jwt.access_exp}") Long ACCESS_EXP,
            @Value("${security.jwt.token_prefix} ") String TOKEN_PREFIX,
            @Value("${security.jwt.header}") String HEADER,
            @Value("${security.jwt.secret}") String SECRET) {
        this.ACCESS_EXP = ACCESS_EXP;
        this.TOKEN_PREFIX = TOKEN_PREFIX;
        this.HEADER = HEADER;
        this.SECRET = SECRET;
    }

    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(Timestamp.valueOf(LocalDateTime.now().plusSeconds(ACCESS_EXP)))
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET)).build()
                .verify(jwt.replace(JWTProvider.TOKEN_PREFIX, ""));
        return decodedJWT;
    }
}
