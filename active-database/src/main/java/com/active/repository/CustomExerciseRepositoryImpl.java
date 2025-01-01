package com.active.repository;

import com.active.models.Exercise;
import com.active.models.exercise.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

import java.util.List;

public class CustomExerciseRepositoryImpl implements CustomExerciseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Exercise> findByCriteria(String title,
                                         Force force,
                                         Level level,
                                         Mechanic mechanic,
                                         Equipment equipment,
                                         List<Muscle> primaryMuscles,
                                         List<Muscle> secondaryMuscles,
                                         Category category,
                                         Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Exercise> query = cb.createQuery(Exercise.class);
        Root<Exercise> root = query.from(Exercise.class);

        Predicate predicate = cb.conjunction();

        if (title != null) {
            predicate = cb.and(predicate, cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (force != null) {
            predicate = cb.and(predicate, cb.equal(root.get("force"), force));
        }
        if (level != null) {
            predicate = cb.and(predicate, cb.equal(root.get("level"), level));
        }
        if (mechanic != null) {
            predicate = cb.and(predicate, cb.equal(root.get("mechanic"), mechanic));
        }
        if (equipment != null) {
            predicate = cb.and(predicate, cb.equal(root.get("equipment"), equipment));
        }
        if (primaryMuscles != null && !primaryMuscles.isEmpty()) {
            predicate = cb.and(predicate, root.join("primaryMuscles").in(primaryMuscles));
        }
        if (secondaryMuscles != null && !secondaryMuscles.isEmpty()) {
            predicate = cb.and(predicate, root.join("secondaryMuscles").in(secondaryMuscles));
        }
        if (category != null) {
            predicate = cb.and(predicate, cb.equal(root.get("category"), category));
        }

        query.where(predicate);
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));

        List<Exercise> resultList = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Exercise> countRoot = countQuery.from(Exercise.class);
        countQuery.select(cb.count(countRoot)).where(predicate);
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }
}
