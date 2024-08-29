package com.active.workoutservice.workoutrecord;

import com.active.workoutservice.workoutrecord.dto.WorkoutRecordCreateRequest;
import com.active.workoutservice.workoutrecord.exceptions.WorkoutRecordNotFoundException;
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
@RequestMapping("/api/workout/record")
@RequiredArgsConstructor
public class WorkoutRecordController {

    private final WorkoutRecordService workoutRecordService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createRecord(@RequestBody @Valid WorkoutRecordCreateRequest workoutRecordCreateRequest,
                               @RequestHeader("uid") String uid) {
        return workoutRecordService.create(workoutRecordCreateRequest, uid);
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
