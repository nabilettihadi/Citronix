package ma.nabil.Citronix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.FarmRequest;
import ma.nabil.Citronix.dtos.requests.FarmSearchCriteria;
import ma.nabil.Citronix.dtos.responses.FarmResponse;
import ma.nabil.Citronix.services.FarmService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FarmResponse create(@Valid @RequestBody FarmRequest request) {
        return farmService.create(request);
    }

    @GetMapping("/{id}")
    public FarmResponse getById(@PathVariable Long id) {
        return farmService.getById(id);
    }

    @GetMapping("/all")
    public List<FarmResponse> getAll() {
        return farmService.getAll();
    }

    @GetMapping
    public List<FarmResponse> search(FarmSearchCriteria criteria) {
        return farmService.search(criteria);
    }

    @PutMapping("/{id}")
    public FarmResponse update(@PathVariable Long id, @Valid @RequestBody FarmRequest request) {
        return farmService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        farmService.delete(id);
    }
}