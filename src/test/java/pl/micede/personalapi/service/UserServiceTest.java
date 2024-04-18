package pl.micede.personalapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.micede.personalapi.dto.HabitReadDto;
import pl.micede.personalapi.dto.UserReqDto;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.model.UserModel;
import pl.micede.personalapi.repository.UserRepository;
import pl.micede.personalapi.utils.exception.UserAlreadyExistsException;
import pl.micede.personalapi.utils.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserReqDto reqDto;

    @BeforeEach
    void setUp() {
        reqDto = UserReqDto.builder()
                .login("String")
                .password("string1")
                .build();
    }

    @Test
    void addNewUser_shouldAddNewUserToDb() {
        //given //when
        when(userRepository.existsByLogin(reqDto.getLogin())).thenReturn(false);
        userService.addNewUser(reqDto);
        //then
        verify(userRepository).save(any(UserModel.class));
    }

    @Test
    void addNewUser_shouldThrowException() {
        //when
        when(userRepository.existsByLogin(reqDto.getLogin())).thenReturn(true);

        //then
        assertThrows(UserAlreadyExistsException.class, () -> userService.addNewUser(reqDto));
    }

    @Test
    void getUserById_shouldGetUserById() {
        //given
        Long id = 1L;
        UserModel userModel = getUserModel();
        //when
        when(userRepository.findById(id)).thenReturn(Optional.of(userModel));
        UserModel result = userService.getUserById(id);
        //given
        assertEquals(userModel.getId(), result.getId());
    }

    @Test
    void getUserById_shouldThrowException() {
        //given
        Long id = 1L;
        //when
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        //given
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));
    }

    @Test
    void updateUserPasswordByLogin_shouldUpdatePasswordById() {
        //given
        String login = "String";
        String newPassword = "New password1";
        UserModel userModel = getUserModel();
        //when
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userModel));
        UserModel result = userService.updateUserPasswordByLogin(login, newPassword);
        //then
        assertEquals(newPassword, result.getPassword());
    }

    @Test
    void updateUserPasswordByLogin_shouldThrowException() {
        //given
        String login = "String";
        String newPassword = "New password1";
        //when
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());
        //then
        assertThrows(UserNotFoundException.class, () -> userService.updateUserPasswordByLogin(login, newPassword));
    }

    @Test
    void deleteUserById_shouldDeleteUserById() {
        //given
        Long id = 1L;
        //when
        when(userRepository.existsById(id)).thenReturn(true);
        userService.deleteUserById(id);
        //given
        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteUserById_shouldThrowException() {
        //given
        Long id = 1L;
        //when
        when(userRepository.existsById(id)).thenReturn(false);
        //given
        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(id));
    }

    @Test
    void authenticateUser_shouldAuthenticateUser() {
        //given
        UserModel userModel = getUserModel();
        //when
        when(userRepository.findByLogin(reqDto.getLogin())).thenReturn(Optional.of(userModel));
        boolean result = userService.authenticateUser(reqDto);
        //given
        assertTrue(result);
    }

    @Test
    void authenticateUser_shouldThrowException() {
        //given
        //when
        when(userRepository.findByLogin(reqDto.getLogin())).thenReturn(Optional.empty());
        //given
        assertThrows(UserNotFoundException.class, () -> userService.authenticateUser(reqDto));
    }

    private UserModel getUserModel() {
        return UserModel.builder()
                .login(reqDto.getLogin())
                .password(reqDto.getPassword())
                .targets(new ArrayList<>())
                .build();
    }

}