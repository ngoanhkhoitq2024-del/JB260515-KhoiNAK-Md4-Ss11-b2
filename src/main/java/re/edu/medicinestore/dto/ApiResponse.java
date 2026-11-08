package re.edu.medicinestore.dto;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
}