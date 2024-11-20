package ma.nabil.Citronix.dtos.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestRequest {
    @NotNull(message = "La date de récolte est obligatoire")
    private LocalDate harvestDate;

    @Valid
    private List<HarvestDetailRequest> harvestDetails = new ArrayList<>();
}