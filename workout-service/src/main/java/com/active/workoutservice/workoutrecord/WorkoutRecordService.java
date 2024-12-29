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
        if (uid == null || uid.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null or blank");
        }
        Pageable page = createPageable(offset, limit);
        return workoutRecordRepository.findByUid(uid, page).getContent();
    }

    private Pageable createPageable(int offset, int limit) {
        return PageRequest.of(offset, limit, Sort.by("createdAt").descending());
    }

}
