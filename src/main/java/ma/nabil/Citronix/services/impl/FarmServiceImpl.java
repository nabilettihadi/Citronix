package ma.nabil.Citronix.services.impl;

import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.FarmRequest;
import ma.nabil.Citronix.dtos.responses.FarmResponse;
import ma.nabil.Citronix.entities.Farm;
import ma.nabil.Citronix.mappers.FarmMapper;
import ma.nabil.Citronix.repositories.FarmRepository;
import ma.nabil.Citronix.services.FarmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    @Override
    @Transactional
    public FarmResponse create(FarmRequest request) {
        validateFarmCreation(request);
        Farm farm = farmMapper.toEntity(request);
        farm = farmRepository.save(farm);
        return farmMapper.toResponse(farm);
    }

    @Override
    @Transactional
    public FarmResponse update(Long id, FarmRequest request) {
        Farm farm = getFarmById(id);
        validateFarmUpdate(farm, request);
        farmMapper.updateEntity(farm, request);
        farm = farmRepository.save(farm);
        return farmMapper.toResponse(farm);
    }

}