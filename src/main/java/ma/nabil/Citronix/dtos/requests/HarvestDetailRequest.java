package ma.nabil.Citronix.dtos.requests;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetailRequest {
    @Positive(message = "La quantité doit être positive")
    private Double quantity;

    private TreeRequest tree;
}