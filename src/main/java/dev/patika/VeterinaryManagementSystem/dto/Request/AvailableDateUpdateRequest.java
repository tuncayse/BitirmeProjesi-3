package dev.patika.VeterinaryManagementSystem.dto.Request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateUpdateRequest {
    @Positive(message = "ID Değeri pozitif olmak zorunda")
    private long id;
    private LocalDate availableDate;
    private long doctorId;
}
