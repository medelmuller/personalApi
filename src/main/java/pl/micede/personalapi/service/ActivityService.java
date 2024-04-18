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
import pl.micede.personalapi.utils.exception.HabitNotFoundException;
import pl.micede.personalapi.utils.mapper.ActivityMapper;

import java.util.Optional;

@Service
@Data
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper = new ActivityMapper();
    private final HabitRepository habitRepository;

    /**
     * Creates a new activity using details provided in a ActivityReqDto object and connects it with chosen habits.
     *
     * @param activityDto Data Transfer Object containing activity details.
     * @param habitId Habits ID
     * @return The saved TargetModel entity.
     */
    public ActivityModel addNewActivity(ActivityReqDto activityDto, Long habitId) {
        ActivityModel activityModel = new ActivityModel();
        activityModel.setActivityName(activityDto.getActivityName());
        activityModel.setActivityDescription(activityDto.getActivityDescription());

        Optional<HabitModel> foundHabit = habitRepository.findById(habitId);
        if (foundHabit.isPresent()) {
            HabitModel habitModel = foundHabit.get();

            activityModel.getHabits().add(habitModel);
            habitModel.getActivities().add(activityModel);
            return activityRepository.save(activityModel);

        } else {
            throw new HabitNotFoundException("Habit with ID " + habitId + "not found");
        }
    }

    /**
     * Retrieves a specific activity by its ID.
     *
     * @param id The ID of the activity to be found.
     * @throws ActivityNotFoundException if activity by its ID could not be found.
     * @return A specific activity mapped into DTO to read.
     */
    public ActivityReadDto findById(Long id) {
        return activityRepository.findById(id)
                .map(activityMapper::toDto)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found"));
    }

    /**
     * Updates an existing activity by its ID with new description in a ActivityModel object.
     *
     * @param id The ID of the activity to be updated.
     * @param newDescription New description of the activity.
     * @throws ActivityNotFoundException if activity could not be found by the ID.
     * @return The updated ActivityModel entity mapped into read dto.
     */
    public ActivityReadDto updateDescriptionById(Long id, String newDescription) {
        Optional<ActivityModel> foundById = activityRepository.findById(id);
        foundById.ifPresent(a -> a.setActivityDescription(newDescription));
        return foundById.map(activityMapper::toDto)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found"));
    }


    /**
     * Deletes an activity from the database.
     *
     * @param id The ID of the activity to be deleted.
     */
    public void deleteById(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new ActivityNotFoundException("Activity not found");
        }
        activityRepository.deleteById(id);
    }
}
