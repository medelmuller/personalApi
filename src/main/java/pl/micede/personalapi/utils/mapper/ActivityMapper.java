package pl.micede.personalapi.utils.mapper;

import org.springframework.stereotype.Component;
import pl.micede.personalapi.dto.ActivityReadDto;
import pl.micede.personalapi.model.ActivityModel;

@Component
public class ActivityMapper {

    public ActivityReadDto toDto(ActivityModel activityModel) {
        return ActivityReadDto.builder()
                .activityName(activityModel.getActivityName())
                .activityDescription(activityModel.getActivityDescription())
                .build();
    }

}
