package ma.nabil.Citronix.services.impl;

import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.HarvestDetailRequest;
import ma.nabil.Citronix.dtos.responses.HarvestDetailResponse;
import ma.nabil.Citronix.entities.Harvest;
import ma.nabil.Citronix.entities.HarvestDetail;
import ma.nabil.Citronix.entities.Tree;
import ma.nabil.Citronix.exceptions.BusinessException;
import ma.nabil.Citronix.mappers.HarvestDetailMapper;
import ma.nabil.Citronix.repositories.HarvestDetailRepository;
import ma.nabil.Citronix.repositories.HarvestRepository;
import ma.nabil.Citronix.repositories.TreeRepository;
import ma.nabil.Citronix.services.HarvestDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HarvestDetailServiceImpl implements HarvestDetailService {
    private final HarvestDetailRepository harvestDetailRepository;
    private final HarvestRepository harvestRepository;
    private final TreeRepository treeRepository;
    private final HarvestDetailMapper harvestDetailMapper;

    @Override
    @Transactional
    public HarvestDetailResponse create(Long harvestId, HarvestDetailRequest request) {
        Harvest harvest = getHarvestById(harvestId);
        Tree tree = getTreeById(request.getTreeId());

        validateHarvestDetail(harvest, tree, request);

        HarvestDetail detail = HarvestDetail.builder()
                .harvest(harvest)
                .tree(tree)
                .quantity(request.getQuantity())
                .build();

        detail = harvestDetailRepository.save(detail);
        return harvestDetailMapper.toResponse(detail);
    }

    @Override
    @Transactional(readOnly = true)
    public HarvestDetailResponse getById(Long id) {
        return harvestDetailMapper.toResponse(getHarvestDetailById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarvestDetailResponse> getByHarvestId(Long harvestId) {
        return harvestDetailRepository.findByHarvestId(harvestId).stream()
                .map(harvestDetailMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarvestDetailResponse> getByTreeId(Long treeId) {
        return harvestDetailRepository.findByTreeId(treeId).stream()
                .map(harvestDetailMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        HarvestDetail detail = getHarvestDetailById(id);
        harvestDetailRepository.delete(detail);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateTotalProductionForTree(Long treeId) {
        return harvestDetailRepository.calculateTotalProductionForTree(treeId);
    }

    private void validateHarvestDetail(Harvest harvest, Tree tree, HarvestDetailRequest request) {
        if (!tree.getField().getId().equals(harvest.getField().getId())) {
            throw new BusinessException("L'arbre n'appartient pas au champ spécifié");
        }

        if (harvestDetailRepository.existsByHarvestIdAndTreeId(harvest.getId(), tree.getId())) {
            throw new BusinessException("Cet arbre a déjà été récolté pour cette récolte");
        }
    }

    private HarvestDetail getHarvestDetailById(Long id) {
        return harvestDetailRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Détail de récolte non trouvé avec l'id: " + id));
    }

    private Harvest getHarvestById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Récolte non trouvée avec l'id: " + id));
    }

    private Tree getTreeById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Arbre non trouvé avec l'id: " + id));
    }
}