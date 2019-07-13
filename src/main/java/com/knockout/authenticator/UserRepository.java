package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findUserByUserName(String userName);
}