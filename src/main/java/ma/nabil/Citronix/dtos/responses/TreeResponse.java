package ma.nabil.Citronix.dtos.responses;

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
public class TreeResponse {
    private Long id;
    private LocalDate plantingDate;
    private int age;
    private double productivity;
    private List<HarvestDetailResponse> harvestDetails = new ArrayList<>();
}