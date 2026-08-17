package com.cleverson.help_desk.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cleverson.help_desk.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JWTService {

    @Value("${auth.jwt.secret-key}")
    private String JWT_SECRET_KEY;

    public String generateToken(User user) {
    try {
        Algorithm algorithm = Algorithm.HMAC256(this.JWT_SECRET_KEY);
        String token = JWT.create().withSubject(user.id().toString()).withExpiresAt(Instant.now().plusSeconds(3600)).sign(algorithm);

        return token;

    } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while creating token...", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.JWT_SECRET_KEY);

            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }
}
