package com.active.web.endpoints;

import com.active.exerciseservice.exerciserecords.ExerciseRecordFactory;
import com.active.exerciseservice.exerciserecords.ExerciseRecordService;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecord;
import com.active.web.dto.exerciserecords.ExerciseRecordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise/record")
@RequiredArgsConstructor
public class ExerciseRecordController {

    private final ExerciseRecordService exerciseRecordService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createExerciseRecord(@RequestBody ExerciseRecordRequest exerciseRecordRequest){

        ExerciseRecord exerciseRecord = ExerciseRecordFactory.createExerciseRecord(
                exerciseRecordRequest.getExerciseName(),
                exerciseRecordRequest.isTimeBased(),
                exerciseRecordRequest.getReps(),
                exerciseRecordRequest.getWeight(),
                exerciseRecordRequest.getTime()
        );


        return exerciseRecordService.createExerciseRecord(exerciseRecord);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> createManyExerciseRecord(@RequestBody List<ExerciseRecordRequest> exerciseRecordRequest){
        return exerciseRecordService.createManyExerciseRecords(convertExerciseRecordRequestToExerciseRecord(exerciseRecordRequest));
    }

    @PostMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<ExerciseRecord> getExerciseRecords(@RequestBody List<String> recordIds){
        return exerciseRecordService.getExerciseRecords(recordIds);
    }

    private List<ExerciseRecord> convertExerciseRecordRequestToExerciseRecord(List<ExerciseRecordRequest> exerciseRecordRequests) {
        return exerciseRecordRequests.stream().map(exerciseRecordRequest -> ExerciseRecordFactory.createExerciseRecord(
                exerciseRecordRequest.getExerciseName(),
                exerciseRecordRequest.isTimeBased(),
                exerciseRecordRequest.getReps(),
                exerciseRecordRequest.getWeight(),
                exerciseRecordRequest.getTime()
        )).toList();
    }
}

