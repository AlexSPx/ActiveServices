package com.active.workoutservice.workout;

import com.active.workoutservice.workout.exceptions.WorkoutNotFoundException;
import com.active.workoutservice.workout.models.Workout;
import com.active.workoutservice.workout.models.WorkoutStructure;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = WorkoutService.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class WorkoutServiceTest {
    @MockBean
    private WorkoutRepository workoutRepository;

    private final WorkoutService workoutService;
    private Workout workout;

    @BeforeEach
    void setUp() {
        this.workout =  new Workout(
                "1",
                new WorkoutStructure(new String[]{"e1","e2"}),
                "Workout",
                "user",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void create_ShouldSaveWorkout() {
        when(workoutRepository.save(any())).thenReturn(workout);

        String id = workoutService.create(new String[]{"rec1", "rec2"}, "Workout", "user");

        assertEquals("1", id);
        verify(workoutRepository).save(any());
        assertEquals("Workout", workout.getTitle());
        assertEquals("user", workout.getCreatedBy());
        assertEquals(2, workout.getWorkoutStructure().getExerciseRecordIds().length);
        assertThat(workout.getWorkoutStructure().getExerciseRecordIds()).contains("e1", "e2");
    }

    @Test
    void getById_ShouldReturnWorkout() throws WorkoutNotFoundException {
        when(workoutRepository.findById("1")).thenReturn(Optional.of(workout));

        Workout result = workoutService.getById("1");

        assertEquals(workout, result);
        verify(workoutRepository).findById("1");
    }

    @Test
    void getById_ShouldThrowException_WhenNotFound() {
        when(workoutRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(WorkoutNotFoundException.class, () -> workoutService.getById("1"));
    }

    @Test
    void getByUser_ShouldReturnWorkouts() {
        Workout newWorkout =  new Workout("2", new WorkoutStructure(new String[]{"e11","e22"}), "Workout 2", "user", null, null);
        List<Workout> workouts = List.of(workout, newWorkout);

        when(workoutRepository.findByCreatedBy(eq("user123"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(workouts));

        List<Workout> result = workoutService.getByUser("user123", 0, 10);

        assertEquals(2, result.size());
        verify(workoutRepository).findByCreatedBy(eq("user123"), any(Pageable.class));
        assertThat(result).contains(workout, newWorkout);
    }
}