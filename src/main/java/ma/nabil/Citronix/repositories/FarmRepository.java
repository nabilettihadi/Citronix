package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.entities.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    Optional<Farm> findByNameIgnoreCase(String name);

    List<Farm> findByLocationContainingIgnoreCase(String location);

    List<Farm> findByAreaGreaterThanEqual(Double minArea);

    List<Farm> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT f FROM Farm f WHERE SIZE(f.fields) >= :minFields")
    List<Farm> findFarmsWithMinFields(@Param("minFields") int minFields);

    @Query("SELECT f FROM Farm f WHERE " +
            "(SELECT COUNT(t) FROM Field fi JOIN fi.trees t WHERE fi.farm = f) >= :minTrees")
    List<Farm> findFarmsWithMinTrees(@Param("minTrees") int minTrees);

    boolean existsByNameIgnoreCase(String name);
}