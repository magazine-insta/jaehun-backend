package pbl.magazine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pbl.magazine.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
