package ma.nabil.Citronix.services.impl;

import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.HarvestDetailRequest;
import ma.nabil.Citronix.dtos.requests.HarvestRequest;
import ma.nabil.Citronix.dtos.responses.HarvestDetailResponse;
import ma.nabil.Citronix.dtos.responses.HarvestResponse;
import ma.nabil.Citronix.entities.Field;
import ma.nabil.Citronix.entities.Harvest;
import ma.nabil.Citronix.entities.HarvestDetail;
import ma.nabil.Citronix.entities.Tree;
import ma.nabil.Citronix.enums.Season;
import ma.nabil.Citronix.exceptions.BusinessException;
import ma.nabil.Citronix.mappers.HarvestDetailMapper;
import ma.nabil.Citronix.mappers.HarvestMapper;
import ma.nabil.Citronix.repositories.FieldRepository;
import ma.nabil.Citronix.repositories.HarvestDetailRepository;
import ma.nabil.Citronix.repositories.HarvestRepository;
import ma.nabil.Citronix.repositories.TreeRepository;
import ma.nabil.Citronix.services.HarvestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HarvestServiceImpl implements HarvestService {
    private final HarvestRepository harvestRepository;
    private final FieldRepository fieldRepository;
    private final TreeRepository treeRepository;
    private final HarvestMapper harvestMapper;
    private final HarvestDetailMapper harvestDetailMapper;
    private final HarvestDetailRepository harvestDetailRepository;

    @Override
    @Transactional
    public HarvestResponse create(HarvestRequest request) {
        validateHarvestCreation(request);

        Field field = getFieldById(request.getFieldId());
        final Harvest harvest = harvestMapper.toEntity(request);
        harvest.setField(field);
        harvest.setHarvestDetails(new ArrayList<>());

        if (request.getHarvestDetails() != null && !request.getHarvestDetails().isEmpty()) {
            for (HarvestDetailRequest detail : request.getHarvestDetails()) {
                Tree tree = getTreeById(detail.getTreeId());
                validateHarvestDetail(field, tree, detail, harvest.getSeason(), harvest.getYear());
                addHarvestDetail(harvest, detail);
            }
        }

        calculateTotalQuantity(harvest);
        return harvestMapper.toResponse(harvestRepository.save(harvest));
    }

    @Override
    @Transactional(readOnly = true)
    public HarvestResponse getById(Long id) {
        return harvestMapper.toResponse(getHarvestById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarvestResponse> getByFieldId(Long fieldId) {
        return harvestRepository.findByFieldId(fieldId).stream()
                .map(harvestMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarvestResponse> getByFieldIdAndYear(Long fieldId, Integer year) {
        return harvestRepository.findByFieldIdAndYear(fieldId, year).stream()
                .map(harvestMapper::toResponse)
                .toList();
    }

    @Override
    public List<HarvestResponse> getBySeason(Season season) {
        return harvestRepository.findBySeason(season).stream()
                .map(harvestMapper::toResponse)
                .toList();
    }

    @Override
    public HarvestResponse update(Long id, HarvestRequest request) {
        Harvest harvest = getHarvestById(id);
        validateHarvestUpdate(harvest, request);

        harvestMapper.updateEntity(harvest, request);
        calculateTotalQuantity(harvest);
        harvest = harvestRepository.save(harvest);
        return harvestMapper.toResponse(harvest);
    }

    @Override
    public void delete(Long id) {
        Harvest harvest = getHarvestById(id);
        if (!harvest.getSales().isEmpty()) {
            throw new BusinessException("Impossible de supprimer une récolte avec des ventes associées");
        }
        harvestRepository.delete(harvest);
    }

    @Override
    @Transactional
    public HarvestDetailResponse addDetail(Long harvestId, HarvestDetailRequest request) {
        Harvest harvest = getHarvestById(harvestId);
        Tree tree = getTreeById(request.getTreeId());

        validateHarvestDetail(harvest.getField(), tree, request, harvest.getSeason(), harvest.getYear());
        HarvestDetail detail = addHarvestDetail(harvest, request);

        calculateTotalQuantity(harvest);
        harvestRepository.save(harvest);

        return harvestDetailMapper.toResponse(detail);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalQuantity(Long harvestId) {
        Harvest harvest = getHarvestById(harvestId);
        return harvest.getTotalQuantity();
    }

    private void validateHarvestCreation(HarvestRequest request) {
        validateHarvestDate(request.getHarvestDate(), request.getSeason());

        if (harvestRepository.existsByFieldIdAndSeasonAndYear(
                request.getFieldId(),
                request.getSeason(),
                request.getHarvestDate().getYear())) {
            throw new BusinessException("Une récolte existe déjà pour ce champ cette saison");
        }
    }

    private void validateHarvestUpdate(Harvest harvest, HarvestRequest request) {
        validateHarvestDate(request.getHarvestDate(), request.getSeason());

        if (!harvest.getField().getId().equals(request.getFieldId())) {
            throw new BusinessException("Impossible de modifier le champ d'une récolte");
        }
    }

    private void validateHarvestDate(LocalDate harvestDate, Season season) {
        if (harvestDate == null) {
            throw new BusinessException("La date de récolte est obligatoire");
        }

        Season actualSeason = getSeason(harvestDate.getMonthValue());
        if (actualSeason != season) {
            throw new BusinessException("La saison ne correspond pas à la date de récolte");
        }
    }

    private Season getSeason(int month) {
        if (month >= 3 && month <= 5) return Season.SPRING;
        if (month >= 6 && month <= 8) return Season.SUMMER;
        if (month >= 9 && month <= 11) return Season.AUTUMN;
        return Season.WINTER;
    }

    private void validateHarvestDetail(Field field, Tree tree, HarvestDetailRequest detail,
                                       Season season, Integer year) {
        if (!tree.getField().getId().equals(field.getId())) {
            throw new BusinessException("L'arbre n'appartient pas au champ spécifié");
        }

        // Suppression de la vérification de la quantité par rapport à la productivité
        // if (detail.getQuantity() > tree.getProductivity()) {
        //     throw new BusinessException(
        //             String.format("La quantité récoltée (%f) dépasse la productivité de l'arbre (%f)",
        //                     detail.getQuantity(), tree.getProductivity())
        //     );
        // }

        if (harvestRepository.existsByTreeIdAndSeasonAndYear(tree.getId(), season, year)) {
            throw new BusinessException("Cet arbre a déjà été récolté cette saison");
        }
    }

    private void calculateTotalQuantity(Harvest harvest) {
        harvest.setTotalQuantity(
                harvest.getHarvestDetails().stream()
                        .mapToDouble(HarvestDetail::getQuantity)
                        .sum()
        );
    }

    private HarvestDetail addHarvestDetail(Harvest harvest, HarvestDetailRequest detail) {
        Tree tree = getTreeById(detail.getTreeId());

        HarvestDetail harvestDetail = HarvestDetail.builder()
                .tree(tree)
                .quantity(detail.getQuantity())
                .build();

        harvest.addDetail(harvestDetail);  // Utiliser la nouvelle méthode utilitaire
        return harvestDetailRepository.save(harvestDetail);  // Sauvegarder explicitement le détail
    }

    private Harvest getHarvestById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Récolte non trouvée avec l'id: " + id));
    }

    private Field getFieldById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Champ non trouvé avec l'id: " + id));
    }

    private Tree getTreeById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Arbre non trouvé avec l'id: " + id));
    }
}