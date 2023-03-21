package com.ntnu.idi.obligfour.repository;

import com.ntnu.idi.obligfour.model.User;

import java.util.Optional;

public interface UserRepositoryInterface {
    void save(User user);
    Optional<User> findByID(long id);

    Optional<User> findByUsername(String username);

    boolean checkPassword(String username, String password);
}
