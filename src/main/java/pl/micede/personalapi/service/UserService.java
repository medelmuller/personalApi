package pl.micede.personalapi.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import pl.micede.personalapi.dto.UserReqDto;
import pl.micede.personalapi.model.UserModel;
import pl.micede.personalapi.repository.UserRepository;
import pl.micede.personalapi.utils.exception.UserAlreadyExistsException;
import pl.micede.personalapi.utils.exception.UserNotFoundException;

@Service
@Data
public class UserService {

    private final UserRepository userRepository;
    public UserModel addNewUser(UserReqDto userReqDto) {
        UserModel userModel = new UserModel();
        boolean exists = userRepository.existsByLogin(userReqDto.getLogin());
        if (!exists) {
            userModel.setLogin(userReqDto.getLogin());
            userModel.setPassword(userReqDto.getPassword());
            userModel.setTargets(userReqDto.getTargets());
            return userRepository.save(userModel);
        } else {
            throw new UserAlreadyExistsException(String.format("User by %s login already exists", userReqDto.getLogin()));
        }

    }

    public UserModel getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User by %d ID not found", id)));

    }
}
