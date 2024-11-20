package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.entities.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Long> {
    List<HarvestDetail> findByHarvestId(Long harvestId);

    List<HarvestDetail> findByTreeId(Long treeId);

    @Query("SELECT SUM(hd.quantity) FROM HarvestDetail hd WHERE hd.tree.id = :treeId")
    Double calculateTotalProductionForTree(@Param("treeId") Long treeId);

    boolean existsByHarvestIdAndTreeId(Long harvestId, Long treeId);
}