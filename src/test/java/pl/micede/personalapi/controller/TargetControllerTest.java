package pl.micede.personalapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.micede.personalapi.dto.TargetReadDto;
import pl.micede.personalapi.dto.TargetReqDto;
import pl.micede.personalapi.model.TargetCategory;
import pl.micede.personalapi.model.TargetModel;
import pl.micede.personalapi.service.TargetService;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TargetController.class)
@WithMockUser(username = "Admin", roles = "ADMIN")
class TargetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TargetService targetService;


    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        TargetModel targetModel = new TargetModel();
        targetModel.setId(1L);
        targetModel.setTargetName("Study");
        targetModel.setDescription("Study harder for final project");
        targetModel.setTargetCategory(TargetCategory.KNOWLEDGE);
        targetModel.setTargetBegins(LocalDateTime.of(2024, Month.JUNE, 1, 12, 0));
        targetModel.setTargetEnds(LocalDateTime.of(2024, Month.JULY, 3, 12, 0));
        targetModel.setHabits(new ArrayList<>());
    }


    @Test
    void addNewTarget_shouldReturnCreatedTarget() throws Exception{
        //given
        TargetReqDto reqDto = TargetReqDto.builder()
                .targetName("Study")
                .description("Study harder for final project")
                .targetBegins(LocalDateTime.of(2024, Month.JUNE, 1, 12, 0))
                .targetEnds(LocalDateTime.of(2024, Month.JULY, 3, 12, 0))
                .targetCategory(TargetCategory.KNOWLEDGE)
                .habits(new ArrayList<>())
                .build();

        // Symulacja działania serwisu
        BDDMockito.when(targetService.addNewTarget(any(TargetReqDto.class))).thenReturn(new TargetModel());
        //when //then
        mockMvc.perform(post("/target/add").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqDto))
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(new TargetModel())));
    }

    @Test
    void getTargetById_ShouldGetTargetById() throws Exception {
        //given
        LocalDateTime targetBegins = LocalDateTime.of(2024, 6, 1, 12, 0);
        LocalDateTime targetEnds = LocalDateTime.of(2024, 7, 3, 12, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        TargetReadDto dto = TargetReadDto.builder()
                .targetName("Study")
                .targetBegins(targetBegins.format(formatter))
                .targetEnds(targetEnds.format(formatter))
                .description("Study harder for final project")
                .targetCategory(TargetCategory.KNOWLEDGE)
                .habits(new ArrayList<>())
                .build();
        //when
        BDDMockito.when(targetService.getTargetById(any(Long.class))).thenReturn(dto);
        //then
        mockMvc.perform(get("/target/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void getTargetsByCategory_shouldGetTargetByCategory() throws Exception {
        //given
        LocalDateTime targetBegins = LocalDateTime.of(2024, 6, 1, 12, 0);
        LocalDateTime targetEnds = LocalDateTime.of(2024, 7, 3, 12, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        TargetReadDto dto = TargetReadDto.builder()
                .targetName("Study")
                .targetBegins(targetBegins.format(formatter))
                .targetEnds(targetEnds.format(formatter))
                .description("description")
                .targetCategory(TargetCategory.KNOWLEDGE)
                .habits(new ArrayList<>())
                .build();

        List<TargetReadDto> list = new ArrayList<>();
        list.add(dto);

        //when
        BDDMockito.when(targetService.getTargetsByCategory("KNOWLEDGE")).thenReturn(list);
        //then
        mockMvc.perform(get("/target/targetsByCategory/{targetCategory}", "KNOWLEDGE")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));

    }

    @Test
    void getTargetsWithDateBetween_shouldGetTargetsFoundByBeginDateBetweenRange() throws Exception{
        //given
        LocalDateTime targetBegins = LocalDateTime.of(2024, 6, 1, 12, 0);
        LocalDateTime targetEnds = LocalDateTime.of(2024, 7, 3, 12, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime minDate = LocalDateTime.of(2024, 5, 20, 12, 0);
        LocalDateTime maxDate = LocalDateTime.of(2024, 6, 10, 12, 0);

        TargetReadDto dto = TargetReadDto.builder()
                .targetName("Study")
                .targetBegins(targetBegins.format(formatter))
                .targetEnds(targetEnds.format(formatter))
                .description("description")
                .targetCategory(TargetCategory.KNOWLEDGE)
                .habits(new ArrayList<>())
                .build();

        List<TargetReadDto> list = new ArrayList<>();
        list.add(dto);

        //when
        BDDMockito.when(targetService.findTargetsBeginsBetweenDates(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(list);
        //then
        mockMvc.perform(get("/target/targetsBeginsBetween?min-date&max-date" )
                        .param("minDate", String.valueOf(minDate))
                        .param("maxDate", String.valueOf(maxDate))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }

    @Test
    void updateTargetEndingDateByName_shouldUpdateTargetEndingDateByName() throws Exception {
        //given
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

        //when
        BDDMockito.given(targetService.updateTargetEndingDateByName(any(String.class), any(LocalDateTime.class))).willReturn(dto);
        //then
        mockMvc.perform(patch("/target/updateEndingDate/{targetName}?new-date", "Study").with(csrf())
                        .param("newDate", formattedNewDate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));

    }

    @Test
    void deleteTargetById_shouldDeleteTargetById() throws Exception {
        //given
        Long id = 1L;
        //when
        BDDMockito.doNothing().when(targetService).deleteTargetById(id);
        //then
        mockMvc.perform(delete("/target/delete/{id}", id).with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
}