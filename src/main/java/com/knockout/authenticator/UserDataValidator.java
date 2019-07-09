package com.knockout.authenticator;

import com.knockout.authenticator.Exceptions.UserDoesntExistException;
import com.knockout.authenticator.Exceptions.WrongCredentialException;
import com.knockout.authenticator.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDataValidator {

    public UserDataValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
        //dummy data for testing purpose
        User user = new User();
        user.setUserName("user");
        user.setPassword(passwordEncoder.encode("pass"));
        userRepository.save(user);
    }

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    void checkUserCredentials(User user) {
       List<User> userFromDB = userRepository.findUserByUserName(user.getUserName());
        if (userFromDB.size() != 1) {
            throw new UserDoesntExistException();
        } else if (!passwordEncoder.matches(user.getPassword(), userFromDB.get(0).getPassword())) {
            throw new WrongCredentialException();
        }
    }
}
