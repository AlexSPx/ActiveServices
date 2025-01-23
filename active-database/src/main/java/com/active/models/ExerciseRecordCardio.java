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
@DiscriminatorValue("Cardio")
public class ExerciseRecordCardio extends ExerciseRecord {
    @ElementCollection
    @CollectionTable(
            name = "CardioExerciseReps",
            joinColumns = @JoinColumn(name = "exercise_record_id")
    )
    @Column(name = "durations")
    private List<Integer> durations;
}
