package pl.micede.personalapi.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.micede.personalapi.model.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByLogin(String login);

    Optional<UserModel> findByLogin(String login);
}
