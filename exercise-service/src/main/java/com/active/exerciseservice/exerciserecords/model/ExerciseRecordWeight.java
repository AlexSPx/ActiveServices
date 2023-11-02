package com.active.exerciseservice.exerciserecords.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRecordWeight extends ExerciseRecord{
    private int[] reps;
    private double[] weight;

    public ExerciseRecordWeight(String exerciseId, boolean isTimeBased, int[] reps, double[] weight) {
        super(exerciseId, isTimeBased);
        this.reps = reps;
        this.weight = weight;
    }
}
