package pl.micede.personalapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.micede.personalapi.config.configuration.AdminConfiguration;
import pl.micede.personalapi.dto.UserReqDto;
import pl.micede.personalapi.model.UserModel;
import pl.micede.personalapi.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * Creates a new User based on the provided UserReqDto object.
     *
     * @param userReqDto The user data transfer object containing the user details.
     * @return ResponseEntity containing the saved UserModel object and http status Created.
     */
    @Operation(summary = "Create User", description = "Creates a new User based on the provided data")
    @PostMapping("/register")
    public ResponseEntity<UserModel> addNewUser(@Valid @RequestBody UserReqDto userReqDto) {
        UserModel userModel = userService.addNewUser(userReqDto);
        return new ResponseEntity<>(userModel, HttpStatus.CREATED);
    }

    /**
     * Retrieves a specific User by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity containing the UserModel if object is found.
     */
    @Operation(summary = "Get User", description = "Retrieves a user by its ID")
    @GetMapping("/get/{id}")
    public ResponseEntity<UserModel> getUserById(@Valid @PathVariable Long id) {
        UserModel userById = userService.getUserById(id);
        return ResponseEntity.ok(userById);
    }


    /**
     * Updates a specific User by its login and changes its password.
     *
     * @param login The login of the user to retrieve.
     * @param password New password for user update provided in request param.
     * @return ResponseEntity containing the updated UserModel if object is found.
     */
    @Operation(summary = "Update User", description = "Updates an existing user with new data")
    @PatchMapping("/update/{login}")
    public ResponseEntity<UserModel> updateUserPasswordByLogin(@Valid @PathVariable String login, @Valid @RequestParam String password) {
        UserModel userModel = userService.updateUserPasswordByLogin(login, password);
        return ResponseEntity.ok(userModel);
    }


    /**
     * Deletes a specific User by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity with NO_CONTENT status if the deletion was successful.
     */
    @Operation(summary = "Delete User", description = "Deletes user by its ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@Valid @PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
