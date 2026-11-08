package re.edu.medicinestore.controller;

import lombok.RequiredArgsConstructor;
import re.edu.medicinestore.dto.ApiResponse;
import re.edu.medicinestore.dto.MedicineResponse;
import re.edu.medicinestore.entity.Medicine;
import re.edu.medicinestore.service.MedicineService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    // GET ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<Medicine>>> getAllMedicines() {

        List<Medicine> medicines = medicineService.getAllMedicines();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Get all medicines successfully",
                        medicines
                )
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicineResponse>> getMedicineById(
            @PathVariable Long id) {

        MedicineResponse medicine = medicineService.getMedicineById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Get medicine successfully",
                        medicine
                )
        );
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<Medicine>> createMedicine(
            @RequestBody Medicine medicine) {

        Medicine created = medicineService.createMedicine(medicine);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Create medicine successfully",
                        created
                ));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Medicine>> updateMedicine(
            @PathVariable Long id,
            @RequestBody Medicine medicine) {

        Medicine updated = medicineService.updateMedicine(id, medicine);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Update medicine successfully",
                        updated
                )
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMedicine(
            @PathVariable Long id) {

        medicineService.deleteMedicine(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Delete medicine successfully",
                        null
                )
        );
    }
}