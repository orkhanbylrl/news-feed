package app.service;

import app.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    Optional<User> getUser(String fullName);
    UserDetails loadUserByUsername(String fullName);
    Optional<User> getUserByEmail(String email);
    Boolean isUserExist(String email);

}
