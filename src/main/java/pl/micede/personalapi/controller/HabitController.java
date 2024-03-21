package pl.micede.personalapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.micede.personalapi.dto.HabitReadDto;
import pl.micede.personalapi.dto.HabitReqDto;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.service.HabitService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/habit")
public class HabitController {

    private final HabitService habitService;


    @PostMapping("/addHabit")
    public ResponseEntity<HabitModel> addNewHabit(@Valid @RequestBody HabitReqDto habitDto) {
        HabitModel habitModel = habitService.addNewHabit(habitDto);
        return new ResponseEntity<>(habitModel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitReadDto> getHabitById(@Valid @PathVariable Long id) {
        HabitReadDto habitById = habitService.findById(id);
        return ResponseEntity.ok(habitById);
    }

    @GetMapping("/findBy/{habitName}")
    public ResponseEntity<HabitReadDto> getHabitByName(@Valid @PathVariable String habitName) {
        HabitReadDto habitByName = habitService.findByName(habitName);
        return ResponseEntity.ok(habitByName);
    }

    @PatchMapping("/updateDescriptionBy/{id}")
    public ResponseEntity<HabitReadDto> updateDescriptionById(@Valid @PathVariable Long id, @Valid @RequestParam String newDescription) {
        HabitReadDto updateDescriptionById = habitService.updateDescriptionById(id, newDescription);
        return new ResponseEntity<>(updateDescriptionById, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@Valid @PathVariable Long id) {
        habitService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
