package ma.nabil.Citronix.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetailRequest {
    @NotNull(message = "L'arbre est obligatoire")
    private Long treeId;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 0, message = "La quantité doit être positive")
    private Double quantity;
}