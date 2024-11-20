package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByHarvestId(Long harvestId);

    List<Sale> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT CASE WHEN SUM(s.quantity) <= h.totalQuantity THEN true ELSE false END " +
            "FROM Sale s JOIN s.harvest h WHERE h.id = :harvestId")
    boolean isQuantityValidForHarvest(@Param("harvestId") Long harvestId);

    @Query("SELECT SUM(s.revenue) FROM Sale s WHERE s.harvest.field.id = :fieldId " +
            "AND s.saleDate BETWEEN :startDate AND :endDate")
    Double calculateRevenue(@Param("fieldId") Long fieldId,
                            @Param("startDate") LocalDate startDate,
                            @Param("endDate") LocalDate endDate);
}