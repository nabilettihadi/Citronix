package ma.nabil.Citronix.services;

import ma.nabil.Citronix.dtos.requests.HarvestDetailRequest;
import ma.nabil.Citronix.dtos.responses.HarvestDetailResponse;

import java.util.List;

public interface HarvestDetailService {
    HarvestDetailResponse addDetail(Long harvestId, HarvestDetailRequest request);

    HarvestDetailResponse getById(Long id);

    List<HarvestDetailResponse> getByHarvestId(Long harvestId);

    List<HarvestDetailResponse> getByTreeId(Long treeId);

    HarvestDetailResponse update(Long id, HarvestDetailRequest request);

    void delete(Long id);

    Double calculateTotalProductionForTree(Long treeId);
}