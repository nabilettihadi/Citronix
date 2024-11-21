package ma.nabil.Citronix.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.TreeRequest;
import ma.nabil.Citronix.dtos.responses.TreeResponse;
import ma.nabil.Citronix.services.TreeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trees")
@RequiredArgsConstructor
@Tag(name = "Trees", description = "API de gestion des arbres")
public class TreeController {
    private final TreeService treeService;

    @PostMapping("/fields/{fieldId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un arbre", description = "Crée un nouvel arbre dans un champ spécifique")
    public TreeResponse create(
            @PathVariable Long fieldId,
            @Valid @RequestBody TreeRequest request) {
        return treeService.create(fieldId, request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un arbre", description = "Récupère les détails d'un arbre par son ID")
    public TreeResponse getById(@PathVariable Long id) {
        return treeService.getById(id);
    }

    @GetMapping("/fields/{fieldId}")
    @Operation(summary = "Lister les arbres d'un champ", description = "Récupère tous les arbres d'un champ spécifique")
    public List<TreeResponse> getByFieldId(@PathVariable Long fieldId) {
        return treeService.getByFieldId(fieldId);
    }

    @GetMapping("/{id}/productivity")
    @Operation(summary = "Calculer la productivité", description = "Calcule la productivité d'un arbre en fonction de son age")
    public Double getProductivity(@PathVariable Long id) {
        return treeService.calculateProductivity(id);
    }

    @GetMapping("/{id}/age")
    @Operation(summary = "Calculer l'âge", description = "Calcule l'âge actuel d'un arbre")
    public Integer getAge(@PathVariable Long id) {
        return treeService.calculateAge(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un arbre", description = "Supprime un arbre par son ID")
    public void delete(@PathVariable Long id) {
        treeService.delete(id);
    }
}