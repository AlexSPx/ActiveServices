package com.active.web.endpoints.workout;

import com.active.models.Exercise;
import com.active.services.ExerciseService;
import com.active.services.structures.ExerciseSearchStructure;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @GetMapping("/search")
    public ResponseEntity<List<Exercise>> searchExercise(@RequestParam("title") String string) {
        log.info("Searching for exercises...");
        ExerciseSearchStructure exerciseSearchStructure = new ExerciseSearchStructure();
        exerciseSearchStructure.setTitle(string);
        exerciseSearchStructure.setPageable(PageRequest.of(0, 10));
        log.info("Searching for exercises with title: {}", exerciseService);
        return ResponseEntity.ok(exerciseService.searchExercise(exerciseSearchStructure).getContent());
    }
}
