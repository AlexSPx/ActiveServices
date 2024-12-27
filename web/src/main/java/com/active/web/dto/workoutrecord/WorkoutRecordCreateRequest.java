package com.active.web.dto.workoutrecord;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WorkoutRecordCreateRequest {
    @NotNull(message = "Workout id cannot be null")
    private String workoutId;
    @NotNull(message = "Workout title cannot be null")
    private String workoutTitle;
    private int duration; // In seconds
    @NotNull(message = "Exercise records cannot be null")
    private String[] exerciseRecordIds;
}
