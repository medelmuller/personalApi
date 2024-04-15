package pl.micede.personalapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.micede.personalapi.model.TargetModel;

import java.util.List;

@Getter
@Setter
@Builder
public class UserReqDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

}
