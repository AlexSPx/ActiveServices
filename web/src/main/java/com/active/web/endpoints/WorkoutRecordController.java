package com.active.web.endpoints;

import com.active.web.dto.workoutrecord.WorkoutRecordCreateRequest;
import com.active.workoutservice.workoutrecord.WorkoutRecordService;
import com.active.workoutservice.workoutrecord.exceptions.WorkoutRecordNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/workout/record")
@RequiredArgsConstructor
public class WorkoutRecordController {

    private final WorkoutRecordService workoutRecordService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createRecord(@RequestBody @Valid WorkoutRecordCreateRequest workoutRecordCreateRequest,
                               @RequestHeader("uid") String uid) {

        WorkoutRecord workoutRecord = WorkoutRecord.builder()
                .workoutId(workoutRecordCreateRequest.getWorkoutId())
                .workoutTitle(workoutRecordCreateRequest.getWorkoutTitle())
                .uid(uid)
                .duration(workoutRecordCreateRequest.getDuration())
                .finishedAt(LocalDateTime.now())
                .exerciseRecordIds(workoutRecordCreateRequest.getExerciseRecordIds())
                .build();

        return workoutRecordService.create(workoutRecord, uid);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkoutRecord findById(@PathVariable("id") String id) throws WorkoutRecordNotFoundException {
        return workoutRecordService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkoutRecord> findByUser(@RequestHeader("uid") String uid,
                                          @RequestParam(defaultValue = "0") int page) {
        return workoutRecordService.getByUser(uid, page, 10);
    }
}

