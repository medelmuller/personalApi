package pl.micede.personalapi.service;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.micede.personalapi.dto.UserReqDto;
import pl.micede.personalapi.model.UserModel;
import pl.micede.personalapi.repository.UserRepository;
import pl.micede.personalapi.utils.exception.UserAlreadyExistsException;
import pl.micede.personalapi.utils.exception.UserNotFoundException;

import java.util.Optional;

@Service
@Data
public class UserService {

    private final UserRepository userRepository;

    /**
     * Creates a new user using details provided in a UserReqDto object.
     *
     * @param userReqDto Data Transfer Object containing user details.
     * @return The saved UserModel entity.
     * @throws UserAlreadyExistsException if UserModel already exists in database.
     */
    public UserModel addNewUser(UserReqDto userReqDto) {
        UserModel userModel = new UserModel();
        boolean exists = userRepository.existsByLogin(userReqDto.getLogin());
        if (!exists) {
            userModel.setLogin(userReqDto.getLogin());
            userModel.setPassword(userReqDto.getPassword());
            return userRepository.save(userModel);
        } else {
            throw new UserAlreadyExistsException(String.format("User by %s login already exists", userReqDto.getLogin()));
        }

    }

    /**
     * Retrieves a specific User by its ID.
     *
     * @param id The ID of the user to be found.
     * @throws UserNotFoundException if user by its ID could not be found.
     * @return A specific UserModel found by ID.
     */
    public UserModel getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User by %d ID not found", id)));
    }

    /**
     * Updates an existing User by its login with new password in a UserModel object.
     *
     * @param login The login of the user to be updated.
     * @param password New password of the user.
     * @throws UserNotFoundException if user could not be found by the login.
     * @return The updated UserModel entity.
     */
    public UserModel updateUserPasswordByLogin(String login, String password) {
        Optional<UserModel> userByLogin = userRepository.findByLogin(login);
        userByLogin.ifPresent(u -> u.setPassword(password));
        return userByLogin
                .orElseThrow(() -> new UserNotFoundException(String.format("User with %s login not found", login)));
    }

    /**
     * Deletes a User from the database.
     *
     * @param id The ID of the user to be deleted.
     * @throws UserNotFoundException if user could not be found by the login.
     */
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    /**
     * Checks if the user exists in the database and can log in to api.
     *
     * @param dto request Data Transfer Object of the user to be found.
     * @throws UserNotFoundException if user could not be found by the login.
     * @return returns access to log in, if passwords from request and database are the same.
     */

    public boolean authenticateUser(UserReqDto dto) {
        UserModel byLogin = userRepository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with %s login not found", dto.getLogin())));
        return dto.getPassword().equals(byLogin.getPassword());
    }

    /**
     * Adds InMemoryUser as UserModel object to database.
     *
     * @param admin In memory details of the user to be added.
     */
    public void saveInMemoryUsers(UserDetails admin) {
        UserModel adminModel = new UserModel();
        adminModel.setLogin(admin.getUsername());
        adminModel.setPassword(admin.getPassword());
        userRepository.save(adminModel);
    }
}
