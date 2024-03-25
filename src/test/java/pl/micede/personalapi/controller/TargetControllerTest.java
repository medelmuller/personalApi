package pl.micede.personalapi.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TargetController.class)
class TargetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TargetService targetService;

    @Autowired
    private TargetMapper targetMapper;

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
        targetModel.setHabits(null);
    }


    @Test
    void addNewTarget_shouldReturnCreatedTarget() throws Exception{
        //given
        TargetReqDto reqDto = targetMapper.toReqDto(targetModel);
        BDDMockito.given(targetService.addNewTarget(reqDto)).willReturn(targetModel);
//        Mockito.when(targetService.addNewTarget(reqDto)).thenReturn(targetModel);
        //when //then
        mockMvc.perform(post("/target/add",reqDto)
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqDto))
                ).andExpect(status().isCreated());
//                .andExpect(content().json(objectMapper.writeValueAsString(targetModel)));
    }

    @Test
    void getTargetById_ShouldGetTargetById() throws Exception {
        TargetReadDto dto = targetMapper.toDto(targetModel);
        BDDMockito.given(targetService.getTargetById(targetModel.getId())).willReturn(dto);
//        Mockito.when(targetService.getTargetById(targetModel.getId())).thenReturn(dto);

        mockMvc.perform(get("/target/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));

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