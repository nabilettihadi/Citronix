package ma.nabil.Citronix.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.nabil.Citronix.enums.Season;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestResponse {
    private Long id;
    private LocalDate harvestDate;
    private Season season;
    private Integer year;
    private Double totalQuantity;
    private List<HarvestDetailResponse> harvestDetails = new ArrayList<>();
    private List<SaleResponse> sales = new ArrayList<>();
}