package pl.micede.personalapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import pl.micede.personalapi.dto.TargetReadDto;
import pl.micede.personalapi.dto.TargetReqDto;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.model.TargetCategory;
import pl.micede.personalapi.model.TargetModel;
import pl.micede.personalapi.service.TargetService;
import pl.micede.personalapi.utils.mapper.TargetMapper;

import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TargetController.class)
class TargetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TargetService targetService;


    @Autowired
    private ObjectMapper objectMapper;

    private TargetModel targetModel;
    @BeforeEach
    void setUp() {
        targetModel = new TargetModel();
        targetModel.setId(1L);
        targetModel.setTargetName("Study");
        targetModel.setDescription("Study harder for final project");
        targetModel.setTargetCategory(TargetCategory.KNOWLEDGE);
        targetModel.setTargetBegins(LocalDateTime.of(2024, Month.JUNE, 1, 12, 00));
        targetModel.setTargetEnds(LocalDateTime.of(2024, Month.JULY, 3, 12, 00));
        targetModel.setHabits(new ArrayList<>());
    }


    @Test
    void addNewTarget_shouldReturnCreatedTarget() throws Exception{
        //given
        TargetReqDto reqDto = TargetReqDto.builder()
                .targetName("Study")
                .description("Study harder for final project")
                .targetBegins(LocalDateTime.of(2024, Month.JUNE, 1, 12, 00))
                .targetEnds(LocalDateTime.of(2024, Month.JULY, 3, 12, 00))
                .targetCategory(TargetCategory.KNOWLEDGE)
                .habits(new ArrayList<>())
                .build();

        // Symulacja działania serwisu
        BDDMockito.when(targetService.addNewTarget(any(TargetReqDto.class))).thenReturn(new TargetModel());
        //when //then
        mockMvc.perform(post("/target/add")
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqDto))
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(new TargetModel())));
    }

    @Test
    void getTargetById_ShouldGetTargetById() throws Exception {

        LocalDateTime targetBegins = LocalDateTime.of(2024, 6, 1, 12, 0);
        LocalDateTime targetEnds = LocalDateTime.of(2024, 7, 3, 12, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        TargetReadDto dto = TargetReadDto.builder()
//                .id(1L)
                .targetName("Study")
                .targetBegins(targetBegins.format(formatter))
                .targetEnds(targetEnds.format(formatter))
                .description("Study harder for final project")
                .targetCategory(TargetCategory.KNOWLEDGE)
                .habits(new ArrayList<>())
                .build();
        BDDMockito.when(targetService.getTargetById(any(Long.class))).thenReturn(dto);

        mockMvc.perform(get("/target/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void getTargetsByCategory_shouldGetTargetByCategory() throws Exception {

        LocalDateTime targetBegins = LocalDateTime.of(2024, 6, 1, 12, 0);
        LocalDateTime targetEnds = LocalDateTime.of(2024, 7, 3, 12, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        TargetReadDto dto = TargetReadDto.builder()
//                .id(1L)
                .targetName("Study")
                .targetBegins(targetBegins.format(formatter))
                .targetEnds(targetEnds.format(formatter))
                .description("description")
                .targetCategory(TargetCategory.KNOWLEDGE)
                .habits(new ArrayList<>())
                .build();

        List<TargetReadDto> list = new ArrayList<>();
        list.add(dto);

        BDDMockito.given(targetService.getTargetsByCategory(String.valueOf(any(TargetCategory.class)))).willReturn(list);

        mockMvc.perform(get("/targetsByCategory/{targetCategory}", "KNOWLEDGE")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));

    }

    @Test
    void updateTargetEndingDateByName_shouldUpdateTargetEndingDateByName() throws Exception {
        LocalDateTime targetEnds = LocalDateTime.of(2024, 7, 3, 12, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String formattedNewDate = targetEnds.format(formatter);
        TargetReadDto dto = TargetReadDto.builder()
                .id(1L)
                .targetName("Study")
                .targetBegins("2024-06-22")
                .targetEnds("2024-07-30")
                .description("description")
                .targetCategory(TargetCategory.KNOWLEDGE)
                .habits(new ArrayList<>())
                .build();

        BDDMockito.given(targetService.updateTargetEndingDateByName(any(String.class), any(LocalDateTime.class))).willReturn(dto);

        mockMvc.perform(patch("/target/updateEndingDate/{targetName}?new-date", "Study")
                        .param("newDate", formattedNewDate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));

    }

    @Test
    void deleteTargetById_shouldDeleteTargetById() throws Exception {

        Long id = 1L;

        BDDMockito.doNothing().when(targetService).deleteTargetById(id);

        mockMvc.perform(delete("/target/delete/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
}