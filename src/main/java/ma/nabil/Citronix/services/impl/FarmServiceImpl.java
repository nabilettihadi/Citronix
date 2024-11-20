package ma.nabil.Citronix.services.impl;

import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.FarmRequest;
import ma.nabil.Citronix.dtos.requests.FarmSearchCriteria;
import ma.nabil.Citronix.dtos.responses.FarmResponse;
import ma.nabil.Citronix.entities.Farm;
import ma.nabil.Citronix.exceptions.BusinessException;
import ma.nabil.Citronix.mappers.FarmMapper;
import ma.nabil.Citronix.repositories.FarmRepository;
import ma.nabil.Citronix.repositories.specs.FarmSpecs;
import ma.nabil.Citronix.services.FarmService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Transactional(readOnly = true)
    public FarmResponse getById(Long id) {
        return farmMapper.toResponse(getFarmById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarmResponse> getAll() {
        return farmRepository.findAll().stream()
                .map(farmMapper::toResponse)
                .toList();
    }


    private Farm getFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Ferme non trouvée avec l'id: " + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Farm farm = getFarmById(id);
        farmRepository.delete(farm);
    }


    private void validateFarmCreation(FarmRequest request) {
        if (farmRepository.existsByNameIgnoreCase(request.getName())) {
            throw new BusinessException("Une ferme avec ce nom existe déjà");
        }
        validateFarmRequest(request);
    }


    private void validateFarmUpdate(Farm farm, FarmRequest request) {
        if (!farm.getName().equalsIgnoreCase(request.getName())
                && farmRepository.existsByNameIgnoreCase(request.getName())) {
            throw new BusinessException("Une ferme avec ce nom existe déjà");
        }
        validateFarmRequest(request);
    }

    private void validateFarmRequest(FarmRequest request) {

        if (request.getArea() < 1000) {
            throw new BusinessException("La superficie minimale doit être de 0.1 hectare (1000 m²)");
        }


        if (request.getFields().size() > 10) {
            throw new BusinessException("Une ferme ne peut pas avoir plus de 10 champs");
        }


        double totalFieldsArea = request.getFields().stream()
                .mapToDouble(field -> field.getArea())
                .sum();

        if (totalFieldsArea >= request.getArea()) {
            throw new BusinessException("La somme des superficies des champs ne peut pas dépasser celle de la ferme");
        }


        request.getFields().forEach(field -> {
            if (field.getArea() > request.getArea() * 0.5) {
                throw new BusinessException("La superficie d'un champ ne peut pas dépasser 50% de celle de la ferme");
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarmResponse> search(FarmSearchCriteria criteria) {
        Specification<Farm> spec = Specification.<Farm>where(null)
                .and(FarmSpecs.nameLike(criteria.getName()))
                .and(FarmSpecs.locationLike(criteria.getLocation()))
                .and(FarmSpecs.areaBetween(criteria.getMinArea(), criteria.getMaxArea()))
                .and(FarmSpecs.creationDateBetween(criteria.getStartDate(), criteria.getEndDate()))
                .and(FarmSpecs.hasMinTrees(criteria.getMinTrees()))
                .and(FarmSpecs.hasMinProductivity(criteria.getMinProductivity()));

        return farmRepository.findAll(spec)
                .stream()
                .map(farmMapper::toResponse)
                .collect(Collectors.toList());
    }
}