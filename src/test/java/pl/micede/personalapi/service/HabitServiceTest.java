package pl.micede.personalapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.micede.personalapi.dto.HabitReadDto;
import pl.micede.personalapi.dto.HabitReqDto;
import pl.micede.personalapi.model.FrequencyType;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.model.TargetModel;
import pl.micede.personalapi.repository.HabitRepository;
import pl.micede.personalapi.repository.TargetRepository;
import pl.micede.personalapi.utils.exception.HabitNotFoundException;
import pl.micede.personalapi.utils.exception.TargetNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitServiceTest {

    @Mock
    private HabitRepository habitRepository;

    @Mock
    private TargetRepository targetRepository;

    @InjectMocks
    private HabitService habitService;

    @Mock
    private HabitReqDto reqDto;

    @BeforeEach
    void setUp() {
        reqDto = HabitReqDto.builder()
                .habitName("Reading Java book")
                .habitDescription("Reading Java each day for 30 min")
                .frequencyType(FrequencyType.DAILY)
                .target(new TargetModel())
                .activities(new ArrayList<>())
                .build();
    }

    @Test
    void addNewHabit_ShouldAddNewHabit() {
        //given
        //when
        when(targetRepository.findById(reqDto.getTarget().getId())).thenReturn(Optional.of(new TargetModel()));
        habitService.addNewHabit(reqDto);

         //then
        verify(habitRepository).save(any(HabitModel.class));
    }

    @Test
    void addNewHabit_ShouldThrowTargetException() {
        //when
        when(targetRepository.findById(reqDto.getTarget().getId())).thenReturn(Optional.empty());

        //then
        assertThrows(TargetNotFoundException.class, () -> habitService.addNewHabit(reqDto));
    }

    @Test
    void findById() {
        //given
        Long id = 1L;
        HabitModel habitModel = getHabitModel();
        //when
        when(habitRepository.findById(id)).thenReturn(Optional.of(habitModel));
        HabitReadDto result = habitService.findById(id);

        //then
        assertEquals(habitModel.getId(), result.getId());
    }




    @Test
    void findById_ShouldThrowException() {
        //given
        Long id = 1L;
        //when
        when(habitRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThrows(HabitNotFoundException.class, () -> habitService.findById(id));
    }

    @Test
    void findByName() {
        //given
        String habitName = "Reading Java book";
        HabitModel habitModel = getHabitModel();

        //when
        when(habitRepository.findByHabitName(habitName)).thenReturn(Optional.of(habitModel));
        HabitReadDto result = habitService.findByName(habitName);

        //then
        assertEquals(habitModel.getHabitName(), result.getHabitName());
    }

    @Test
    void findByName_ShouldThrowException() {
        //given
        String habitName = "Reading Java book";

        //when
        when(habitRepository.findByHabitName(habitName)).thenReturn(Optional.empty());

        //then
        assertThrows(HabitNotFoundException.class, () -> habitService.findByName(habitName));
    }

    @Test
    void updateDescriptionById() {
        //given
        Long id = 1L;
        String newDescription = "Reading Java book for 1 hour";
        HabitModel habitModel = getHabitModel();
        //when
        when(habitRepository.findById(id)).thenReturn(Optional.of(habitModel));
        HabitReadDto result = habitService.updateDescriptionById(id, newDescription);
        //then
        assertEquals(newDescription, result.getHabitDescription());
    }

    @Test
    void updateDescriptionById_ShouldThrowException() {
        //given
        Long id = 1L;
        String newDescription = "Reading Java book for 1 hour";
        //when
        when(habitRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThrows(HabitNotFoundException.class, () -> habitService.updateDescriptionById(id, newDescription));
    }

    @Test
    void deleteById() {
        //given
        Long id = 1L;
        //when
        when(habitRepository.existsById(id)).thenReturn(true);
        habitService.deleteById(id);
        //then
        verify(habitRepository).deleteById(id);
    }

    @Test
    void deleteById_ShouldThrowException() {
        //given
        Long id = 1L;

        //when
        when(habitRepository.existsById(id)).thenReturn(false);

        //then
        assertThrows(HabitNotFoundException.class, () -> habitService.deleteById(id));
    }

    private HabitModel getHabitModel() {
        HabitModel habitModel = new HabitModel();
        habitModel.setHabitName(reqDto.getHabitName());
        habitModel.setHabitDescription(reqDto.getHabitDescription());
        habitModel.setFrequencyType(reqDto.getFrequencyType());
        habitModel.setTarget(reqDto.getTarget());
        habitModel.setActivities(reqDto.getActivities());
        return habitModel;
    }
}