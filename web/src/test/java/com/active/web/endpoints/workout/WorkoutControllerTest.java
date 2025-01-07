package com.active.web.endpoints.workout;

import com.active.authservice.token.TokenService;
import com.active.models.Exercise;
import com.active.models.User;
import com.active.models.exercise.*;
import com.active.models.workout.Workout;
import com.active.models.workout.WorkoutRecord;
import com.active.models.workout.template.WorkoutTemplate;
import com.active.repository.*;
import com.active.services.WorkoutService;
import com.active.services.WorkoutTemplateService;
import com.active.services.structures.ExerciseStructure;
import com.active.web.dto.workout.WorkoutCreateRequest;
import com.active.web.dto.workoutrecord.WorkoutRecordCreateRequest;
import com.active.web.endpoints.TestConfigurations;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(classes = {WorkoutController.class, TestConfigurations.class})
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WorkoutControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRecordRepository workoutRecordRepository;
    private final ExerciseRecordRepository exerciseRecordRepository;
    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;

    private final WorkoutService workoutService;
    private final WorkoutTemplateService workoutTemplateService;
    private final TokenService tokenService;

    private User user;
    private String token;

    @AfterEach
    void tearDown() {
        exerciseRecordRepository.deleteAll();
        workoutRecordRepository.deleteAll();
        exerciseRepository.deleteAll();
    }

        @BeforeAll
        void setUp() {
            User user = userRepository.save(User.builder()
                    .username("userA")
                    .email("usera@mail.com")
                    .firstname("user")
                    .lastname("A")
                    .password("password")
                    .build()
            );

            this.token = tokenService.generateTokenPair(user.getId()).getToken();
            this.user = user;

            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        }

        @Test
        void createWorkoutWeightsOnlyTest() throws Exception {
            Exercise exercise1 = loadExercise("Exercise 1");
            Exercise exercise2 = loadExercise("Exercise 2");

            WorkoutCreateRequest workoutCreateRequest = new WorkoutCreateRequest("Workout 1", List.of(
                    new ExerciseStructure(exercise1.getId(), List.of(3,4,5), List.of(10.0,11.0,12.0), null),
                    new ExerciseStructure(exercise2.getId(), null, null, List.of(30,40,50))
            ));

            String responseString = mockMvc.perform(post("/api/workout")
                            .contentType("application/json")
                            .header("Authorization", "Bearer " + token)
                            .content(objectMapper.writeValueAsString(workoutCreateRequest)))
                            .andExpect(status().isCreated())
                            .andReturn().getResponse().getContentAsString();

            Workout workout = objectMapper.readValue(responseString, Workout.class);

            assertThat(workout.getTitle(), is("Workout 1"));
            assertThat(workout.getWorkoutTemplate().getTemplateExercises().size(), is(2));
            assertThat(workout.getWorkoutTemplate().getTemplateExercises(), hasItem(allOf(
                    hasProperty("exercise", hasProperty("title", is("Exercise 1"))),
                    hasProperty("repetitions", contains(3, 4, 5)),
                    hasProperty("weights", contains(10.0, 11.0, 12.0))
            )));
            assertThat(workout.getWorkoutTemplate().getTemplateExercises(), hasItem(allOf(
                    hasProperty("exercise", hasProperty("title", is("Exercise 2"))),
                    hasProperty("durations", contains(30, 40, 50))
            )));

            workoutService.deleteWorkout(workout.getId());
        }

        @Test
        void createWorkoutRecordTest() throws Exception {
            Exercise exercise1 = loadExercise("Exercise 1");
            Exercise exercise2 = loadExercise("Exercise 2");

            WorkoutCreateRequest workoutCreateRequest = new WorkoutCreateRequest("Workout 1", List.of(
                    new ExerciseStructure(exercise1.getId(), List.of(3,4,5), List.of(10.0,11.0,12.0), null),
                    new ExerciseStructure(exercise2.getId(), null, null, List.of(30,40,50))
            ));

            WorkoutTemplate workoutTemplate = workoutTemplateService.createWorkoutTemplate(workoutCreateRequest.getTemplateExerciseStructures());
            Workout workout = workoutService.createWorkout("Workout 1", workoutTemplate, user);

            WorkoutRecordCreateRequest wrcr = WorkoutRecordCreateRequest.builder()
                    .workoutId(workout.getId())
                    .timeToComplete(4600L)
                    .exerciseRecords(List.of(
                            ExerciseStructure.builder()
                                    .exerciseId(exercise1.getId())
                                    .repetitions(List.of(3,4,5))
                                    .weights(List.of(10.0,11.0,12.0))
                                    .build(),
                            ExerciseStructure.builder()
                                    .exerciseId(exercise2.getId())
                                    .durations(List.of(30,40,50))
                                    .build()
                    ))
                    .build();

            String responseString = mockMvc.perform(post("/api/workout/record")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(wrcr))
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();

            WorkoutRecord workoutRecord = objectMapper.readValue(responseString, WorkoutRecord.class);

            assertThat(workoutRecord.getExerciseRecords().size(), is(2));
            assertThat(workoutRecord.getTimeToComplete(), is(4600L));
            assertThat(workoutRecord.getExerciseRecords(), hasItem(allOf(
                    hasProperty("exercise", hasProperty("title", is("Exercise 1"))),
                    hasProperty("repetitions", contains(3, 4, 5)),
                    hasProperty("weights", contains(10.0, 11.0, 12.0))
            )));
            assertThat(workoutRecord.getExerciseRecords(), hasItem(allOf(
                    hasProperty("exercise", hasProperty("title", is("Exercise 2"))),
                    hasProperty("durations", contains(30, 40, 50))
            )));

            assertThat(workoutRecordRepository.count(), is(1L));
            assertThat(exerciseRecordRepository.count(), is(2L));

            // Deleting workout should keep the records
            workoutService.deleteWorkout(workout.getId());

            assertThat(workoutRepository.findById(workout.getId()).isEmpty(), is(true));
            assertThat(workoutRecordRepository.count(), is(1L));
            assertThat(exerciseRecordRepository.count(), is(2L));
        }

        private Exercise loadExercise(String title) {
            return exerciseRepository.save(Exercise.builder()
                    .title(title)
                    .force(Force.PUSH)
                    .level(Level.BEGINNER)
                    .mechanic(Mechanic.COMPOUND)
                    .equipment(Equipment.BANDS)
                    .primaryMuscles(List.of(Muscle.MIDDLE_BACK, Muscle.BICEPS))
                    .secondaryMuscles(List.of(Muscle.LATS))
                    .instructions(List.of("Instruction 1", "Instruction 2"))
                    .category(Category.OLYMPIC_WEIGHTLIFTING)
                    .build());
        }
}
