package com.active.repository;

import com.active.models.ExerciseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {
    @Query("SELECT e FROM ExerciseRecord e WHERE e.id IN :ids")
    List<ExerciseRecord> findAllById(@Param("ids") List<Long> ids);
}
