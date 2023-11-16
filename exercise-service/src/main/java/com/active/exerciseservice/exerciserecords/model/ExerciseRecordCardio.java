package com.active.exerciseservice.exerciserecords.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRecordCardio extends ExerciseRecord {
    private double[] time;

    public ExerciseRecordCardio(String exerciseId, String exerciseName, boolean isTimeBased, double[] time) {
        super(exerciseId, exerciseName, isTimeBased);
        this.time = time;
    }
}
