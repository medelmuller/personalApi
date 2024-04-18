package pl.micede.personalapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.micede.personalapi.model.HabitModel;

import java.util.List;

@Getter
@Setter
@Builder
public class ActivityReqDto {

    @NotBlank(message = "Invalid Name: Empty name")
    private String activityName;

    @NotBlank(message = "Invalid Description: Empty description")
    private String activityDescription;

    private List<HabitModel> habits;
}

