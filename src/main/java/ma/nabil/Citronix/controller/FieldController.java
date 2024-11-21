package ma.nabil.Citronix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.FieldRequest;
import ma.nabil.Citronix.dtos.responses.FieldResponse;
import ma.nabil.Citronix.services.FieldService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @PostMapping("/farms/{farmId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FieldResponse create(@PathVariable Long farmId, @Valid @RequestBody FieldRequest request) {
        return fieldService.create(farmId, request);
    }

    @GetMapping("/{id}")
    public FieldResponse getById(@PathVariable Long id) {
        return fieldService.getById(id);
    }

    @GetMapping("/farms/{farmId}")
    public List<FieldResponse> getByFarmId(@PathVariable Long farmId) {
        return fieldService.getByFarmId(farmId);
    }

    @PutMapping("/{id}")
    public FieldResponse update(@PathVariable Long id, @Valid @RequestBody FieldRequest request) {
        return fieldService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        fieldService.delete(id);
    }

    @GetMapping("/farms/{farmId}/available-area")
    public Double getAvailableArea(@PathVariable Long farmId) {
        return fieldService.calculateAvailableArea(farmId);
    }
}