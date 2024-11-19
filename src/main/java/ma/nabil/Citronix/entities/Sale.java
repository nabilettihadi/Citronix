package ma.nabil.Citronix.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de vente est obligatoire")
    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Positive(message = "Le prix unitaire doit être positif")
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @NotBlank(message = "Le client est obligatoire")
    @Column(nullable = false)
    private String client;

    @Positive(message = "La quantité doit être positive")
    @Column(name = "quantity_kg", nullable = false)
    private Double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harvest_id", nullable = false)
    private Harvest harvest;

    @Formula("quantity_kg * unit_price")
    private Double revenue;

    @AssertTrue(message = "La quantité vendue ne peut pas dépasser la quantité récoltée")
    private boolean isQuantityValid() {
        if (harvest == null) return false;
        double totalSold = harvest.getSales().stream()
                .filter(sale -> !sale.getId().equals(this.id))
                .mapToDouble(Sale::getQuantity)
                .sum() + this.quantity;
        return totalSold <= harvest.getTotalQuantity();
    }
}