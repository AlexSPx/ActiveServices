package com.active.exerciseservice.exercisemanagment;

import com.active.exerciseservice.exerciserecords.ExerciseRecordRepository;
import com.active.exerciseservice.exerciserecords.ExerciseRecordService;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecord;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecordCardio;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecordWeight;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ExerciseRecordService.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ExerciseRecordServiceTest {

    @MockBean
    private ExerciseRecordRepository exerciseRecordRepository;

    private final ExerciseRecordService exerciseRecordService;

    @Test
    void createExerciseRecord_ShouldSaveAndReturnId_Weight() {
        ExerciseRecordWeight record = ExerciseRecordWeight.builder()
                .exerciseId("ex123")
                .exerciseName("Push-ups")
                .isTimeBased(false)
                .reps(new int[]{10, 10, 10})
                .weight(new double[]{10.0, 10.0, 10.0})
                .build();
        record.setId("record123");

        when(exerciseRecordRepository.save(any(ExerciseRecord.class))).thenReturn(record);

        String id = exerciseRecordService.createExerciseRecord(record);

        assertEquals("record123", id);
        verify(exerciseRecordRepository).save(record);
        assertEquals(3, record.getWeight().length);
        assertThat(record.getWeight()).containsExactly(10.0, 10.0, 10.0);
        assertEquals(3, record.getReps().length);
        assertThat(record.getReps()).containsExactly(10, 10, 10);
    }

    @Test
    void createExerciseRecord_ShouldSaveAndReturnId_Cardio() {
        ExerciseRecordCardio record = ExerciseRecordCardio.builder()
                .exerciseId("ex123")
                .exerciseName("sprints")
                .isTimeBased(false)
                .time(new double[]{10.0, 10.0, 10.0})
                .build();
        record.setId("record123");

        when(exerciseRecordRepository.save(any(ExerciseRecord.class))).thenReturn(record);

        String id = exerciseRecordService.createExerciseRecord(record);

        assertEquals("record123", id);
        verify(exerciseRecordRepository).save(record);
        assertEquals(3, record.getTime().length);
        assertThat(record.getTime()).containsExactly(10.0, 10.0, 10.0);
    }

    @Test
    void createManyExerciseRecords_ShouldSaveAllAndReturnIds() {
        List<ExerciseRecord> records = List.of(
                ExerciseRecord.builder().exerciseId("ex123").exerciseName("Squats").build(),
                ExerciseRecord.builder().exerciseId("ex124").exerciseName("Lunges").build()
        );
        records.get(0).setId("rec123");
        records.get(1).setId("rec124");

        when(exerciseRecordRepository.saveAll(records)).thenReturn(records);

        List<String> ids = exerciseRecordService.createManyExerciseRecords(records);

        assertEquals(List.of("rec123", "rec124"), ids);
        verify(exerciseRecordRepository).saveAll(records);
    }

    @Test
    void getExerciseRecords_ShouldReturnValidRecords() {
        List<ExerciseRecord> records = List.of(
                ExerciseRecord.builder().id("rec123").exerciseId("ex123").exerciseName("Push-ups").build(),
                ExerciseRecord.builder().id("rec124").exerciseId("ex124").exerciseName("Pull-ups").build()
        );

        when(exerciseRecordRepository.findAllById(List.of("rec123", "rec124"))).thenReturn(records);

        List<ExerciseRecord> result = exerciseRecordService.getExerciseRecords(List.of("rec123", "rec124"));

        assertEquals(2, result.size());
        assertEquals("Push-ups", result.get(0).getExerciseName());
        assertEquals("Pull-ups", result.get(1).getExerciseName());
        verify(exerciseRecordRepository).findAllById(List.of("rec123", "rec124"));
    }
}
