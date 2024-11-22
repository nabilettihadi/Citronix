package ma.nabil.Citronix.dtos.responses;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TreeBasicResponse {
    private Long id;
    private LocalDate plantingDate;
    private int age;
    private double productivity;
}