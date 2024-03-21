package pl.micede.personalapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ActivityReadDto {

    private String activityName;

    private String activityDescription;

}
