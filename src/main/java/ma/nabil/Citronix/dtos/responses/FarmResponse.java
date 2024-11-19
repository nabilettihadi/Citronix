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
public class FarmResponse {
    private Long id;
    private String name;
    private String location;
    private Double area;
    private LocalDate creationDate;
    private List<FieldResponse> fields = new ArrayList<>();
}