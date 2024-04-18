package pl.micede.personalapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.micede.personalapi.dto.UserReqDto;
import pl.micede.personalapi.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

        private final AuthenticationManager authenticationManager;
        private final UserService userService;

        /**
        * Checks if user details contained in request Data Transfer Object are authenticated to log in.
        *
        * @param dto The user data transfer object containing the user details.
        * @return ResponseEntity with http status Accepted.
        */
        @PostMapping("/login")
        public ResponseEntity<Void> loginWithUserData(@Valid @RequestBody UserReqDto dto) {
            if (userService.authenticateUser(dto)) {
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } else {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword()));
            }
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

}
