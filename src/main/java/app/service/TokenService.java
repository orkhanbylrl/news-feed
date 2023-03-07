package app.service;


import app.entity.PasswordResetToken;
import app.entity.User;

import java.util.Optional;

public interface TokenService {

    void createToken(User user, String token);

    boolean isValid(PasswordResetToken token);

    User getUser(String token);
}
