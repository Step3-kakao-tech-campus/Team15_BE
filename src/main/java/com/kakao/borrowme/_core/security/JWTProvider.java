package com.kakao.borrowme._core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kakao.borrowme.user.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {
    public static final Long ACCESS_EXP = 1000L * 60 * 60 * 48;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
    public static final String SECRET = "MySecretKey";

    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject("AccessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_EXP))
                .withClaim("id", user.getId())
                .withClaim("nickname", user.getNickname())
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt.replace(JWTProvider.TOKEN_PREFIX, ""));
        return decodedJWT;
    }
}
