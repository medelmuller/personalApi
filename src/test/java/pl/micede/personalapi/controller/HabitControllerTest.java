package pl.micede.personalapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.micede.personalapi.dto.HabitReadDto;
import pl.micede.personalapi.dto.HabitReqDto;
import pl.micede.personalapi.model.FrequencyType;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.model.TargetModel;
import pl.micede.personalapi.service.HabitService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HabitController.class)
class HabitControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HabitService habitService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addNewHabit_ShouldAddNewHabit() throws Exception {
        //given
        HabitReqDto dto = HabitReqDto.builder()
                .habitName("Read")
                .habitDescription("Reading Java books")
                .frequencyType(FrequencyType.DAILY)
                .target(new TargetModel())
                .activities(new ArrayList<>())
                .build();
        //when
        BDDMockito.when(habitService.addNewHabit(any(HabitReqDto.class))).thenReturn(new HabitModel());
        //then
        mockMvc.perform(post("/habit/addHabit")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(new HabitModel())));
    }

    @Test
    void getHabitById_ShouldGetHabitById() throws Exception {
        //given
        HabitReadDto dto = HabitReadDto.builder()
                .habitName("Read")
                .habitDescription("Reading Java books")
                .frequencyType(FrequencyType.DAILY)
                .target(new TargetModel())
                .activities(new ArrayList<>())
                .build();
        //when
        BDDMockito.when(habitService.findById(any(Long.class))).thenReturn(dto);
        //then
        mockMvc.perform(get("/habit/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void getHabitByName_ShouldGetHabitByName() throws Exception {
        //given
        HabitReadDto dto = HabitReadDto.builder()
                .habitName("Read")
                .habitDescription("Reading Java books")
                .frequencyType(FrequencyType.DAILY)
                .target(new TargetModel())
                .activities(new ArrayList<>())
                .build();
        //when
        BDDMockito.when(habitService.findByName(any(String.class))).thenReturn(dto);
        //then
        mockMvc.perform(get("/habit/findBy/{habitName}", "Read")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void updateDescriptionById_ShouldUpdateHabitDescriptionById() throws Exception {
        //given
        String newDescription = "New description";

        HabitReadDto dto = HabitReadDto.builder()
                .habitName("Read")
                .habitDescription("Reading Java books")
                .frequencyType(FrequencyType.DAILY)
                .target(new TargetModel())
                .activities(new ArrayList<>())
                .build();
        //when
        BDDMockito.when(habitService.updateDescriptionById(any(Long.class), any(String.class))).thenReturn(dto);
        //then
        mockMvc.perform(patch("/habit/updateDescriptionBy/{id}?new-description", 1L)
                        .param("newDescription", newDescription)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));

    }

    @Test
    void deleteById_ShouldDeleteHabitById() throws Exception {
        //given
        Long id = 1L;
        //when
        BDDMockito.doNothing().when(habitService).deleteById(id);
        //then
        mockMvc.perform(delete("/habit/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}