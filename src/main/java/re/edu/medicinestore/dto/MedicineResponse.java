package re.edu.medicinestore.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MedicineResponse(
        Long id,
        String name,
        String activeIngredient,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String manufacturer,
        LocalDate expiryDate,
        Long categoryId,
        String categoryName
) {
}