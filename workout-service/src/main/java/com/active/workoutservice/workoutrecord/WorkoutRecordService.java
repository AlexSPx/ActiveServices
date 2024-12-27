package com.active.workoutservice.workoutrecord;

import com.active.workoutservice.workoutrecord.exceptions.WorkoutRecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutRecordService {
    private final WorkoutRecordRepository workoutRecordRepository;

    public String create(WorkoutRecord workoutRecord, String uid) {
        return workoutRecordRepository.save(workoutRecord).getId();
    }

    public WorkoutRecord getById(String id) throws WorkoutRecordNotFoundException {
        return workoutRecordRepository.findById(id)
                .orElseThrow(WorkoutRecordNotFoundException::new);
    }

    public List<WorkoutRecord> getByUser(String uid, int offset, int limit) {
        Pageable page = PageRequest.of(offset, limit, Sort.by("createdAt").descending());
        return workoutRecordRepository.findByUid(uid, page).getContent();
    }
}
