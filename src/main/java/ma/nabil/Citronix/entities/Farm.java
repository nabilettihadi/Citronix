package ma.nabil.Citronix.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import ma.nabil.Citronix.entities.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la ferme est obligatoire")
    private String name;

    @NotBlank(message = "La localisation est obligatoire")
    private String location;

    @Min(value = 1000, message = "La superficie minimale doit être de 0.1 hectare (1000 m²)")
    private double area;

    @NotNull(message = "La date de création est obligatoire")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    @Size(max = 10, message = "Une ferme ne peut pas avoir plus de 10 champs")
    private List<Field> fields = new ArrayList<>();
}