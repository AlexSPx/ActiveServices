package com.active.workoutservice.workoutrecord;

import com.active.workoutservice.workoutrecord.exceptions.WorkoutRecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = WorkoutRecordService.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class WorkoutRecordServiceTest {

    @MockBean
    private WorkoutRecordRepository workoutRecordRepository;

    private final WorkoutRecordService workoutRecordService;

    @Test
    void create_ShouldSaveWorkoutRecord() {
        WorkoutRecord record = createRandomWorkoutRecord();

        when(workoutRecordRepository.save(any())).thenReturn(record);

        String id = workoutRecordService.create(record, "user123");

        assertEquals(record.getId(), id);
        verify(workoutRecordRepository).save(record);
    }

    @Test
    void getById_ShouldReturnWorkoutRecord() throws WorkoutRecordNotFoundException {
        WorkoutRecord record = createRandomWorkoutRecord();

        when(workoutRecordRepository.findById(record.getId())).thenReturn(Optional.of(record));

        WorkoutRecord result = workoutRecordService.getById(record.getId());

        assertEquals(record, result);
        verify(workoutRecordRepository).findById(record.getId());
    }

    @Test
    void getById_ShouldThrowException_WhenNotFound() {
        when(workoutRecordRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(WorkoutRecordNotFoundException.class, () -> workoutRecordService.getById("1"));
    }

    @Test
    void getByUser_ShouldReturnWorkoutRecords() {
        List<WorkoutRecord> records = List.of(createRandomWorkoutRecord(), createRandomWorkoutRecord());

        when(workoutRecordRepository.findByUid(eq("user123"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(records));

        List<WorkoutRecord> result = workoutRecordService.getByUser("user123", 0, 10);

        assertEquals(2, result.size());
        verify(workoutRecordRepository).findByUid(eq("user123"), any(Pageable.class));
    }

    private WorkoutRecord createRandomWorkoutRecord() {
        return WorkoutRecord.builder()
                .id(UUID.randomUUID().toString())
                .workoutId(UUID.randomUUID().toString())
                .workoutTitle(UUID.randomUUID().toString())
                .uid("user123")
                .duration(3600)
                .finishedAt(null)
                .exerciseRecordIds(new String[]{"1", "2"})
                .build();
    }
}
