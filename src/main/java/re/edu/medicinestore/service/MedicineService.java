package re.edu.medicinestore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import re.edu.medicinestore.dto.MedicineResponse;
import re.edu.medicinestore.entity.Category;
import re.edu.medicinestore.entity.Medicine;
import re.edu.medicinestore.exception.ResourceNotFoundException;
import re.edu.medicinestore.repository.CategoryRepository;
import re.edu.medicinestore.repository.MedicineRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final CategoryRepository categoryRepository;

    // Lấy tất cả thuốc
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    // Lấy thuốc theo ID
    @Cacheable(value = "medicines", key = "#id")
    public MedicineResponse getMedicineById(Long id) {

        System.out.println(">>> QUERY DATABASE");

        Medicine medicine = medicineRepository.findWithCategoryById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Medicine not found with ID: " + id
                        )
                );

        return new MedicineResponse(
                medicine.getId(),
                medicine.getName(),
                medicine.getActiveIngredient(),
                medicine.getDescription(),
                medicine.getPrice(),
                medicine.getStockQuantity(),
                medicine.getManufacturer(),
                medicine.getExpiryDate(),
                medicine.getCategory().getId(),
                medicine.getCategory().getName()
        );
    }

    // Thêm thuốc
    @CacheEvict(value = "medicines", allEntries = true)
    public Medicine createMedicine(Medicine medicine) {

        Category category = categoryRepository.findById(
                medicine.getCategory().getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Category not found")
        );

        medicine.setCategory(category);

        return medicineRepository.save(medicine);
    }

    // Cập nhật thuốc
    @CacheEvict(value = "medicines", key = "#id")
    public Medicine updateMedicine(Long id, Medicine medicine) {

        Medicine existing = medicineRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Medicine not found with ID: " + id
                        )
                );

        Category category = categoryRepository.findById(
                medicine.getCategory().getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Category not found")
        );

        existing.setName(medicine.getName());
        existing.setActiveIngredient(medicine.getActiveIngredient());
        existing.setDescription(medicine.getDescription());
        existing.setPrice(medicine.getPrice());
        existing.setStockQuantity(medicine.getStockQuantity());
        existing.setManufacturer(medicine.getManufacturer());
        existing.setExpiryDate(medicine.getExpiryDate());
        existing.setCategory(category);

        return medicineRepository.save(existing);
    }

    // Xóa thuốc
    @CacheEvict(value = "medicines", key = "#id")
    public void deleteMedicine(Long id) {

        if (!medicineRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Medicine not found with ID: " + id
            );
        }

        medicineRepository.deleteById(id);
    }
}