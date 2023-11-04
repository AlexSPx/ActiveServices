package com.active.workoutservice.workout;

import com.active.workoutservice.workout.dto.WorkoutCreateRequest;
import com.active.workoutservice.workout.exceptions.WorkoutNotFoundException;
import com.active.workoutservice.workout.models.Workout;
import com.active.workoutservice.workout.models.WorkoutStructure;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    public String create(WorkoutCreateRequest workoutCreateRequest, String uid) {
        WorkoutStructure workoutStructure = WorkoutStructure.builder()
                .exerciseRecordIds(workoutCreateRequest.getWorkoutStructureRecords())
                .build();

        Workout workout = Workout.builder()
                .createdBy(uid)
                .workoutStructure(workoutStructure)
                .title(workoutCreateRequest.getTitle())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return workoutRepository.insert(workout).getId();
    }

    public Workout getById(String id) throws WorkoutNotFoundException {
        return workoutRepository.findById(id)
                .orElseThrow(WorkoutNotFoundException::new);
    }
}
