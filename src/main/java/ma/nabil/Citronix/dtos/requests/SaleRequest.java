package ma.nabil.Citronix.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest {
    @NotNull(message = "La date de vente est obligatoire")
    private LocalDate saleDate;

    @Positive(message = "Le prix unitaire doit être positif")
    private Double unitPrice;

    @NotBlank(message = "Le client est obligatoire")
    private String client;

    @Positive(message = "La quantité doit être positive")
    private Double quantity;

    private HarvestRequest harvest;
}