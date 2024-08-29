package com.active.workoutservice.workout;

import com.active.workoutservice.workout.dto.WorkoutCreateRequest;
import com.active.workoutservice.workout.exceptions.WorkoutNotFoundException;
import com.active.workoutservice.workout.models.Workout;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createWorkout(@RequestBody @Valid WorkoutCreateRequest workoutCreateRequest,
                                @RequestHeader("uid") String uid) {
        return workoutService.create(workoutCreateRequest, uid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Workout> getUserWorkouts(@RequestHeader("uid") String uid,
                                         @RequestParam(defaultValue = "0") int page) {
        return workoutService.getByUser(uid, page, 10);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Workout getWorkoutById(@PathVariable("id") String id) throws WorkoutNotFoundException {
        return workoutService.getById(id);
    }
}
