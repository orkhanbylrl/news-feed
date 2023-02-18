package app.service;

import app.entity.User;

import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    Optional<User> getUser(String fullName);

}
