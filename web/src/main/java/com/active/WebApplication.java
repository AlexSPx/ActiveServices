package com.active;

import com.active.models.Exercise;
import com.active.services.ExerciseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.active")
@EnableJpaAuditing
public class WebApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class, args);

        ExerciseService es = context.getBean(ExerciseService.class);

//        try {
//            List<Exercise> exercises = parseJson("/home/alexspx/IdeaProjects/ActiveServices/web/src/main/resources/exercises.json");
//
//            for (Exercise exercise : exercises) {
//
//                es.createExercise(exercise);
//                System.out.println("Saved exercise: " + exercise.getTitle() + " with id: " + exercise.getId());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public static List<Exercise> parseJson(String filePath) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                new File(filePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Exercise.class)
        );
    }
}
