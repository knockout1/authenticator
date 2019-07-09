package com.knockout.authenticator.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User doesn't exist")
public class UserDoesntExistException extends AuthenticationException {

    public UserDoesntExistException() {
        super("User doesn't exist");
    }
}
