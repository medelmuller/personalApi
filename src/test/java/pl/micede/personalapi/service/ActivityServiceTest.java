package pl.micede.personalapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.micede.personalapi.dto.ActivityReadDto;
import pl.micede.personalapi.dto.ActivityReqDto;
import pl.micede.personalapi.model.ActivityModel;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.repository.ActivityRepository;
import pl.micede.personalapi.repository.HabitRepository;
import pl.micede.personalapi.utils.exception.ActivityNotFoundException;
import pl.micede.personalapi.utils.exception.HabitNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {
    @Mock
    private HabitRepository habitRepository;

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    @Mock
    private ActivityReqDto reqDto;

    @BeforeEach
    void setUp() {
        reqDto = ActivityReqDto.builder()
                .activityName("Reading")
                .activityDescription("Reading Book")
                .habits(new ArrayList<>())
                .build();
    }
    @Test
    void addNewActivity_ShouldAddNewActivityToSelectedHabit() {
        //given
        Long habitId = 1L;
        //when
        when(habitRepository.findById(habitId)).thenReturn(Optional.of(new HabitModel()));
        activityService.addNewActivity(reqDto, habitId);
        //then
        verify(activityRepository).save(any(ActivityModel.class));
    }

    @Test
    void addNewActivity_ShouldThrowHabitException() {
        //given
        Long habitId = 1L;

        //when
        when(habitRepository.findById(habitId)).thenReturn(Optional.empty());

        //then
        assertThrows(HabitNotFoundException.class, () -> activityService.addNewActivity(reqDto, habitId));
    }

    @Test
    void findById_ShouldFindActivityById() {
        //given
        Long id = 1L;
        ActivityModel activityModel = getActivityModel();
        //when
        when(activityRepository.findById(id)).thenReturn(Optional.of(activityModel));
        ActivityReadDto result = activityService.findById(id);
        //then
        assertEquals(activityModel.getId(), result.getId());
    }


    @Test
    void findById_ShouldThrowException() {
        //given
        Long id = 1L;
        //when
        when(activityRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThrows(ActivityNotFoundException.class, () -> activityService.findById(id));
    }

    @Test
    void updateDescriptionById_ShouldUpdateActivityDescriptionById() {
        //given
        Long id = 1L;
        String newDescription = "Reading articles";
        ActivityModel activityModel = getActivityModel();
        //when
        when(activityRepository.findById(id)).thenReturn(Optional.of(activityModel));
        ActivityReadDto result = activityService.updateDescriptionById(id, newDescription);
        //then
        assertEquals(newDescription, result.getActivityDescription());
    }

    @Test
    void updateDescriptionById_ShouldThrowException() {
        //given
        Long id = 1L;
        String newDescription = "Reading articles";
        //when
        when(activityRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThrows(ActivityNotFoundException.class, () -> activityService.updateDescriptionById(id, newDescription));
    }

    @Test
    void deleteById_ShouldDeleteActivityById() {
        //given
        Long id = 1L;
        //when
        when(activityRepository.existsById(id)).thenReturn(true);
        activityService.deleteById(id);
        //then
        verify(activityRepository).deleteById(id);
    }

    @Test
    void deleteById_ShouldThrowException() {
        //given
        Long id = 1L;
        //when
        when(activityRepository.existsById(id)).thenReturn(false);
        //then
        assertThrows(ActivityNotFoundException.class, () -> activityService.deleteById(id));
    }

    private ActivityModel getActivityModel() {
        ActivityModel activityModel = new ActivityModel();
        activityModel.setActivityName(reqDto.getActivityName());
        activityModel.setActivityDescription(reqDto.getActivityDescription());
        activityModel.setHabits(reqDto.getHabits());
        return activityModel;
    }
}