package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long>, JpaSpecificationExecutor<Field> {
    boolean existsByNameAndFarmId(String name, Long farmId);

    List<Field> findByFarmId(Long farmId);

    @Query("SELECT COUNT(t) FROM Field f JOIN f.trees t WHERE f.id = :fieldId")
    long countTrees(@Param("fieldId") Long fieldId);

    @Query("SELECT f FROM Field f WHERE f.farm.id = :farmId AND f.area <= :maxArea")
    List<Field> findFieldsWithMaxArea(@Param("farmId") Long farmId, @Param("maxArea") Double maxArea);

    @Query("SELECT SUM(f.area) FROM Field f WHERE f.farm.id = :farmId")
    Double calculateTotalAreaByFarmId(@Param("farmId") Long farmId);
}