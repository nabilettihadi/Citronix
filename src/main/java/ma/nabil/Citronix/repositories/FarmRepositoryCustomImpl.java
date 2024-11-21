package ma.nabil.Citronix.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import ma.nabil.Citronix.dtos.requests.FarmSearchCriteria;
import ma.nabil.Citronix.entities.Farm;
import ma.nabil.Citronix.entities.Field;
import ma.nabil.Citronix.entities.Tree;

import java.util.ArrayList;
import java.util.List;

public class FarmRepositoryCustomImpl implements FarmRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Farm> searchFarms(FarmSearchCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Farm> query = cb.createQuery(Farm.class);
        Root<Farm> farm = query.from(Farm.class);

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getName() != null) {
            predicates.add(cb.like(cb.lower(farm.get("name")),
                    "%" + criteria.getName().toLowerCase() + "%"));
        }

        if (criteria.getLocation() != null) {
            predicates.add(cb.like(cb.lower(farm.get("location")),
                    "%" + criteria.getLocation().toLowerCase() + "%"));
        }

        if (criteria.getMinArea() != null) {
            predicates.add(cb.greaterThanOrEqualTo(farm.get("area"), criteria.getMinArea()));
        }

        if (criteria.getMaxArea() != null) {
            predicates.add(cb.lessThanOrEqualTo(farm.get("area"), criteria.getMaxArea()));
        }

        if (criteria.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(farm.get("creationDate"),
                    criteria.getStartDate()));
        }

        if (criteria.getEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(farm.get("creationDate"),
                    criteria.getEndDate()));
        }

        if (criteria.getMinTrees() != null) {
            Join<Farm, Field> fieldJoin = farm.join("fields", JoinType.LEFT);
            Join<Field, Tree> treeJoin = fieldJoin.join("trees", JoinType.LEFT);

            query.groupBy(farm);
            predicates.add(cb.greaterThanOrEqualTo(
                    cb.count(treeJoin), cb.literal(criteria.getMinTrees().longValue())));
        }

        if (criteria.getMinProductivity() != null) {
            Join<Farm, Field> fieldJoin = farm.join("fields", JoinType.LEFT);
            Join<Field, Tree> treeJoin = fieldJoin.join("trees", JoinType.LEFT);

            query.groupBy(farm);
            predicates.add(cb.greaterThanOrEqualTo(
                    cb.avg(treeJoin.<Double>get("productivity")),
                    criteria.getMinProductivity()));
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        return em.createQuery(query).getResultList();
    }
}