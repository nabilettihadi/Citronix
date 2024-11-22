package ma.nabil.Citronix.dtos.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.nabil.Citronix.enums.Season;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestRequest {
    @NotNull(message = "La date de récolte est obligatoire")
    private LocalDate harvestDate;

    @NotNull(message = "Le champ est obligatoire")
    private Long fieldId;

    @NotNull(message = "La saison est obligatoire")
    private Season season;

    @Valid
    private List<HarvestDetailRequest> harvestDetails;
}