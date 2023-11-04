package com.active.workoutservice.workout.dto;

import com.active.workoutservice.workout.models.WorkoutStructure;
import com.active.workoutservice.workoutrecord.WorkoutRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WorkoutCreateRequest {
    private String[] workoutStructureRecords;
    private String title;
    private String createdBy;
    private String updatedAt;
}
