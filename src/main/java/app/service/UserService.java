package app.service;

import app.entity.User;

public interface UserService {
    void saveUser(User user);
    User getUser(String fullName);


}
