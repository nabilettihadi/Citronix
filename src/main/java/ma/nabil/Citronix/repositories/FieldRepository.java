package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByFarmId(Long farmId);

    Optional<Field> findByNameAndFarmId(String name, Long farmId);

    List<Field> findByAreaGreaterThanEqual(Double minArea);

    @Query("SELECT f FROM Field f WHERE SIZE(f.trees) >= :minTrees")
    List<Field> findFieldsWithMinTrees(@Param("minTrees") int minTrees);

    @Query("SELECT f FROM Field f WHERE f.farm.id = :farmId " +
            "AND (SELECT SUM(h.totalQuantity) FROM Harvest h WHERE h.field = f) >= :minProduction")
    List<Field> findFieldsWithMinProduction(@Param("farmId") Long farmId,
                                            @Param("minProduction") Double minProduction);

    boolean existsByNameAndFarmId(String name, Long farmId);
}