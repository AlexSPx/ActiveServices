package com.active.repository;

import com.active.models.Exercise;
import com.active.models.exercise.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

import java.util.List;

public class CustomExerciseRepositoryImpl implements CustomExerciseRepository {

    @PersistenceContext
    private EntityManager entityManager;

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
        Join<Exercise, Muscle> primaryMusclesJoin = null;
        if (primaryMuscles != null && !primaryMuscles.isEmpty()) {
            primaryMusclesJoin = root.join("primaryMuscles");
            predicate = cb.and(predicate, primaryMusclesJoin.in(primaryMuscles));
        }
        Join<Exercise, Muscle> secondaryMusclesJoin = null;
        if (secondaryMuscles != null && !secondaryMuscles.isEmpty()) {
            secondaryMusclesJoin = root.join("secondaryMuscles");
            predicate = cb.and(predicate, secondaryMusclesJoin.in(secondaryMuscles));
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
        Predicate countPredicate = cb.conjunction();

        if (title != null) {
            countPredicate = cb.and(countPredicate, cb.like(cb.lower(countRoot.get("title")), "%" + title.toLowerCase() + "%"));
        }
        // Repeat other conditions for countQuery

        countQuery.select(cb.count(countRoot)).where(countPredicate);
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }

}
