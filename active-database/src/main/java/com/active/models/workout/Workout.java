package com.active.models.workout;

import com.active.models.User;
import com.active.models.workout.template.WorkoutTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Workout")
@EntityListeners(AuditingEntityListener.class)
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column
    @CreatedDate
    private LocalDate createdAt;

    @Column
    @LastModifiedDate
    private LocalDate updatedAt;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "template_id", nullable = false)
    private WorkoutTemplate workoutTemplate;

    @OneToMany(mappedBy = "workout")
    private List<WorkoutRecord> workoutRecords;
}
