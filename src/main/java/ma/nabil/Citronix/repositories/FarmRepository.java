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
public interface FarmRepository extends JpaRepository<Farm, Long>, JpaSpecificationExecutor<Farm> {
    boolean existsByNameIgnoreCase(String name);
}