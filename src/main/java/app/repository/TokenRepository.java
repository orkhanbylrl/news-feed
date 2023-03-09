package app.repository;

import app.entity.PassResetToken;
import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<PassResetToken, Integer> {

    @Query("SELECT t.user FROM PassResetToken t WHERE t.token = :token")
    User findUserByToken(@Param("token") String token);

    Optional<PassResetToken> findPassResetTokenByToken(String token);

    @Query("delete from PassResetToken t where t.user = :user")
    void deleteByUser(@Param("user") User user);

}
