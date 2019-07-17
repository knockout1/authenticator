package com.knockout.authenticator;

import com.knockout.authenticator.exceptions.UserDoesntExistException;
import com.knockout.authenticator.exceptions.WrongCredentialException;
import com.knockout.authenticator.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDataValidatorTest {

    private UserRepository userRepository = mock(UserRepository.class);
    private UserDataValidator userDataValidator = new UserDataValidator(userRepository);
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void shouldNotThrowExceptionWhenValidUserProvided() {
        User userFromDB = new User();
        userFromDB.setUserName("user");
        userFromDB.setPassword(passwordEncoder.encode("pass"));
        User providedUser = new User();
        providedUser.setUserName("user");
        providedUser.setPassword("pass");

        when(userRepository.findUserByUserName("user")).thenReturn(Arrays.asList(userFromDB));

        assertDoesNotThrow(() -> userDataValidator.checkUserCredentials(providedUser));
    }

    @Test
    void shouldThrowExceptionWhenTwoTheSameUsersExist() {
        User userFromDB = new User();
        userFromDB.setUserName("user");
        userFromDB.setPassword(passwordEncoder.encode("pass"));
        User providedUser = new User();
        providedUser.setUserName("user");
        providedUser.setPassword("pass");

        when(userRepository.findUserByUserName("user")).thenReturn(Arrays.asList(userFromDB, userFromDB));

        assertThrows(UserDoesntExistException.class, () -> userDataValidator.checkUserCredentials(providedUser));
    }

    @Test
    void shouldThrowUserDoesNotExistWhenProvidedNonExistingUser() {
        User nonexistentUser = new User();
        nonexistentUser.setUserName("user");
        nonexistentUser.setPassword("pass");

        when(userRepository.findUserByUserName("user")).thenReturn(Collections.emptyList());
        assertThrows(UserDoesntExistException.class, () -> userDataValidator.checkUserCredentials(nonexistentUser));
    }

    @Test
    void shouldThrowWrongCredentialExceptiontWhenProvidedInvalidPassword() {
        User userFromDB = new User();
        userFromDB.setUserName("user");
        userFromDB.setPassword(passwordEncoder.encode("pass"));
        User providedUser = new User();
        providedUser.setUserName("user");
        providedUser.setPassword("wrongPass");

        when(userRepository.findUserByUserName("user")).thenReturn(Arrays.asList(userFromDB));

        assertThrows(WrongCredentialException.class, () -> userDataValidator.checkUserCredentials(providedUser));
    }
}