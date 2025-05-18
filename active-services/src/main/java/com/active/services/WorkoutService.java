package com.active.services;

import com.active.models.*;
import com.active.models.workout.Workout;
import com.active.models.workout.WorkoutRecord;
import com.active.models.workout.template.WorkoutTemplate;
import com.active.repository.ExerciseRecordRepository;
import com.active.repository.ExerciseRepository;
import com.active.repository.WorkoutRecordRepository;
import com.active.repository.WorkoutRepository;
import com.active.services.exceptions.WorkoutNotFoundException;
import com.active.services.structures.ExerciseStructure;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final WorkoutRecordRepository workoutRecordRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseRecordRepository exerciseRecordRepository;

    public Workout createWorkout(String name, WorkoutTemplate workoutTemplate, User user) {
        Workout workout = Workout.builder()
                .title(name)
                .workoutTemplate(workoutTemplate)
                .user(user)
                .build();

        return workoutRepository.save(workout);
    }

    public Page<Workout> getWorkoutsByUser(String uid, Pageable pageable) {
        return workoutRepository.findByUser_Id(uid, pageable);
    }

    public WorkoutRecord createWorkoutRecord(List<ExerciseStructure> records, String workoutId, long timeToComplete) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(String.format("Workout with id %s not found", workoutId)));

        Map<String, Exercise> exerciseRecords = exerciseRepository.findAllById(records.stream().map(ExerciseStructure::getExerciseId).toList())
                .stream()
                .collect(Collectors.toMap(Exercise::getId, Function.identity()));

        WorkoutRecord workoutRecord = WorkoutRecord.builder()
                .workout(workout)
                .timeToComplete(timeToComplete)
                .build();
        workoutRepository.save(workout);

        List<ExerciseRecord> exerciseRecordList = records.stream().map(recordStructure -> {
            Exercise exercise = exerciseRecords.get(recordStructure.getExerciseId());
            ExerciseRecord exerciseRecord = createExerciseRecord(recordStructure, exercise);
            exerciseRecord.setWorkoutRecord(workoutRecord);
            return exerciseRecord;
        }).toList();

        exerciseRecordRepository.saveAll(exerciseRecordList);

        workoutRecord.setExerciseRecords(exerciseRecordList);
        workoutRecordRepository.save(workoutRecord);

        return workoutRecord;
    }

    public void deleteWorkout(String workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));

        List<WorkoutRecord> workoutRecords = workout.getWorkoutRecords();
        if (workoutRecords != null && !workoutRecords.isEmpty()) {
            workoutRecords.forEach(record -> record.setWorkout(null));
            workoutRecordRepository.saveAll(workoutRecords);
        }

        workoutRepository.deleteById(workoutId);
    }

    public List<WorkoutRecordWithTitle> getWorkoutRecordsByUser(String uid) {
        List<WorkoutRecord> workoutRecords = workoutRecordRepository.findAllByWorkout_User_Id(uid, PageRequest.of(0, 100)).getContent();

        return workoutRecords.stream()
                .map(wr -> new WorkoutRecordWithTitle(
                        wr.getId(),
                        wr.getTimeToComplete(),
                        wr.getWorkout().getTitle(),
                        wr.getExerciseRecords(),
                        wr.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    private ExerciseRecord createExerciseRecord(ExerciseStructure recordStructure, Exercise exercise) {
        if(recordStructure.getDurations() == null) {
            return ExerciseRecordWeight.builder()
                    .exercise(exercise)
                    .repetitions(recordStructure.getRepetitions())
                    .weights(recordStructure.getWeights())
                    .build();
        } else {
            return ExerciseRecordCardio.builder()
                    .exercise(exercise)
                    .durations(recordStructure.getDurations())
                    .build();
        }
    }

}
