package pl.micede.personalapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.micede.personalapi.dto.TargetReadDto;
import pl.micede.personalapi.dto.TargetReqDto;
import pl.micede.personalapi.model.TargetCategory;
import pl.micede.personalapi.model.TargetModel;
import pl.micede.personalapi.repository.TargetRepository;
import pl.micede.personalapi.utils.mapper.TargetMapper;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TargetServiceTest {

    @Mock
    private TargetRepository targetRepository;

//    @InjectMocks
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
        TargetModel targetModel = new TargetModel();
        targetModel.setTargetName(reqDto.getTargetName());
        targetModel.setDescription(reqDto.getDescription());
        targetModel.setTargetBegins(reqDto.getTargetBegins());
        targetModel.setTargetEnds(reqDto.getTargetEnds());
        targetModel.setTargetCategory(reqDto.getTargetCategory());
        targetModel.setHabits(reqDto.getHabits());


//        BDDMockito.when(targetRepository.save(targetModel)).thenReturn(new TargetModel());

        targetService.addNewTarget(reqDto);
        verify(targetRepository).save(any(TargetModel.class));
    }

    @Test
    void getTargetById_ShouldFindTargetById() {
        Long id = 1L;
        TargetModel targetModel = new TargetModel();
//        targetModel.setId(id);
        targetModel.setTargetName(reqDto.getTargetName());
        targetModel.setDescription(reqDto.getDescription());
        targetModel.setTargetBegins(reqDto.getTargetBegins());
        targetModel.setTargetEnds(reqDto.getTargetEnds());
        targetModel.setTargetCategory(reqDto.getTargetCategory());
        targetModel.setHabits(reqDto.getHabits());

        TargetReadDto readDto = new TargetReadDto(targetModel.getId(), targetModel.getTargetName(), targetModel.getDescription(), "", "", TargetCategory.KNOWLEDGE, targetModel.getHabits());

//        when(targetRepository.save(any(TargetModel.class))).thenReturn(targetModel);
        when(targetRepository.findById(id)).thenReturn(Optional.of(targetModel));
//        when(targetMapper.toDto(any(TargetModel.class))).thenReturn(readDto);



        TargetReadDto result = targetService.getTargetById(id);
        assertEquals(targetModel.getId(), result.getId());
    }

    @Test
    void getTargetById_ShouldThrowAnException() {

    }

    @Test
    void getTargetsByCategory() {
        TargetCategory category = TargetCategory.KNOWLEDGE;
        TargetModel targetModel = new TargetModel();
        targetModel.setTargetName(reqDto.getTargetName());
        targetModel.setDescription(reqDto.getDescription());
        targetModel.setTargetBegins(reqDto.getTargetBegins());
        targetModel.setTargetEnds(reqDto.getTargetEnds());
        targetModel.setTargetCategory(reqDto.getTargetCategory());
        targetModel.setHabits(reqDto.getHabits());


//        List<TargetReadDto> collect = Stream.of(targetModel).map(targetMapper::toDto).collect(Collectors.toList());

        when(targetRepository.findAllByTargetCategory(category)).thenReturn(List.of(targetModel));
        when(targetRepository.save(any(TargetModel.class))).thenReturn(targetModel);

        List<TargetReadDto> result = targetService.getTargetsByCategory(String.valueOf(category));



//        assertEquals(collect , result);
    }

    @Test
    void updateTargetEndingDateByName() {
    }

    @Test
    void deleteTargetById() {
    }
}