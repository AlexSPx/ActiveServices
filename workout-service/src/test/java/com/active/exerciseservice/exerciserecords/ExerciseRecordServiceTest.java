package com.active.exerciseservice.exerciserecords;

import com.active.exerciseservice.exercisemanagment.ExerciseModel;
import com.active.exerciseservice.exercisemanagment.ExerciseService;
import com.active.exerciseservice.exercisemanagment.repository.ExerciseRepository;
import com.active.exerciseservice.types.BodyPart;
import com.active.exerciseservice.types.ExerciseType;
import com.active.exerciseservice.types.Level;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ExerciseService.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ExerciseServiceTest {

    @MockBean
    private ExerciseRepository exerciseRepository;

    private final ExerciseService exerciseService;

    @Test
    void createExercise_ShouldSaveAndReturnId() {
        ExerciseModel exercise = ExerciseModel.builder()
                .title("Bench Press")
                .description("Chest workout")
                .type(ExerciseType.STRENGTH)
                .bodyPart(BodyPart.CHEST)
                .level(Level.INTERMEDIATE)
                .build();
        exercise.setId("exercise123");

        when(exerciseRepository.save(any(ExerciseModel.class))).thenReturn(exercise);

        String id = exerciseService.createExercise(exercise);

        assertEquals("exercise123", id);
        verify(exerciseRepository).save(exercise);
    }

    @Test
    void getAllExercises_ShouldReturnAllExercises() {
        List<ExerciseModel> exercises = List.of(
                ExerciseModel.builder().id("ex123").title("Deadlift").build(),
                ExerciseModel.builder().id("ex124").title("Squats").build()
        );

        when(exerciseRepository.findAll()).thenReturn(exercises);

        List<ExerciseModel> result = exerciseService.getAllExercises();

        assertEquals(2, result.size());
        assertEquals("Deadlift", result.get(0).getTitle());
        assertEquals("Squats", result.get(1).getTitle());
        verify(exerciseRepository).findAll();
    }

    @Test
    void searchExercises_ShouldReturnFilteredResults() {
        List<ExerciseModel> exercises = List.of(
                ExerciseModel.builder().id("ex123").title("Push-ups").description("Basic").build()
        );
        Page<ExerciseModel> exercisePage = new PageImpl<>(exercises);

        when(exerciseRepository.findByCriteria(eq("Push-ups"), eq("Basic"), eq(ExerciseType.OLYMPIC_WEIGHTLIFTING),
                eq(BodyPart.CHEST), eq(Level.BEGINNER), any(Pageable.class)))
                .thenReturn(exercisePage);

        List<ExerciseModel> result = exerciseService.searchExercises(
                "Push-ups", "Basic", ExerciseType.OLYMPIC_WEIGHTLIFTING, BodyPart.CHEST, Level.BEGINNER, 0, 10);

        assertEquals(1, result.size());
        assertEquals("Push-ups", result.get(0).getTitle());
        assertEquals("Basic", result.get(0).getDescription());
        verify(exerciseRepository).findByCriteria(
                eq("Push-ups"), eq("Basic"), eq(ExerciseType.OLYMPIC_WEIGHTLIFTING),
                eq(BodyPart.CHEST), eq(Level.BEGINNER), any(Pageable.class));
    }
}