package pl.micede.personalapi.utils.mapper;

import org.springframework.stereotype.Component;
import pl.micede.personalapi.dto.ActivityReadDto;
import pl.micede.personalapi.model.ActivityModel;

@Component
public class ActivityMapper {

    /**
     * Converts a ActivityModel entity to a ActivityReadDto
     *
     * @param activityModel the activityModel entity to convert
     * @return The converted ActivityReadDto
     */
    public ActivityReadDto toDto(ActivityModel activityModel) {
        return ActivityReadDto.builder()
                .id(activityModel.getId())
                .activityName(activityModel.getActivityName())
                .activityDescription(activityModel.getActivityDescription())
                .build();
    }

}
