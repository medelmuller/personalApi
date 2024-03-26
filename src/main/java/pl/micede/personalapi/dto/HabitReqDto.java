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

    @NotBlank
    private String habitName;

    @NotBlank
    private String habitDescription;

    @NotNull
    private FrequencyType frequencyType;

    @NotNull
    private TargetModel target;

    private List<ActivityModel> activities;

}
