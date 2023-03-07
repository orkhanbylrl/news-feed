package app.service.impl;

import app.entity.User;
import app.repository.UserRepository;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository repo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repo.findUserByEmail(email);

        return user.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("user %s doesn't exist", email)));

    }

    @Override
    public void saveUser(User user) {
        repo.save(user);
    }

    @Override
    public Optional<User> getUser(String email) {
        return repo.findUserByEmail(email);
    }

    @Override
    public Boolean isUserExist(String email) {
        return repo.existsUserByEmail(email);
    }


}
