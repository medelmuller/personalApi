package pl.micede.personalapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.micede.personalapi.dto.UserReqDto;
import pl.micede.personalapi.model.UserModel;
import pl.micede.personalapi.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserModel> addNewUser(@Valid @RequestBody UserReqDto userReqDto) {
        UserModel userModel = userService.addNewUser(userReqDto);
        return new ResponseEntity<>(userModel, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserModel> getUserById(@Valid @PathVariable Long id) {
        UserModel userById = userService.getUserById(id);
        return ResponseEntity.ok(userById);
    }

}
