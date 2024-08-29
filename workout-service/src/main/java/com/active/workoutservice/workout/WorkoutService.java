package com.active.workoutservice.workout;

import com.active.workoutservice.workout.dto.WorkoutCreateRequest;
import com.active.workoutservice.workout.exceptions.WorkoutNotFoundException;
import com.active.workoutservice.workout.models.Workout;
import com.active.workoutservice.workout.models.WorkoutStructure;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    public List<Workout> getByUser(String uid, int offset, int limit) {
        Pageable page = PageRequest.of(offset, limit, Sort.by("createdAt").descending());
        return workoutRepository.findByCreatedBy(uid, page).getContent();
    }

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
