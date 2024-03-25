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


    /**
     * Creates a new Habit based on the provided HabitReqDto object.
     *
     * @param habitDto The habit data transfer object containing the activity details.
     * @return ResponseEntity containing the saved HabitModel object and http status Created.
     */
    @PostMapping("/addHabit")
    public ResponseEntity<HabitModel> addNewHabit(@Valid @RequestBody HabitReqDto habitDto) {
        HabitModel habitModel = habitService.addNewHabit(habitDto);
        return new ResponseEntity<>(habitModel, HttpStatus.CREATED);
    }

    /**
     * Retrieves a specific Habit by its ID with information converted into ReadDto.
     *
     * @param id The ID of the habit to retrieve.
     * @return ResponseEntity containing the HabitReadDto if object is found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HabitReadDto> getHabitById(@Valid @PathVariable Long id) {
        HabitReadDto habitById = habitService.findById(id);
        return ResponseEntity.ok(habitById);
    }


    /**
     * Retrieves a specific Habit by its name with information converted into ReadDto.
     *
     * @param habitName The name of the habit to retrieve.
     * @return ResponseEntity containing the HabitReadDto if object is found.
     */
    @GetMapping("/findBy/{habitName}")
    public ResponseEntity<HabitReadDto> getHabitByName(@Valid @PathVariable String habitName) {
        HabitReadDto habitByName = habitService.findByName(habitName);
        return ResponseEntity.ok(habitByName);
    }


    /**
     * Updates a specific habit by its ID and changes its description.
     *
     * @param id The ID of the habit to retrieve.
     * @param newDescription New description for habit update provided in request param.
     * @return ResponseEntity containing the updated HabitReadDto if object is found.
     */
    @PatchMapping("/updateDescriptionBy/{id}")
    public ResponseEntity<HabitReadDto> updateDescriptionById(@Valid @PathVariable Long id, @Valid @RequestParam String newDescription) {
        HabitReadDto updateDescriptionById = habitService.updateDescriptionById(id, newDescription);
        return new ResponseEntity<>(updateDescriptionById, HttpStatus.ACCEPTED);
    }


    /**
     * Deletes a specific Habit by its ID.
     *
     * @param id The ID of the habit to retrieve.
     * @return ResponseEntity with NO_CONTENT status if the deletion was successful.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@Valid @PathVariable Long id) {
        habitService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
