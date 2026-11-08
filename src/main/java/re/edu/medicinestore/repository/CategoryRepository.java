package re.edu.medicinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.edu.medicinestore.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}