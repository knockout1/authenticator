package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private static User user;
    private final String VALID_JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrbm9ja291dC5jb20iLCJzdWIiOiJ1c2VyIn0.yDKAjP57gJ6G4riqg9CDjq8ZRnYGf8jr_6TErqVMq60";
    private final String INVALID_JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrbm9ja291dC5jb20iVyIn0.yDKAjP57gJ6G4riqg9CDjq8ZRnYGf8jr_6TErqVMq60";
    private JwtProvider jwtProvider = mock(JwtProvider.class);
    private UserDataValidator userDataValidator = mock(UserDataValidator.class);
    private UserService userService = new UserService(jwtProvider, userDataValidator);

    @BeforeAll
    static void setUp() {
        user = new User();
        user.setUserName("user");
        user.setPassword("pass");
    }

    @Test
    void shouldGenerateJwtWhenValidUserProvided() {
        when(jwtProvider.generateJwt(user)).thenReturn(VALID_JWT_TOKEN);

        String jwt = userService.generateJwt(user);
        String secret = "secretKey";
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt).getBody();
        assertEquals(claims.getSubject(), "user");
        assertEquals(claims.getIssuer(), ("knockout.com"));
    }

    @Test
    void shouldReturnTrueWhenValidJwtProvided() {
        when(jwtProvider.validateJwt(VALID_JWT_TOKEN)).thenReturn(true);
        assertTrue(userService.validateJwt(VALID_JWT_TOKEN));
    }

    @Test
    void shouldReturnFalseWhenInvalidJwtProvided() {
        when(jwtProvider.validateJwt(INVALID_JWT_TOKEN)).thenReturn(false);
        assertFalse(userService.validateJwt(INVALID_JWT_TOKEN));
    }
}