package pl.micede.personalapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.model.TargetCategory;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TargetReadDto {

    private Long id;

    private String targetName;

    private String description;

    private String targetBegins;

    private String targetEnds;

    private TargetCategory targetCategory;

    private List<HabitModel> habits;

}
