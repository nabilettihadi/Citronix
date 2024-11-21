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
    
    List<Sale> findByClientContainingIgnoreCase(String client);
    
    @Query("SELECT SUM(s.quantity * s.unitPrice) FROM Sale s WHERE s.harvest.id = :harvestId")
    Double calculateTotalRevenueByHarvestId(@Param("harvestId") Long harvestId);
    
    @Query("SELECT SUM(s.quantity) FROM Sale s WHERE s.harvest.id = :harvestId")
    Double calculateTotalQuantitySoldByHarvestId(@Param("harvestId") Long harvestId);
}