package ma.nabil.Citronix.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {
    private Long id;
    private LocalDate saleDate;
    private Double unitPrice;
    private String client;
    private Double quantity;
    private Double revenue;
}