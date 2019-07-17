package com.knockout.authenticator;

import com.knockout.authenticator.exceptions.UserDoesntExistException;
import com.knockout.authenticator.exceptions.WrongCredentialException;
import com.knockout.authenticator.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDataValidator {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDataValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
        //dummy data for testing purpose
        //        User user = new User();
        //        user.setUserName("user");
        //        user.setPassword(passwordEncoder.encode("pass"));
        //        userRepository.save(user);
    }

    void checkUserCredentials(User user) {
       List<User> userFromDB = userRepository.findUserByUserName(user.getUserName());
        if (userFromDB.size() != 1) {
            throw new UserDoesntExistException();
        } else if (!passwordEncoder.matches(user.getPassword(), userFromDB.get(0).getPassword())) {
            throw new WrongCredentialException();
        }
    }
}
