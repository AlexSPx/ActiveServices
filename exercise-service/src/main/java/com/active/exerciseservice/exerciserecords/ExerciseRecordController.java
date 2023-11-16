package com.active.exerciseservice.exerciserecords;

import com.active.exerciseservice.exerciserecords.dto.ExerciseRecordRequest;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercise/record")
@RequiredArgsConstructor
public class ExerciseRecordController {

    private final ExerciseRecordService exerciseRecordService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createExerciseRecord(@RequestBody ExerciseRecordRequest exerciseRecordRequest){
        return exerciseRecordService.createExerciseRecord(exerciseRecordRequest);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> createManyExerciseRecord(@RequestBody List<ExerciseRecordRequest> exerciseRecordRequest){
        return exerciseRecordService.createManyExerciseRecords(exerciseRecordRequest);
    }

    @PostMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<ExerciseRecord> getExerciseRecords(@RequestBody List<String> recordIds){
        return exerciseRecordService.getExerciseRecords(recordIds);
    }
}
