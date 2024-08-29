package com.active.exerciseservice.exercisemanagment.repository;

import com.active.exerciseservice.exercisemanagment.ExerciseModel;
import com.active.exerciseservice.types.BodyPart;
import com.active.exerciseservice.types.ExerciseType;
import com.active.exerciseservice.types.Level;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@AllArgsConstructor
public class CustomExerciseRepositoryImpl implements CustomExerciseRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public Page<ExerciseModel> findByCriteria(
            String title,
            String description,
            ExerciseType type,
            BodyPart bodyPart,
            Level level,
            Pageable pageable
    ) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        if (title != null) {
            criteria.and("title").regex(title, "i");
        }

        if (description != null) {
            criteria.and("description").regex(description, "i");
        }

        if (type != null) {
            criteria.and("type").is(type);
        }

        if (bodyPart != null) {
            criteria.and("bodyPart").is(bodyPart);
        }

        if (level != null) {
            criteria.and("level").is(level);
        }

        query.addCriteria(criteria);
        long count = mongoTemplate.count(query, ExerciseModel.class);
        List<ExerciseModel> resultList = mongoTemplate.find(query.with(pageable), ExerciseModel.class);

        return new PageImpl<>(resultList, pageable, count);
    }
}
