package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private JwtProvider jwtProvider;
    private UserDataValidator userDataValidator;

    public UserController(JwtProvider jwtProvider, UserDataValidator userDataValidator) {
        this.jwtProvider = jwtProvider;
        this.userDataValidator = userDataValidator;
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody User user) {
        userDataValidator.checkUserCredentials(user);
        return ResponseEntity.ok(jwtProvider.generateJwt(user));
    }
}
