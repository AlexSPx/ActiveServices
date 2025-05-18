package com.active.web.endpoints.workout;

import com.active.authservice.user.UserService;
import com.active.models.User;
import com.active.models.WorkoutRecordWithTitle;
import com.active.models.workout.Workout;
import com.active.models.workout.WorkoutRecord;
import com.active.models.workout.template.WorkoutTemplate;
import com.active.services.WorkoutService;
import com.active.services.WorkoutTemplateService;
import com.active.web.dto.workout.WorkoutCreateRequest;
import com.active.web.dto.workoutrecord.WorkoutRecordCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;
    private final UserService userService;
    private final WorkoutTemplateService workoutTemplateService;

    @GetMapping
    public ResponseEntity<List<Workout>> getUserWorkouts(@RequestAttribute("X-Uid") String userId,
                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "100") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(workoutService.getWorkoutsByUser(userId, pageable).getContent());
    }

    @PostMapping
    public ResponseEntity<Workout> createWorkout(@RequestAttribute("X-Uid") String userId,
                                                 @RequestBody WorkoutCreateRequest workoutCreateRequest) {

        WorkoutTemplate workoutTemplate = workoutTemplateService.createWorkoutTemplate(workoutCreateRequest.getTemplateExerciseStructures());
        User user = userService.getUserByid(userId);
        Workout workout = workoutService.createWorkout(workoutCreateRequest.getTitle(), workoutTemplate, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(workout);
    }

    @PostMapping("/record")
    public ResponseEntity<WorkoutRecord> createWorkoutRecord(@RequestBody WorkoutRecordCreateRequest workoutRecordCreateRequest) {
        WorkoutRecord workoutRecord = workoutService.createWorkoutRecord(
                workoutRecordCreateRequest.getExerciseRecords(),
                workoutRecordCreateRequest.getWorkoutId(),
                workoutRecordCreateRequest.getTimeToComplete()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(workoutRecord);
    }

    @GetMapping("/record")
    public ResponseEntity<List<WorkoutRecordWithTitle>> createWorkoutRecord(@RequestAttribute("X-Uid") String userId) {
        return ResponseEntity.ok(workoutService.getWorkoutRecordsByUser(userId));
    }
}
