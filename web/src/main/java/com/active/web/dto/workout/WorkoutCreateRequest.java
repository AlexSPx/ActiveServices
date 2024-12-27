package com.active.web.dto.workout;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WorkoutCreateRequest {
    @NotNull(message = "Workout must contain exercises")
    private String[] workoutStructureRecords;
    @NotNull(message = "Workout title must be present")
    private String title;
}
