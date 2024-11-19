package ma.nabil.Citronix.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farms")
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la ferme est obligatoire")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "La localisation est obligatoire")
    @Column(nullable = false)
    private String location;

    @Min(value = 1000, message = "La superficie minimale doit être de 0.1 hectare (1000 m²)")
    @Column(nullable = false)
    private Double area;

    @NotNull(message = "La date de création est obligatoire")
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Size(max = 10, message = "Une ferme ne peut pas avoir plus de 10 champs")
    private List<Field> fields = new ArrayList<>();
}