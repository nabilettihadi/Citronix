package ma.nabil.Citronix.dtos.requests;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
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
    private LocalDate plantingDate;

    @AssertTrue(message = "Les arbres ne peuvent être plantés qu'entre mars et mai")
    private boolean isPlantingDateValid() {
        if (plantingDate == null) return false;
        int month = plantingDate.getMonthValue();
        return month >= 3 && month <= 5;
    }
}