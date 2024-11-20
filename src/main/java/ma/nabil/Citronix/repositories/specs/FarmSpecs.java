package ma.nabil.Citronix.repositories.specs;

import jakarta.persistence.criteria.*;
import ma.nabil.Citronix.entities.Farm;
import ma.nabil.Citronix.entities.Field;
import ma.nabil.Citronix.entities.Tree;
import ma.nabil.Citronix.entities.Harvest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FarmSpecs {

    public static Specification<Farm> nameLike(String name) {
        return (root, query, cb) -> {
            if (name == null) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Farm> locationLike(String location) {
        return (root, query, cb) -> {
            if (location == null) return null;
            return cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
        };
    }

    public static Specification<Farm> areaBetween(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (max == null) return cb.greaterThanOrEqualTo(root.get("area"), min);
            if (min == null) return cb.lessThanOrEqualTo(root.get("area"), max);
            return cb.between(root.get("area"), min, max);
        };
    }

    public static Specification<Farm> creationDateBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;
            if (end == null) return cb.greaterThanOrEqualTo(root.get("creationDate"), start);
            if (start == null) return cb.lessThanOrEqualTo(root.get("creationDate"), end);
            return cb.between(root.get("creationDate"), start, end);
        };
    }

    public static Specification<Farm> hasMinTrees(Integer minTrees) {
        return (root, query, cb) -> {
            if (minTrees == null) return null;
            
            Join<Farm, Field> fieldJoin = root.join("fields");
            Join<Field, Tree> treeJoin = fieldJoin.join("trees");
            
            Subquery<Long> treeCount = query.subquery(Long.class);
            Root<Farm> subRoot = treeCount.from(Farm.class);
            
            treeCount.select(cb.count(subRoot.join("fields").join("trees")))
                    .where(cb.equal(subRoot, root));
            
            return cb.greaterThanOrEqualTo(treeCount, minTrees.longValue());
        };
    }

    public static Specification<Farm> hasMinProductivity(Double minProductivity) {
        return (root, query, cb) -> {
            if (minProductivity == null) return null;
            
            Join<Farm, Field> fieldJoin = root.join("fields");
            Join<Field, Harvest> harvestJoin = fieldJoin.join("harvests");
            
            Subquery<Double> productivity = query.subquery(Double.class);
            Root<Farm> subRoot = productivity.from(Farm.class);
            
            productivity.select(cb.sum(subRoot.join("fields").join("harvests").get("totalQuantity")))
                      .where(cb.equal(subRoot, root));
            
            return cb.greaterThanOrEqualTo(productivity, minProductivity);
        };
    }
}