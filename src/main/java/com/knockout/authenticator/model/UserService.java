package com.knockout.authenticator.model;

import com.knockout.authenticator.JwtProvider;
import com.knockout.authenticator.UserDataValidator;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private JwtProvider jwtProvider;
    private UserDataValidator userDataValidator;

    public UserService(JwtProvider jwtProvider, UserDataValidator userDataValidator) {
        this.jwtProvider = jwtProvider;
        this.userDataValidator = userDataValidator;
    }

    public String generateJwt(User user) {
        userDataValidator.checkUserCredentials(user);
        return jwtProvider.generateJwt(user);
    }

    public Boolean validateJwt(String jwt) {
        return jwtProvider.validateJwt(jwt);
    }

}
