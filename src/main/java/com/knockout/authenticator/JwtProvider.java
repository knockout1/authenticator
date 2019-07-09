package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    String generateJwt(User user) {
        return Jwts.builder().setIssuer("knockout.com")
                .setSubject(user.getUserName())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
