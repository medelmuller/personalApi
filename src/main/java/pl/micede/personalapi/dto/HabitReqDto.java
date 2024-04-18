package pl.micede.personalapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.micede.personalapi.model.ActivityModel;
import pl.micede.personalapi.model.FrequencyType;
import pl.micede.personalapi.model.TargetModel;

import java.util.List;

@Getter
@Setter
@Builder
public class HabitReqDto {

    @NotBlank(message = "Invalid Name: Empty name")
    private String habitName;

    @NotBlank(message = "Invalid Description: Empty description")
    private String habitDescription;

    @NotNull(message = "Invalid Type: Empty type")
    private FrequencyType frequencyType;

    @NotNull(message = "Invalid Target: Type Target number")
    private TargetModel target;

    private List<ActivityModel> activities;

}
