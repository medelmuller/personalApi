package pl.micede.personalapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.micede.personalapi.dto.UserReqDto;
import pl.micede.personalapi.model.UserModel;
import pl.micede.personalapi.service.UserService;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@WithMockUser(username = "Admin", roles = "ADMIN")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void addNewUser_shouldAddNewUser() throws Exception {
        //given
        UserReqDto dto = UserReqDto.builder()
                .login("String")
                .password("string1")
                .build();
        //when
        BDDMockito.when(userService.addNewUser(any(UserReqDto.class))).thenReturn(new UserModel());
        //then
        mockMvc.perform(post("/user/register").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(new UserModel())));
    }

    @Test
    void getUserById_shouldGetUserById() throws Exception {
        //given
        UserModel userModel = getUserModel();
        //when
        BDDMockito.when(userService.getUserById(any(Long.class))).thenReturn(userModel);
        //then
        mockMvc.perform(get("/user/get/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userModel)));
    }

    @Test
    void updateUserPasswordByLogin_shouldUpdatePasswordByLogin() throws Exception {
        //given
        String newPassword = "New password1";
        UserModel userModel = getUserModel();
        //when
        BDDMockito.when(userService.updateUserPasswordByLogin(any(String.class), any(String.class))).thenReturn(userModel);
        //then
        mockMvc.perform(patch("/user/update/{login}?password", "String").with(csrf())
                        .param("password", newPassword)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userModel)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userModel)));
    }

    @Test
    void deleteUserById_shouldDeleteUserById() throws Exception {
        //given
        Long id = 1L;
        //when
        BDDMockito.doNothing().when(userService).deleteUserById(id);
        //then
        mockMvc.perform(delete("/user/delete/{id}", id).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private static UserModel getUserModel() {
        return UserModel.builder()
                .id(1L)
                .login("String")
                .password("String1")
                .targets(new ArrayList<>())
                .build();
    }
}