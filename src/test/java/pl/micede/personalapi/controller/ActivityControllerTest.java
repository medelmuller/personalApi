package pl.micede.personalapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.micede.personalapi.dto.ActivityReadDto;
import pl.micede.personalapi.dto.ActivityReqDto;

import pl.micede.personalapi.model.ActivityModel;

import pl.micede.personalapi.service.ActivityService;


import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ActivityController.class)
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void addNewActivity_ShouldAddNewActivity() throws Exception {
        //given
        ActivityReqDto dto = ActivityReqDto.builder()
                .activityName("Walk")
                .activityDescription("Walk for 20 minutes a day")
                .habits(new ArrayList<>())
                .build();
        //when
        BDDMockito.when(activityService.addNewActivity(any(ActivityReqDto.class), any(Long.class))).thenReturn(new ActivityModel());
        //then
        mockMvc.perform(post("/activity/add/{habitId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(new ActivityModel())));

    }

    @Test
    void getActivityById_ShouldGetActivityById() throws Exception {
        //given
        ActivityReadDto  dto = ActivityReadDto.builder()
                .activityName("Walk")
                .activityDescription("Walk for 20 minutes a day")
                .build();
        //when
        BDDMockito.when(activityService.findById(any(Long.class))).thenReturn(dto);
        //then
        mockMvc.perform(get("/activity/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));

    }

    @Test
    void updateDescriptionById_ShouldUpdateActivityDescriptionById() throws Exception {
        //given
        String newDescription = "New description";

        ActivityReadDto  dto = ActivityReadDto.builder()
                .activityName("Walk")
                .activityDescription("Walk for 20 minutes a day")
                .build();
        //when
        BDDMockito.when(activityService.updateDescriptionById(any(Long.class), any(String.class))).thenReturn(dto);
        //then
        mockMvc.perform(patch("/activity/updateDescription/{id}?new-description", 1L)
                        .param("newDescription", newDescription)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void deleteById_ShouldDeleteActivityById() throws Exception {
        //given
        Long id = 1L;
        //when
        BDDMockito.doNothing().when(activityService).deleteById(id);
        //then
        mockMvc.perform(delete("/activity/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}