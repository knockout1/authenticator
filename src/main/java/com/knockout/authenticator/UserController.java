package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody User user) {
        return ResponseEntity.ok(userService.generateJwt(user));
    }

    @PostMapping("/validateJwt")
    public ResponseEntity validateJwt(@RequestBody String jwt) {
        if (userService.validateJwt(jwt)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
