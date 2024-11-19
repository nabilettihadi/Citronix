package ma.nabil.Citronix.dtos.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class FarmRequest {
    @NotBlank(message = "Le nom de la ferme est obligatoire")
    private String name;

    @NotBlank(message = "La localisation est obligatoire")
    private String location;

    @NotNull(message = "La superficie est obligatoire")
    @Min(value = 1000, message = "La superficie minimale doit être de 0.1 hectare (1000 m²)")
    private Double area;

    @NotNull(message = "La date de création est obligatoire")
    private LocalDate creationDate;

    @Valid
    @Size(max = 10, message = "Une ferme ne peut pas avoir plus de 10 champs")
    private List<FieldRequest> fields = new ArrayList<>();
}