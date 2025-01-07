package com.active.web.dto.workout;

import com.active.services.structures.ExerciseStructure;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class WorkoutCreateRequest {
    @NotNull(message = "Workout name must be present")
    private String title;

    private List<ExerciseStructure> templateExerciseStructures;
}
