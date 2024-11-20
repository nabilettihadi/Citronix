package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.entities.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {
    List<Tree> findByFieldId(Long fieldId);

    List<Tree> findByPlantingDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT t FROM Tree t WHERE t.field.id = :fieldId AND t.plantingDate <= :date")
    List<Tree> findProductiveTrees(@Param("fieldId") Long fieldId, @Param("date") LocalDate date);

    @Query("SELECT t FROM Tree t WHERE t.field.id = :fieldId " +
            "AND (SELECT SUM(hd.quantity) FROM HarvestDetail hd WHERE hd.tree = t) >= :minProduction")
    List<Tree> findTreesWithMinProduction(@Param("fieldId") Long fieldId,
                                          @Param("minProduction") Double minProduction);
}