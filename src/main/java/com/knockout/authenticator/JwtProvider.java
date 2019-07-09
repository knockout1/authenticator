package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtProvider {

    private String secret ="secret";

    public String generateJwt(User user){
        return Jwts.builder().setIssuer("knockout.com")
                .setSubject(user.getUserName())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
