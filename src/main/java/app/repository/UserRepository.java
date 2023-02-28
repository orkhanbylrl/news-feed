package app.repository;

import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByFullName(String fullName);
    Boolean existsUserByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
