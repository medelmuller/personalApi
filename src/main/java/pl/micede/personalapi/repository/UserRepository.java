package pl.micede.personalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.micede.personalapi.model.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByLogin(String login);


    /**
     * Finds UserModel entity by its login;
     *
     * @param login The unique login of the user;
     * @return UserModel Object found by its login;
     */
    Optional<UserModel> findByLogin(String login);


}
