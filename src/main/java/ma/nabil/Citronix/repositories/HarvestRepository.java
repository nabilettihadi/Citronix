package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.entities.Harvest;
import ma.nabil.Citronix.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    List<Harvest> findByFieldId(Long fieldId);

    List<Harvest> findByFieldIdAndYear(Long fieldId, Integer year);

    List<Harvest> findBySeason(Season season);

    boolean existsByFieldIdAndSeasonAndYear(Long fieldId, Season season, Integer year);

    boolean existsByFieldIdAndSeasonAndYearAndIdNot(
            Long fieldId,
            Season season,
            Integer year,
            Long id
    );
}