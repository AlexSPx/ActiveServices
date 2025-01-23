package com.active.models.workout.template;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("Cardio")
public class TemplateExerciseCardio extends TemplateExercise {

    @ElementCollection
    @CollectionTable(
            name = "CardioTemplateDurations",
            joinColumns = @JoinColumn(name = "template_exercise_id")
    )
    @Column(name = "durations")
    private List<Integer> durations;

}
