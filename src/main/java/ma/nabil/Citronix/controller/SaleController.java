package ma.nabil.Citronix.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.SaleRequest;
import ma.nabil.Citronix.dtos.responses.SaleResponse;
import ma.nabil.Citronix.services.SaleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
@Tag(name = "Ventes", description = "API de gestion des ventes")
public class SaleController {
    private final SaleService saleService;

    @PostMapping("/harvests/{harvestId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une vente", description = "Enregistre une nouvelle vente pour une récolte")
    public SaleResponse create(
            @PathVariable Long harvestId,
            @Valid @RequestBody SaleRequest request) {
        return saleService.create(harvestId, request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une vente", description = "Récupère les détails d'une vente par son ID")
    public SaleResponse getById(@PathVariable Long id) {
        return saleService.getById(id);
    }

    @GetMapping("/harvests/{harvestId}")
    @Operation(summary = "Lister par récolte", description = "Récupère toutes les ventes d'une récolte")
    public List<SaleResponse> getByHarvestId(@PathVariable Long harvestId) {
        return saleService.getByHarvestId(harvestId);
    }

    @GetMapping("/date-range")
    @Operation(summary = "Lister par période", description = "Récupère les ventes sur une période donnée")
    public List<SaleResponse> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return saleService.getByDateRange(startDate, endDate);
    }

    @GetMapping("/clients/{client}")
    @Operation(summary = "Lister par client", description = "Récupère toutes les ventes d'un client")
    public List<SaleResponse> getByClient(@PathVariable String client) {
        return saleService.getByClient(client);
    }

    @GetMapping("/harvests/{harvestId}/revenue")
    @Operation(summary = "Calculer le revenu", description = "Calcule le revenu total d'une récolte")
    public Double calculateTotalRevenue(@PathVariable Long harvestId) {
        return saleService.calculateTotalRevenue(harvestId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une vente", description = "Met à jour les informations d'une vente")
    public SaleResponse update(
            @PathVariable Long id,
            @Valid @RequestBody SaleRequest request) {
        return saleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une vente", description = "Supprime une vente")
    public void delete(@PathVariable Long id) {
        saleService.delete(id);
    }
}