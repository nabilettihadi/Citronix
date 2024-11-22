package ma.nabil.Citronix.services.impl;

import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.FieldRequest;
import ma.nabil.Citronix.dtos.responses.FieldResponse;
import ma.nabil.Citronix.entities.Farm;
import ma.nabil.Citronix.entities.Field;
import ma.nabil.Citronix.exceptions.BusinessException;
import ma.nabil.Citronix.mappers.FieldMapper;
import ma.nabil.Citronix.repositories.FarmRepository;
import ma.nabil.Citronix.repositories.FieldRepository;
import ma.nabil.Citronix.services.FieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FieldMapper fieldMapper;

    @Override
    @Transactional
    public FieldResponse create(Long farmId, FieldRequest request) {
        validateFieldCreation(farmId, request);

        Farm farm = getFarmById(farmId);
        Field field = fieldMapper.toEntity(request);
        field.setFarm(farm);

        field = fieldRepository.save(field);
        return fieldMapper.toResponse(field);
    }

    @Override
    @Transactional
    public FieldResponse update(Long id, FieldRequest request) {
        Field field = getFieldById(id);
        validateFieldUpdate(field, request);

        fieldMapper.updateEntity(field, request);
        field = fieldRepository.save(field);
        return fieldMapper.toResponse(field);
    }

    @Override
    @Transactional(readOnly = true)
    public FieldResponse getById(Long id) {
        return fieldMapper.toResponse(getFieldById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldResponse> getByFarmId(Long farmId) {
        return fieldRepository.findByFarmId(farmId).stream()
                .map(fieldMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Field field = getFieldById(id);
        fieldRepository.delete(field);
    }

    @Override
    public void validateTreeDensity(Long fieldId, int numberOfTrees) {
        Field field = getFieldById(fieldId);
        double maxTrees = field.getArea() / 100;
        if (numberOfTrees > maxTrees) {
            throw new BusinessException("Densité maximale dépassée: " + maxTrees + " arbres autorisés pour " + field.getArea() + " m²");
        }
    }

    @Override
    public void validateFieldArea(Long farmId, Double fieldArea) {
        Farm farm = getFarmById(farmId);

        if (fieldArea < 1000) {
            throw new BusinessException("La superficie minimale doit être de 0.1 hectare (1000 m²)");
        }

        if (fieldArea > farm.getArea() * 0.5) {
            throw new BusinessException("La superficie du champ ne peut pas dépasser 50% de celle de la ferme");
        }

        Double totalArea = fieldRepository.calculateTotalAreaByFarmId(farmId);
        if (totalArea != null && (totalArea + fieldArea) > farm.getArea()) {
            throw new BusinessException("La somme des superficies des champs ne peut pas dépasser celle de la ferme");
        }
    }

    @Override
    public Double calculateAvailableArea(Long farmId) {
        Farm farm = getFarmById(farmId);
        Double usedArea = fieldRepository.calculateTotalAreaByFarmId(farmId);
        return farm.getArea() - (usedArea != null ? usedArea : 0.0);
    }

    private Field getFieldById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Champ non trouvé avec l'id: " + id));
    }

    private Farm getFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Ferme non trouvée avec l'id: " + id));
    }

    private void validateFieldCreation(Long farmId, FieldRequest request) {
        if (fieldRepository.existsByNameAndFarmId(request.getName(), farmId)) {
            throw new BusinessException("Un champ avec ce nom existe déjà dans cette ferme");
        }
        validateFieldArea(farmId, request.getArea());
    }

    private void validateFieldUpdate(Field field, FieldRequest request) {
        if (!field.getName().equals(request.getName()) &&
                fieldRepository.existsByNameAndFarmId(request.getName(), field.getFarm().getId())) {
            throw new BusinessException("Un champ avec ce nom existe déjà dans cette ferme");
        }
        validateFieldArea(field.getFarm().getId(), request.getArea());
    }
}