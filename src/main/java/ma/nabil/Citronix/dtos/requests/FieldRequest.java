package ma.nabil.Citronix.dtos.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldRequest {
    @NotBlank(message = "Le nom du champ est obligatoire")
    private String name;

    @NotNull(message = "La superficie est obligatoire")
    @Min(value = 1000, message = "La superficie minimale doit être de 0.1 hectare (1000 m²)")
    private Double area;

    @Valid
    private List<TreeRequest> trees = new ArrayList<>();

    @Valid
    private List<HarvestRequest> harvests = new ArrayList<>();
}