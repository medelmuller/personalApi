package pl.micede.personalapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import pl.micede.personalapi.model.HabitModel;

import java.util.List;

@Getter
@Setter
public class ActivityReqDto {

    @NotBlank
    private String activityName;

    @NotBlank
    private String activityDescription;

//    private Long habitId;

    private List<HabitModel> habits;
}

