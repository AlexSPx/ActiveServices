package com.active.models.workout.template;

import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Cardio")
public class TemplateExerciseCardio extends TemplateExercise {

    @ElementCollection
    @CollectionTable(
            name = "CardioTemplateDurations",
            joinColumns = @JoinColumn(name = "template_exercise_id") // Refers to TemplateExercise.id
    )
    @Column(name = "duration")
    private List<Integer> durations;

    // Getters and Setters
}
