package ma.nabil.Citronix.dtos.requests;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeRequest {
    @NotNull(message = "La date de plantation est obligatoire")
    @PastOrPresent(message = "La date de plantation ne peut pas être dans le futur")
    private LocalDate plantingDate;

    private Long fieldId;

    @AssertTrue(message = "Les arbres ne peuvent être plantés qu'entre mars et mai")
    private boolean isPlantingDateValid() {
        if (plantingDate == null) return false;
        int month = plantingDate.getMonthValue();
        return month >= 3 && month <= 5;
    }
}