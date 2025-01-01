package com.active.models.workout.template;

import com.active.models.Exercise;
import jakarta.persistence.*;

@Entity
@Table(name = "TemplateExerciseBase")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "exerciseType", discriminatorType = DiscriminatorType.STRING)
public abstract class TemplateExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private WorkoutTemplate workoutTemplate;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
}

