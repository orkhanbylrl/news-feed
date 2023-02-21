package app.service;

import app.entity.User;
import app.repository.UserRepository;
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
    public UserDetails loadUserByUsername(String fullName) throws UsernameNotFoundException {
        Optional<User> user = repo.findUserByFullName(fullName);

        return user.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("username %s doesn't exist", fullName)));
    }

    @Override
    public void saveUser(User user) {
        repo.save(user);
    }

    @Override
    public Optional<User> getUser(String fullName) {
        return repo.findUserByFullName(fullName);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return repo.findUserByEmail(email);
    }

    @Override
    public Boolean isUserExist(String email) {
        return repo.existsUserByEmail(email);
    }


}
