package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.entities.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {
    List<Tree> findByFieldId(Long fieldId);
    long countByFieldId(Long fieldId);
}