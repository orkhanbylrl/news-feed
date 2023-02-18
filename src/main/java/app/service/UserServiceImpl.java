package app.service;

import app.entity.User;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository repo;

    @Override
    public void saveUser(User user) {

    }

    @Override
    public User getUser(String fullName) {
        return null;
    }
}
