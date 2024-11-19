package ma.nabil.Citronix.dtos.responses;

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
public class FieldResponse {
    private Long id;
    private String name;
    private Double area;
    private List<TreeResponse> trees = new ArrayList<>();
    private List<HarvestResponse> harvests = new ArrayList<>();
}