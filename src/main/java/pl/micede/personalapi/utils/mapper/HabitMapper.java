package pl.micede.personalapi.utils.mapper;

import org.springframework.stereotype.Component;
import pl.micede.personalapi.dto.HabitReadDto;
import pl.micede.personalapi.model.HabitModel;

@Component
public class HabitMapper {

    /**
     * Converts a HabitModel entity to a HabitReadDto
     *
     * @param habitModel the habitModel entity to convert
     * @return The converted HabitReadDto
     */
    public HabitReadDto toDto(HabitModel habitModel) {
        return HabitReadDto.builder()
                .id(habitModel.getId())
                .habitName(habitModel.getHabitName())
                .habitDescription(habitModel.getHabitDescription())
                .frequencyType(habitModel.getFrequencyType())
                .target(habitModel.getTarget())
                .activities(habitModel.getActivities())
                .build();
    }
}
