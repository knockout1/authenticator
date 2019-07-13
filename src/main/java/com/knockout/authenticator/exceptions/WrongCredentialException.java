package com.knockout.authenticator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Wrong Credential Provided")
public class WrongCredentialException extends AuthenticationException {

    public WrongCredentialException() {
        super("Wrong Credential Provided");
    }
}
