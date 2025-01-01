package com.active.models.workout.template;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("Weight")
public class TemplateExerciseWeight extends TemplateExercise {

    @ElementCollection
    @CollectionTable(
            name = "WeightTemplateRepetitions",
            joinColumns = @JoinColumn(name = "template_exercise_id") // Refers to TemplateExercise.id
    )
    @Column(name = "repetition")
    private List<Integer> repetitions;

    @ElementCollection
    @CollectionTable(
            name = "WeightTemplateWeights",
            joinColumns = @JoinColumn(name = "template_exercise_id") // Refers to TemplateExercise.id
    )
    @Column(name = "weight")
    private List<Double> weights;

    // Getters and Setters
}