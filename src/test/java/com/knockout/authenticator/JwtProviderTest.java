package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtProviderTest {

    private static User user;
    private String secret = "secretKey";
    private final JwtProvider jwtProvider = new JwtProvider(secret);

    @BeforeAll
    static void setUp() {
        user = new User();
        user.setUserName("user");
        user.setPassword("pass");

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldReturnValidJwtToken() {
        String jwt = jwtProvider.generateJwt(user);
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt).getBody();
        assertEquals(claims.getSubject(), "user");
        assertEquals(claims.getIssuer(), ("knockout.com"));
    }

    @Test
    void shouldReturnTrueWhenValidJwtTokenProvided() {
        String jwt = jwtProvider.generateJwt(user);
        assertTrue(jwtProvider.validateJwt(jwt));
    }

    @Test
    void shouldTrowExceptionWhenMalformedJwtTokenProvided() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrbm9ja291dC5jb20iLCJzdWIiOiJ1c2VyIn0";
        assertThrows(MalformedJwtException.class, () -> jwtProvider.validateJwt(jwt));
    }
}