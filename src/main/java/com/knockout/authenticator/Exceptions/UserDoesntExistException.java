package com.knockout.authenticator.Exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserDoesntExistException extends AuthenticationException {

    public UserDoesntExistException(String msg) {
        super(msg);
    }
}
