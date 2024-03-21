package pl.micede.personalapi.controller;

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

    @PostMapping("/add/{habitId}")
    public ResponseEntity<ActivityModel> addNewActivity(@Valid @RequestBody ActivityReqDto activityDto, @PathVariable Long habitId) {
        ActivityModel activityModel = activityService.addNewActivity(activityDto, habitId);
        return new ResponseEntity<>(activityModel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityReadDto> getActivityById(@Valid @PathVariable Long id) {
        ActivityReadDto foundById = activityService.findById(id);
        return ResponseEntity.ok(foundById);
    }

    @PatchMapping("/updateDescription/{id}")
    public ResponseEntity<ActivityReadDto> updateDescriptionById(@Valid @PathVariable Long id, @Valid @RequestParam String newDescription) {
        ActivityReadDto activityReadDto = activityService.updateDescriptionById(id, newDescription);
        return ResponseEntity.ok(activityReadDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@Valid @PathVariable Long id) {
        activityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
