package com.active.exerciseservice.exerciserecords.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRecordWeight extends ExerciseRecord {
    private int[] reps;
    private double[] weight;

    public ExerciseRecordWeight(String exerciseId, String exerciseName, boolean isTimeBased, int[] reps, double[] weight) {
        super(exerciseId, exerciseName, isTimeBased);
        this.reps = reps;
        this.weight = weight;
    }
}
