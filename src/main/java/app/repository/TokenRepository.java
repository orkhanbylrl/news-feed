package app.repository;

import app.entity.PasswordResetToken;
import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    @Query("SELECT t.user FROM PasswordResetToken t WHERE t.token = :token")
    User findUserByToken(@Param("token") String token);

    Optional<PasswordResetToken> findPasswordResetTokenByToken(String token);

    void deleteByUser(User user);

}
