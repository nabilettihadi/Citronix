package ma.nabil.Citronix.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.HarvestRequest;
import ma.nabil.Citronix.dtos.responses.HarvestResponse;
import ma.nabil.Citronix.enums.Season;
import ma.nabil.Citronix.services.HarvestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/harvests")
@RequiredArgsConstructor
@Tag(name = "Récoltes", description = "API de gestion des récoltes")
public class HarvestController {
    private final HarvestService harvestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une récolte", description = "Crée une nouvelle récolte avec ses détails")
    public HarvestResponse create(@Valid @RequestBody HarvestRequest request) {
        return harvestService.create(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une récolte", description = "Récupère les détails d'une récolte par son ID")
    public HarvestResponse getById(@PathVariable Long id) {
        return harvestService.getById(id);
    }

    @GetMapping("/fields/{fieldId}")
    @Operation(summary = "Lister les récoltes d'un champ", description = "Récupère toutes les récoltes d'un champ spécifique")
    public List<HarvestResponse> getByFieldId(@PathVariable Long fieldId) {
        return harvestService.getByFieldId(fieldId);
    }

    @GetMapping("/fields/{fieldId}/years/{year}")
    @Operation(summary = "Lister les récoltes par année", description = "Récupère les récoltes d'un champ pour une année spécifique")
    public List<HarvestResponse> getByFieldIdAndYear(@PathVariable Long fieldId, @PathVariable Integer year) {
        return harvestService.getByFieldIdAndYear(fieldId, year);
    }

    @GetMapping("/seasons/{season}")
    @Operation(summary = "Lister par saison", description = "Récupère toutes les récoltes d'une saison spécifique")
    public List<HarvestResponse> getBySeason(@PathVariable Season season) {
        return harvestService.getBySeason(season);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une récolte", description = "Met à jour les informations d'une récolte existante")
    public HarvestResponse update(@PathVariable Long id, @Valid @RequestBody HarvestRequest request) {
        return harvestService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une récolte", description = "Supprime une récolte et tous ses détails associés")
    public void delete(@PathVariable Long id) {
        harvestService.delete(id);
    }

    @GetMapping("/{id}/quantity")
    @Operation(summary = "Calculer le total", description = "Calcule la quantité totale récoltée")
    public Double getTotalQuantity(@PathVariable Long id) {
        return harvestService.getTotalQuantity(id);
    }
}