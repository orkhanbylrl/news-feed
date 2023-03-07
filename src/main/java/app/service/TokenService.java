package app.service;


import app.entity.PassResetToken;
import app.entity.User;

import java.util.Optional;

public interface TokenService {

    void createToken(User user, String token);

    boolean isValid(PassResetToken token);

    User getUser(String token);

    Optional<PassResetToken> getToken(String token);

    void deleteToken(User user);
}
