package ma.nabil.Citronix.services.impl;

import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.FarmRequest;
import ma.nabil.Citronix.dtos.responses.FarmResponse;
import ma.nabil.Citronix.entities.Farm;
import ma.nabil.Citronix.exceptions.BusinessException;
import ma.nabil.Citronix.mappers.FarmMapper;
import ma.nabil.Citronix.repositories.FarmRepository;
import ma.nabil.Citronix.services.FarmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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

}