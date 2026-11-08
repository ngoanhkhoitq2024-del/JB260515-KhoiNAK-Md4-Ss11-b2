package re.edu.medicinestore.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.edu.medicinestore.entity.Medicine;

import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    @EntityGraph(attributePaths = "category")
    Optional<Medicine> findWithCategoryById(Long id);
}