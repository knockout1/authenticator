package com.knockout.authenticator;

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

    public Boolean userExist(User user){
       List<User> userFromDB = userRepository.findUserByUserName(user.getUserName());
       return (passwordEncoder.matches(user.getPassword(), userFromDB.get(1).getPassword()));
    }
}
