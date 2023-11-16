package com.active.workoutservice.workoutrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class WorkoutRecordCreateRequest {
    private String workoutId;
    private String workoutTitle;
    private int duration; // In seconds
    private String[] exerciseRecordIds;
}
