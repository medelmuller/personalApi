package pl.micede.personalapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.micede.personalapi.dto.TargetReqDto;
import pl.micede.personalapi.model.TargetModel;
import pl.micede.personalapi.repository.TargetRepository;
import pl.micede.personalapi.utils.mapper.TargetMapper;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TargetServiceTest {

    @Mock
    private TargetRepository targetRepository;

    @Mock
    private TargetMapper targetMapper;

    @Mock
    private TargetReqDto reqDto;

    @InjectMocks
    private TargetService targetService;

    @Test
    void addNewTarget_ShouldSaveNewTarget() {
        TargetModel targetModel = new TargetModel();
        targetModel.setTargetName("targetName");
        targetModel.setDescription("description");
//        targetModel.setTargetBegins(2024-04-20T22:22:22);
    }

    @Test
    void getTargetById() {
    }

    @Test
    void getTargetsByCategory() {
    }

    @Test
    void updateTargetEndingDateByName() {
    }

    @Test
    void deleteTargetById() {
    }
}