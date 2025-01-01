package com.active.models.workout.template;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "WorkoutTemplate")
public class WorkoutTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name; // Name of the template (e.g., "Leg Day", "Cardio Blast")

    @OneToMany(mappedBy = "workoutTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TemplateExercise> templateExercises;

    // Getters and Setters
}
