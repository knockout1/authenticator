package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findUserByUserName(String userName);
}