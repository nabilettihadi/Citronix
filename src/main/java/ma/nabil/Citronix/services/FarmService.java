package ma.nabil.Citronix.services;

import ma.nabil.Citronix.dtos.requests.FarmRequest;
import ma.nabil.Citronix.dtos.requests.FarmSearchCriteria;
import ma.nabil.Citronix.dtos.responses.FarmResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface FarmService {
    FarmResponse create(FarmRequest request);

    FarmResponse update(Long id, FarmRequest request);

    FarmResponse getById(Long id);

    Page<FarmResponse> getAll(Pageable pageable);

    void delete(Long id);

    List<FarmResponse> search(FarmSearchCriteria criteria);
}