package ma.nabil.Citronix.services;

import ma.nabil.Citronix.dtos.requests.HarvestRequest;
import ma.nabil.Citronix.dtos.responses.HarvestResponse;
import ma.nabil.Citronix.enums.Season;

import java.util.List;

public interface HarvestService {
    HarvestResponse create(HarvestRequest request);

    HarvestResponse getById(Long id);

    List<HarvestResponse> getByFieldId(Long fieldId);

    List<HarvestResponse> getByFieldIdAndYear(Long fieldId, Integer year);

    List<HarvestResponse> getBySeason(Season season);

    HarvestResponse update(Long id, HarvestRequest request);

    void delete(Long id);

    Double getTotalQuantity(Long harvestId);
}