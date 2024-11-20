package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.entities.Harvest;
import ma.nabil.Citronix.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    List<Harvest> findByFieldId(Long fieldId);

    List<Harvest> findByFieldIdAndSeasonAndYear(Long fieldId, Season season, Integer year);

    List<Harvest> findByHarvestDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT h FROM Harvest h WHERE h.field.id = :fieldId " +
            "AND h.totalQuantity >= :minQuantity")
    List<Harvest> findHarvestsWithMinQuantity(@Param("fieldId") Long fieldId,
                                              @Param("minQuantity") Double minQuantity);

    @Query("SELECT SUM(h.totalQuantity) FROM Harvest h WHERE h.field.id = :fieldId " +
            "AND h.harvestDate BETWEEN :startDate AND :endDate")
    Double calculateTotalProduction(@Param("fieldId") Long fieldId,
                                    @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);
}