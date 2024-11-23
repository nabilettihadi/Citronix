package ma.nabil.Citronix.services.impl;

import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.HarvestRequest;
import ma.nabil.Citronix.dtos.responses.HarvestResponse;
import ma.nabil.Citronix.entities.Field;
import ma.nabil.Citronix.entities.Harvest;
import ma.nabil.Citronix.enums.Season;
import ma.nabil.Citronix.exceptions.BusinessException;
import ma.nabil.Citronix.mappers.HarvestMapper;
import ma.nabil.Citronix.repositories.FieldRepository;
import ma.nabil.Citronix.repositories.HarvestRepository;
import ma.nabil.Citronix.services.HarvestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HarvestServiceImpl implements HarvestService {
    private final HarvestRepository harvestRepository;
    private final FieldRepository fieldRepository;
    private final HarvestMapper harvestMapper;

    @Override
    @Transactional
    public HarvestResponse create(HarvestRequest request) {
        validateHarvestCreation(request);
        Field field = getFieldById(request.getFieldId());

        Harvest harvest = Harvest.builder()
                .field(field)
                .harvestDate(request.getHarvestDate())
                .season(request.getSeason())
                .year(request.getHarvestDate().getYear())
                .totalQuantity(0.0)
                .build();

        harvest = harvestRepository.save(harvest);
        return harvestMapper.toResponse(harvest);
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
    @Transactional
    public HarvestResponse update(Long id, HarvestRequest request) {
        Harvest harvest = getHarvestById(id);
        validateHarvestUpdate(harvest, request);

        if (harvestRepository.existsByFieldIdAndSeasonAndYearAndIdNot(
                request.getFieldId(),
                request.getSeason(),
                request.getHarvestDate().getYear(),
                id)) {
            throw new BusinessException("Une récolte existe déjà pour ce champ cette saison");
        }

        harvest.setHarvestDate(request.getHarvestDate());
        harvest.setSeason(request.getSeason());
        harvest.setYear(request.getHarvestDate().getYear());

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

    private Harvest getHarvestById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Récolte non trouvée avec l'id: " + id));
    }

    private Field getFieldById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Champ non trouvé avec l'id: " + id));
    }

}