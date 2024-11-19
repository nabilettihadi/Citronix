package ma.nabil.Citronix.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trees")
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de plantation est obligatoire")
    @Column(name = "planting_date", nullable = false)
    private LocalDate plantingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HarvestDetail> harvestDetails = new ArrayList<>();

    public int getAge() {
        return Period.between(plantingDate, LocalDate.now()).getYears();
    }

    public double getProductivity() {
        int age = getAge();
        if (age > 20) return 0.0;
        if (age > 10) return 20.0;
        if (age >= 3) return 12.0;
        return 2.5;
    }

    @AssertTrue(message = "Les arbres ne peuvent être plantés qu'entre mars et mai")
    private boolean isPlantingDateValid() {
        if (plantingDate == null) return false;
        int month = plantingDate.getMonthValue();
        return month >= 3 && month <= 5;
    }
}