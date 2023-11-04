package com.active.workoutservice.workout.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WorkoutStructure {
    private String[] exerciseRecordIds;
}
