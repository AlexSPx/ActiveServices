package com.active.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Weight")
public class ExerciseRecordWeight extends ExerciseRecord {
    @ElementCollection
    @CollectionTable(
            name = "WeightExerciseReps",
            joinColumns = @JoinColumn(name = "exercise_record_id")
    )
    @Column(name = "repetition")
    private List<Integer> repetitions;

    // Store weights as an array of doubles
    @ElementCollection
    @CollectionTable(
            name = "WeightExerciseWeights",
            joinColumns = @JoinColumn(name = "exercise_record_id")
    )
    @Column(name = "weight")
    private List<Double> weights;
}