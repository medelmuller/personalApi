package pl.micede.personalapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.micede.personalapi.utils.validator.PasswordStrengthChecker;

@Getter
@Setter
@Builder
public class UserReqDto {

    @NotBlank(message = "Invalid Login: Empty login")
    private String login;

    @NotBlank(message = "Invalid Password: Empty password")
    @PasswordStrengthChecker(message = "Password must contain at least one number")
    private String password;

}
