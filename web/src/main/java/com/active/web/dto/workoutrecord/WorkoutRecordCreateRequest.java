package com.active.web.dto.workoutrecord;

import com.active.services.structures.ExerciseStructure;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@Builder
public class WorkoutRecordCreateRequest {
    @NotNull(message = "Workout id cannot be null")
    private String workoutId;
    private long timeToComplete; // In seconds
    @NotNull(message = "Exercise records cannot be null")
    @NotEmpty(message = "Exercise records cannot be empty")
    private List<ExerciseStructure> exerciseRecords;
}
