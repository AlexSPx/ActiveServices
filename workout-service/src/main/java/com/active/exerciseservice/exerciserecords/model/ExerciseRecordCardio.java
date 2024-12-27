package com.active.exerciseservice.exerciserecords.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRecordCardio extends ExerciseRecord {
    private double[] time;

    public ExerciseRecordCardio(String exerciseId, String exerciseName, boolean isTimeBased, double[] time) {
        super(exerciseId, exerciseName, isTimeBased);
        this.time = time;
    }
}
