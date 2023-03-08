package app.service;

import app.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    Map<String, Object> forToken = new HashMap<>();
    void saveUser(User user);

    Optional<User> getUser(String email);
    UserDetails loadUserByUsername(String email);

    Boolean isUserExist(String email);


}
