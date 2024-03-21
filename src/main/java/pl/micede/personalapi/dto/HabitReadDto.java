package pl.micede.personalapi.dto;

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
public class HabitReadDto {

    private Long id;

    private String habitName;

    private String habitDescription;

    private FrequencyType frequencyType;

    private TargetModel target;

    private List<ActivityModel> activities;

}
