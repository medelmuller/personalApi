package pl.micede.personalapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import pl.micede.personalapi.dto.TargetReqDto;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.model.TargetCategory;
import pl.micede.personalapi.model.TargetModel;
import pl.micede.personalapi.service.TargetService;
import pl.micede.personalapi.utils.mapper.TargetMapper;

import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TargetController.class)
class TargetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TargetService targetService;

    @MockBean
    private TargetMapper targetMapper;

    private final String targetName = "Study";
    private final String targetDescription = "Study harder for final project";
    private final TargetCategory targetCategory = TargetCategory.KNOWLEDGE;
    private final LocalDateTime targetBegins = LocalDateTime.of(2024, Month.JUNE, 1, 12, 00);
    private final LocalDateTime targetEnds = LocalDateTime.of(2024, Month.JULY, 3, 12, 00);
    private final List<HabitModel> habits = null;


    @Test
    void addNewTarget_shouldReturnCreatedTarget() throws Exception{
        //given
        TargetReqDto targetReqDto = new TargetReqDto();
        targetReqDto.setTargetName(targetName);
        targetReqDto.setDescription(targetDescription);
        targetReqDto.setTargetCategory(targetCategory);
        targetReqDto.setTargetBegins(targetBegins);
        targetReqDto.setTargetEnds(targetEnds);
        targetReqDto.setHabits(habits);
        //when
        TargetModel model = targetMapper.toModel(targetReqDto);
        Mockito.when(targetService.addNewTarget(targetReqDto)).thenReturn(model);

        //then
        mockMvc.perform(post("/target/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(model))
                ).andExpect(status().isCreated());
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