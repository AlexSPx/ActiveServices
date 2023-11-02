package com.active.exerciseservice.exerciserecords.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExerciseRecordRequest {
    private String exerciseId;
    private boolean isTimeBased;
    private int[] reps;
    private double[] weight;
    private double[] time;
}
