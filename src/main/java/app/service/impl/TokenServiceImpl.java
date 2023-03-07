package app.service.impl;


import app.entity.PassResetToken;
import app.entity.User;
import app.repository.TokenRepository;
import app.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository repo;


    @Override
    public void createToken(User user, String token) {
        PassResetToken pRToken = new PassResetToken();
        pRToken.setUser(user);
        pRToken.setToken(token);
        repo.save(pRToken);
    }

    @Override
    public boolean isValid(PassResetToken token) {
        return token.getDate().after(new Date());
    }

    @Override
    public User getUser(String token) {
        return repo.findUserByToken(token);
    }

    @Override
    public Optional<PassResetToken> getToken(String token) {
        return repo.findPassResetTokenByToken(token);
    }

    @Override
    public void deleteToken(User user) {
        repo.deleteByUser(user);
    }


}
