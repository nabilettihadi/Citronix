package ma.nabil.Citronix.services;

import ma.nabil.Citronix.dtos.requests.FieldRequest;
import ma.nabil.Citronix.dtos.responses.FieldResponse;

import java.util.List;

public interface FieldService {
    FieldResponse create(Long farmId, FieldRequest request);

    FieldResponse update(Long id, FieldRequest request);

    FieldResponse getById(Long id);

    List<FieldResponse> getByFarmId(Long farmId);

    void delete(Long id);

    void validateTreeDensity(Long fieldId, int numberOfTrees);

    void validateFieldArea(Long farmId, Double fieldArea);

    Double calculateAvailableArea(Long farmId);
}