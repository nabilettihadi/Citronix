package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.entities.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long>, FarmRepositoryCustom {
    boolean existsByNameIgnoreCase(String name);
}