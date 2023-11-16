package com.active.exerciseservice.exerciserecords;

import com.active.exerciseservice.exerciserecords.dto.ExerciseRecordRequest;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecord;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecordCardio;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecordWeight;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseRecordService {

    private final ExerciseRecordRepository exerciseRecordRepository;

    public String createExerciseRecord(ExerciseRecordRequest exerciseRecordRequest) {
        return exerciseRecordRepository.save(convertFromDTO(exerciseRecordRequest)).getId();
    }

    public List<String> createManyExerciseRecords(List<ExerciseRecordRequest> exerciseRecordRequests) {
        List<ExerciseRecord> exerciseRecordList = new ArrayList<>();

        for (ExerciseRecordRequest request : exerciseRecordRequests){
            log.info(convertFromDTO(request).toString());
            exerciseRecordList.add(convertFromDTO(request));
        }

        return exerciseRecordRepository.saveAll(exerciseRecordList).stream().map(ExerciseRecord::getId).collect(Collectors.toList());
    }

    private ExerciseRecord convertFromDTO(ExerciseRecordRequest exerciseRecordRequest) {
        if(exerciseRecordRequest.isTimeBased()){
            return new ExerciseRecordCardio(
                    exerciseRecordRequest.getExerciseId(),
                    exerciseRecordRequest.getExerciseName(),
                    true,
                    exerciseRecordRequest.getTime()
            );
        } else {
            return new ExerciseRecordWeight(
                    exerciseRecordRequest.getExerciseId(),
                    exerciseRecordRequest.getExerciseName(),
                    false,
                    exerciseRecordRequest.getReps(),
                    exerciseRecordRequest.getWeight()
            );
        }
    }

    public List<ExerciseRecord> getExerciseRecords(List<String> recordIds) {
        return exerciseRecordRepository.findAllById(recordIds);
    }
}
