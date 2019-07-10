package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
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

    Boolean validateJwt(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt);
            return true;
        } catch (SignatureException e) {
            return false;
        }
    }
}
