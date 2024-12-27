package com.active.web.dto.exerciserecords;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExerciseRecordRequest {
    private String exerciseId;
    private String exerciseName;
    private boolean isTimeBased;
    private int[] reps;
    private double[] weight;
    private double[] time;
}
