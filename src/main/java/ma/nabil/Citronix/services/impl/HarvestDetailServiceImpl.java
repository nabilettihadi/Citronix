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
    public HarvestDetailResponse addDetail(Long harvestId, HarvestDetailRequest request) {
        Harvest harvest = getHarvestById(harvestId);
        Tree tree = getTreeById(request.getTreeId());

        validateHarvestDetail(harvest, tree, request);
        HarvestDetail detail = addHarvestDetail(harvest, request);

        calculateTotalQuantity(harvest);
        harvestRepository.save(harvest);

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
    public HarvestDetailResponse update(Long id, HarvestDetailRequest request) {
        HarvestDetail detail = getHarvestDetailById(id);
        Tree tree = getTreeById(request.getTreeId());

        if (!detail.getTree().getId().equals(tree.getId())) {
            throw new BusinessException("Impossible de modifier l'arbre d'un détail de récolte");
        }

        detail.setQuantity(request.getQuantity());
        detail = harvestDetailRepository.save(detail);

        Harvest harvest = detail.getHarvest();
        calculateTotalQuantity(harvest);
        harvestRepository.save(harvest);

        return harvestDetailMapper.toResponse(detail);
    }

    private void calculateTotalQuantity(Harvest harvest) {
        harvest.setTotalQuantity(
                harvest.getHarvestDetails().stream()
                        .mapToDouble(HarvestDetail::getQuantity)
                        .sum()
        );
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

    private HarvestDetail addHarvestDetail(Harvest harvest, HarvestDetailRequest request) {
        HarvestDetail detail = new HarvestDetail();
        detail.setHarvest(harvest);
        detail.setTree(getTreeById(request.getTreeId()));
        detail.setQuantity(request.getQuantity());
        return harvestDetailRepository.save(detail);
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