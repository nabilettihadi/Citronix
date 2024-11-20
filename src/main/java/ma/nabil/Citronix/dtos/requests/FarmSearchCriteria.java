package ma.nabil.Citronix.dtos.requests;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FarmSearchCriteria {
    private String name;
    private String location;
    private Double minArea;
    private Double maxArea;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer minFields;
    private Integer maxFields;
    private Integer minTrees;
    private Double minProductivity;
}