package ma.nabil.Citronix.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "harvest_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"harvest_id", "tree_id"})
})
public class HarvestDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "La quantité doit être positive")
    @Column(name = "quantity_kg", nullable = false)
    private Double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harvest_id", nullable = false)
    private Harvest harvest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_id", nullable = false)
    private Tree tree;

    @AssertTrue(message = "La quantité récoltée ne peut pas dépasser la productivité de l'arbre")
    private boolean isQuantityValid() {
        return tree != null && quantity <= tree.getProductivity();
    }
}