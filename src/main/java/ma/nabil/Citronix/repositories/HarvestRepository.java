package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.entities.Harvest;
import ma.nabil.Citronix.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    List<Harvest> findByFieldId(Long fieldId);

    List<Harvest> findByFieldIdAndYear(Long fieldId, Integer year);

    List<Harvest> findBySeason(Season season);

    Optional<Harvest> findByFieldIdAndSeasonAndYear(Long fieldId, Season season, Integer year);

    boolean existsByFieldIdAndSeasonAndYear(Long fieldId, Season season, Integer year);

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Harvest h " +
            "JOIN h.harvestDetails d WHERE d.tree.id = :treeId " +
            "AND h.season = :season AND h.year = :year")
    boolean existsByTreeIdAndSeasonAndYear(Long treeId, Season season, Integer year);

    @Query("SELECT SUM(h.totalQuantity) FROM Harvest h WHERE h.field.id = :fieldId AND h.year = :year")
    Double calculateTotalQuantityByFieldAndYear(@Param("fieldId") Long fieldId, @Param("year") Integer year);
}