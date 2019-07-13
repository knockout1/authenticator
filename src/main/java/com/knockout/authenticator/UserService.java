package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
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
