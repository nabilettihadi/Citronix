package ma.nabil.Citronix.services.impl;

import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.TreeRequest;
import ma.nabil.Citronix.dtos.responses.TreeResponse;
import ma.nabil.Citronix.entities.Field;
import ma.nabil.Citronix.entities.Tree;
import ma.nabil.Citronix.exceptions.BusinessException;
import ma.nabil.Citronix.mappers.TreeMapper;
import ma.nabil.Citronix.repositories.FieldRepository;
import ma.nabil.Citronix.repositories.TreeRepository;
import ma.nabil.Citronix.services.TreeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TreeServiceImpl implements TreeService {
    private final TreeRepository treeRepository;
    private final FieldRepository fieldRepository;
    private final TreeMapper treeMapper;

    @Override
    @Transactional
    public TreeResponse create(Long fieldId, TreeRequest request) {
        validateTreeCreation(fieldId, request);

        Field field = getFieldById(fieldId);
        Tree tree = treeMapper.toEntity(request);
        tree.setField(field);

        tree = treeRepository.save(tree);
        return treeMapper.toResponse(tree);
    }

    @Override
    @Transactional(readOnly = true)
    public TreeResponse getById(Long id) {
        return treeMapper.toResponse(getTreeById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TreeResponse> getByFieldId(Long fieldId) {
        return treeRepository.findByFieldId(fieldId).stream()
                .map(treeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateProductivity(Long id) {
        return getTreeById(id).getProductivity();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer calculateAge(Long id) {
        return getTreeById(id).getAge();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tree tree = getTreeById(id);
        treeRepository.delete(tree);
    }

    @Override
    public void validateTreeDensity(Long fieldId) {
        Field field = getFieldById(fieldId);
        long currentTrees = treeRepository.countByFieldId(fieldId);
        double maxTrees = field.getArea() / 100;

        if (currentTrees >= maxTrees) {
            throw new BusinessException(
                    String.format("Densité maximale atteinte: %.0f arbres autorisés pour %.0f m²",
                            maxTrees, field.getArea())
            );
        }
    }

    private Tree getTreeById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Arbre non trouvé avec l'id: " + id));
    }

    private Field getFieldById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Champ non trouvé avec l'id: " + id));
    }

    private void validateTreeCreation(Long fieldId, TreeRequest request) {
        validatePlantingDate(request.getPlantingDate());
        validateTreeDensity(fieldId);
    }

    private void validatePlantingDate(LocalDate plantingDate) {
        if (plantingDate == null) {
            throw new BusinessException("La date de plantation est obligatoire");
        }

        int month = plantingDate.getMonthValue();
        if (month < 3 || month > 5) {
            throw new BusinessException("Les arbres ne peuvent être plantés qu'entre mars et mai");
        }
    }
}