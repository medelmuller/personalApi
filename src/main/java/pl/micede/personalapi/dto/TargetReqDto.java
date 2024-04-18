package pl.micede.personalapi.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.model.TargetCategory;
import pl.micede.personalapi.model.UserModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class TargetReqDto {

    @NotBlank(message = "Invalid Name: Empty name")
    private String targetName;

    @NotBlank(message = "Invalid Description: Empty description")
    private String description;

    @FutureOrPresent(message = "Invalid Date: Future or present date")
    private LocalDateTime targetBegins;

    @FutureOrPresent(message = "Invalid Date: Future or present date")
    private LocalDateTime targetEnds;

    @NotNull(message = "Invalid Name: Empty name")
    private TargetCategory targetCategory;


    private List<HabitModel> habits;

    private UserModel user;


}
