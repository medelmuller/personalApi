package pl.micede.personalapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.micede.personalapi.dto.TargetReadDto;
import pl.micede.personalapi.dto.TargetReqDto;
import pl.micede.personalapi.model.TargetCategory;
import pl.micede.personalapi.model.TargetModel;
import pl.micede.personalapi.repository.TargetRepository;
import pl.micede.personalapi.utils.exception.TargetNotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TargetServiceTest {

    @Mock
    private TargetRepository targetRepository;

//    @Mock
//    private TargetMapper targetMapper;

    @Mock
    private TargetReqDto reqDto;

    @InjectMocks
    private TargetService targetService;


    @BeforeEach
    void setUp() {
        reqDto = TargetReqDto.builder()
                .targetName("Study")
                .description("Study harder for final project")
                .targetCategory(TargetCategory.KNOWLEDGE)
                .targetBegins(LocalDateTime.of(2024, Month.JUNE, 1, 12, 0))
                .targetEnds(LocalDateTime.of(2024, Month.JULY, 3, 12, 0))
                .habits(new ArrayList<>())
                .build();
    }

    @Test
    void addNewTarget_ShouldSaveNewTarget() {
        //given
        targetService.addNewTarget(reqDto);
        //when //then
        verify(targetRepository).save(any(TargetModel.class));
    }

    @Test
    void getTargetById_ShouldFindTargetById() {
        //given
        Long id = 1L;
        TargetModel targetModel = getTestModel();
//        TargetReadDto readDto = new TargetReadDto(targetModel.getId(), targetModel.getTargetName(), targetModel.getDescription(), "", "", TargetCategory.KNOWLEDGE, targetModel.getHabits());
        //when
        when(targetRepository.findById(id)).thenReturn(Optional.of(targetModel));
//        when(targetMapper.toDto(any(TargetModel.class))).thenReturn(readDto);
        TargetReadDto result = targetService.getTargetById(id);

        //then
        assertEquals(targetModel.getId(), result.getId());
    }

    @Test
    void getTargetById_ShouldThrowAnException() {
        //given
        Long id = 1L;

        //when
        when(targetRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThrows(TargetNotFoundException.class, () -> targetService.getTargetById(id));

    }


    @Test
    void getTargetsByCategory_ShouldGetListOfTargetsByCategory() {
        //given
        TargetCategory category = TargetCategory.KNOWLEDGE;
        TargetModel targetModel = getTestModel();
//        TargetReadDto readDto = new TargetReadDto(targetModel.getId(), targetModel.getTargetName(), targetModel.getDescription(), "", "", TargetCategory.KNOWLEDGE, targetModel.getHabits());

        //when
        when(targetRepository.findAllByTargetCategory(category)).thenReturn(List.of(targetModel));
//        when(targetMapper.toDto(any(TargetModel.class))).thenReturn(readDto);
        List<TargetReadDto> result = targetService.getTargetsByCategory(String.valueOf(category));

        //then
        assertEquals(targetModel.getId() , result.get(0).getId());
    }

    @Test
    void getTargetsByCategory_ShouldThrowAnException() {
        //given
        TargetCategory category = TargetCategory.KNOWLEDGE;

        //when
        when(targetRepository.findAllByTargetCategory(category)).thenReturn(emptyList());

        //then
        assertThrows(TargetNotFoundException.class, () -> targetService.getTargetsByCategory(String.valueOf(category)));

    }
    @Test
    void updateTargetEndingDateByName() {
        //given
        TargetModel testModel = getTestModel();
        String targetName = "Study";
        LocalDateTime newDate = LocalDateTime.now().plusDays(2);

        //when
        when(targetRepository.findByTargetName(targetName)).thenReturn(Optional.of(testModel));
        TargetReadDto result = targetService.updateTargetEndingDateByName(targetName, newDate);

        //then
//        assertEquals(newDate, result.getTargetEnds());
        assertTrue(newDate.toString().contains(result.getTargetEnds()));


    }

    @Test
    void updateTargetEndingDateByName_ShouldThrowAnException() {
        //given
        String targetName = "Study";
        LocalDateTime newDate = LocalDateTime.now().plusDays(2);

        //when
        when(targetRepository.findByTargetName(targetName)).thenReturn(Optional.empty());

        //then
        assertThrows(TargetNotFoundException.class, () -> targetService.updateTargetEndingDateByName(targetName, newDate));
    }

    @Test
    void deleteTargetById() {
        //given
        Long id = 1L;

        //when
        when(targetRepository.existsById(id)).thenReturn(true);

        targetService.deleteTargetById(id);

        //then
        verify(targetRepository).deleteById(id);
    }

    @Test
    void deleteTargetById_ShouldThrowAnException() {
        //given
        Long id = 1L;

        //when
        when(targetRepository.existsById(id)).thenReturn(false);

        //then
        assertThrows(TargetNotFoundException.class, () -> targetService.deleteTargetById(id));
    }

    private TargetModel getTestModel() {
        TargetModel targetModel = new TargetModel();
        targetModel.setTargetName(reqDto.getTargetName());
        targetModel.setDescription(reqDto.getDescription());
        targetModel.setTargetBegins(reqDto.getTargetBegins());
        targetModel.setTargetEnds(reqDto.getTargetEnds());
        targetModel.setTargetCategory(reqDto.getTargetCategory());
        targetModel.setHabits(reqDto.getHabits());
        return targetModel;
    }
}