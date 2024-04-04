package pl.micede.personalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.micede.personalapi.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByLogin(String login);

}
