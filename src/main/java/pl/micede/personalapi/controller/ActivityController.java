package pl.micede.personalapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.micede.personalapi.dto.ActivityReadDto;
import pl.micede.personalapi.dto.ActivityReqDto;
import pl.micede.personalapi.model.ActivityModel;
import pl.micede.personalapi.service.ActivityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;


    /**
     * Creates a new Activity based on the provided ActivityReqDto object and habitId for connecting activity to its Habit.
     *
     * @param activityDto The activity data transfer object containing the activity details.
     * @param habitId The ID of the habit to connect one to another.
     * @return ResponseEntity containing the saved ActivityModel object and http status Created.
     */
    @Operation(summary = "Create Activity", description = "Creates a new Activity based on the provided data")
    @PostMapping("/add/{habitId}")
    public ResponseEntity<ActivityModel> addNewActivity(@Valid @RequestBody ActivityReqDto activityDto, @PathVariable Long habitId) {
        ActivityModel activityModel = activityService.addNewActivity(activityDto, habitId);
        return new ResponseEntity<>(activityModel, HttpStatus.CREATED);
    }

    /**
     * Retrieves a specific Activity by its ID with information converted into ReadDto.
     *
     * @param id The ID of the activity to retrieve.
     * @return ResponseEntity containing the ActivityReadDto if object is found.
     */
    @Operation(summary = "Get Activity by ID", description = "Retrieves activity by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ActivityReadDto> getActivityById(@Valid @PathVariable Long id) {
        ActivityReadDto foundById = activityService.findById(id);
        return ResponseEntity.ok(foundById);
    }


    /**
     * Updates a specific activity by its ID and changes its description.
     *
     * @param id The ID of the activity to retrieve.
     * @param newDescription New description for activity update provided in request param.
     * @return ResponseEntity containing the updated ActivityReadDto if object is found.
     */
    @Operation(summary = "Update Activity", description = "Updates an existing activity with new data")
    @PatchMapping("/updateDescription/{id}")
    public ResponseEntity<ActivityReadDto> updateDescriptionById(@Valid @PathVariable Long id, @Valid @RequestParam String newDescription) {
        ActivityReadDto activityReadDto = activityService.updateDescriptionById(id, newDescription);
        return ResponseEntity.ok(activityReadDto);
    }


    /**
     * Deletes a specific Activity by its ID.
     *
     * @param id The ID of the activity to retrieve.
     * @return ResponseEntity with NO_CONTENT status if the deletion was successful.
     */
    @Operation(summary = "Delete Activity", description = "Deletes activity by its ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@Valid @PathVariable Long id) {
        activityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
