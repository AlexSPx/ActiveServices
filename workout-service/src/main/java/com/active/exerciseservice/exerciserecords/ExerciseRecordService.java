package com.active.exerciseservice.exerciserecords;

import com.active.models.ExerciseRecord;
import com.active.repository.ExerciseRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseRecordService {

    private final ExerciseRecordRepository exerciseRecordRepository;

    public Long createExerciseRecord(ExerciseRecord exerciseRecord) {
        return exerciseRecordRepository.save(exerciseRecord).getId();
    }

    public List<Long> createManyExerciseRecords(List<ExerciseRecord> exerciseRecords) {
        return exerciseRecordRepository.saveAll(exerciseRecords).stream().map(ExerciseRecord::getId).collect(Collectors.toList());
    }

    public List<ExerciseRecord> getExerciseRecords(List<Long> recordIds) {
        return exerciseRecordRepository.findAllById(recordIds);
    }
}
