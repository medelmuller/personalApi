package pl.micede.personalapi.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import pl.micede.personalapi.dto.ActivityReadDto;
import pl.micede.personalapi.dto.ActivityReqDto;
import pl.micede.personalapi.model.ActivityModel;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.repository.ActivityRepository;
import pl.micede.personalapi.repository.HabitRepository;
import pl.micede.personalapi.utils.exception.ActivityNotFoundException;
import pl.micede.personalapi.utils.mapper.ActivityMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final HabitRepository habitRepository;

    public ActivityModel addNewActivity(ActivityReqDto activityDto, Long habitId) {
        ActivityModel activityModel = new ActivityModel();
        activityModel.setActivityName(activityDto.getActivityName());
        activityModel.setActivityDescription(activityDto.getActivityDescription());
        List<HabitModel> foundById = habitRepository.findAllById(activityDto.getHabits().stream().filter(h -> h.getId().equals(habitId)).toList());
        activityModel.setHabits(foundById);
        return activityRepository.save(activityModel);
    }

    public ActivityReadDto findById(Long id) {
        return activityRepository.findById(id)
                .map(activityMapper::toDto)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found"));
    }

    public ActivityReadDto updateDescriptionById(Long id, String newDescription) {
        Optional<ActivityModel> foundById = activityRepository.findById(id);
        foundById.ifPresent(a -> a.setActivityDescription(newDescription));
        return foundById.map(activityMapper::toDto)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found"));
    }

    public void deleteById(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new ActivityNotFoundException("Activity not found");
        }
        activityRepository.deleteById(id);
    }
}
