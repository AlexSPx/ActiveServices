package com.active.models;

import com.active.models.exercise.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Force force;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private Mechanic mechanic;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private Equipment equipment;

    @ElementCollection
    @CollectionTable(name = "PrimaryMuscles", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "muscle")
    @Enumerated(EnumType.STRING)
    private List<Muscle> primaryMuscles;

    @ElementCollection
    @CollectionTable(name = "SecondaryMuscles", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "muscle")
    @Enumerated(EnumType.STRING)
    private List<Muscle> secondaryMuscles;

    @ElementCollection
    @CollectionTable(name = "ExerciseInstructions", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "instruction")
    private List<String> instructions;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;
}
