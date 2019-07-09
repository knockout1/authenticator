package com.knockout.authenticator;

import com.knockout.authenticator.Exceptions.UserDoesntExistException;
import com.knockout.authenticator.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private UserRepository userRepository;

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody User user){
        UserDataValidator userDataValidator = new UserDataValidator(userRepository);
        JwtProvider jwtProvider = new JwtProvider();
        if (userDataValidator.userExist(user)) {
            return ResponseEntity.ok(jwtProvider.generateJwt(user));
        } else{
            throw new UserDoesntExistException("Wrong credential provided");
        }
    }
}
