package ma.nabil.Citronix.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.HarvestDetailRequest;
import ma.nabil.Citronix.dtos.responses.HarvestDetailResponse;
import ma.nabil.Citronix.services.HarvestDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/harvest-details")
@RequiredArgsConstructor
@Tag(name = "Détails des récoltes", description = "API de gestion des détails des récoltes")
public class HarvestDetailController {
    private final HarvestDetailService harvestDetailService;

    @PostMapping("/harvests/{harvestId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un détail", description = "Ajoute un nouveau détail à une récolte")
    public HarvestDetailResponse create(
            @PathVariable Long harvestId,
            @Valid @RequestBody HarvestDetailRequest request) {
        return harvestDetailService.create(harvestId, request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un détail", description = "Récupère un détail de récolte par son ID")
    public HarvestDetailResponse getById(@PathVariable Long id) {
        return harvestDetailService.getById(id);
    }

    @GetMapping("/harvests/{harvestId}")
    @Operation(summary = "Lister par récolte", description = "Récupère tous les détails d'une récolte")
    public List<HarvestDetailResponse> getByHarvestId(@PathVariable Long harvestId) {
        return harvestDetailService.getByHarvestId(harvestId);
    }

    @GetMapping("/trees/{treeId}")
    @Operation(summary = "Lister par arbre", description = "Récupère tous les détails de récolte d'un arbre")
    public List<HarvestDetailResponse> getByTreeId(@PathVariable Long treeId) {
        return harvestDetailService.getByTreeId(treeId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un détail", description = "Met à jour la quantité d'un détail de récolte")
    public HarvestDetailResponse update(
            @PathVariable Long id,
            @Valid @RequestBody HarvestDetailRequest request) {
        return harvestDetailService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un détail", description = "Supprime un détail de récolte")
    public void delete(@PathVariable Long id) {
        harvestDetailService.delete(id);
    }

    @GetMapping("/trees/{treeId}/total-production")
    @Operation(summary = "Calculer la production totale", description = "Calcule la production totale d'un arbre")
    public Double getTotalProductionForTree(@PathVariable Long treeId) {
        return harvestDetailService.calculateTotalProductionForTree(treeId);
    }
}