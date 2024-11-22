package ma.nabil.Citronix.services.impl;

import lombok.RequiredArgsConstructor;
import ma.nabil.Citronix.dtos.requests.SaleRequest;
import ma.nabil.Citronix.dtos.responses.SaleResponse;
import ma.nabil.Citronix.entities.Harvest;
import ma.nabil.Citronix.entities.Sale;
import ma.nabil.Citronix.exceptions.BusinessException;
import ma.nabil.Citronix.mappers.SaleMapper;
import ma.nabil.Citronix.repositories.HarvestRepository;
import ma.nabil.Citronix.repositories.SaleRepository;
import ma.nabil.Citronix.services.SaleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;
    private final SaleMapper saleMapper;

    @Override
    @Transactional
    public SaleResponse create(Long harvestId, SaleRequest request) {
        Harvest harvest = getHarvestById(harvestId);
        validateSale(harvest, request);

        Sale sale = saleMapper.toEntity(request);
        sale.setHarvest(harvest);

        sale = saleRepository.save(sale);
        return saleMapper.toResponse(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponse getById(Long id) {
        return saleMapper.toResponse(getSaleById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> getByHarvestId(Long harvestId) {
        return saleRepository.findByHarvestId(harvestId).stream()
                .map(saleMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> getByDateRange(LocalDate startDate, LocalDate endDate) {
        return saleRepository.findBySaleDateBetween(startDate, endDate).stream()
                .map(saleMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> getByClient(String client) {
        return saleRepository.findByClientContainingIgnoreCase(client).stream()
                .map(saleMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateTotalRevenue(Long harvestId) {
        return saleRepository.calculateTotalRevenueByHarvestId(harvestId);
    }

    @Override
@Transactional
public SaleResponse update(Long id, SaleRequest request) {
    Sale sale = getSaleById(id);
    
    double currentQuantity = sale.getQuantity();
    double newQuantity = request.getQuantity();
    double difference = newQuantity - currentQuantity;
    
    Double remainingQuantity = getRemainingQuantity(sale.getHarvest());
    if (difference > remainingQuantity) {
        throw new BusinessException(
            String.format("Quantité insuffisante. Disponible: %.2f kg, Demandée: %.2f kg",
                remainingQuantity, difference)
        );
    }
    
    sale.setSaleDate(request.getSaleDate());
    sale.setUnitPrice(request.getUnitPrice());
    sale.setClient(request.getClient());
    sale.setQuantity(request.getQuantity());
    
    sale = saleRepository.save(sale);
    return saleMapper.toResponse(sale);
}

    @Override
    @Transactional
    public void delete(Long id) {
        Sale sale = getSaleById(id);
        saleRepository.delete(sale);
    }

    private void validateSale(Harvest harvest, SaleRequest request) {
        Double remainingQuantity = getRemainingQuantity(harvest);
        if (request.getQuantity() > remainingQuantity) {
            throw new BusinessException(
                    String.format("Quantité insuffisante. Disponible: %.2f kg, Demandée: %.2f kg",
                            remainingQuantity, request.getQuantity())
            );
        }
    }

    private Double getRemainingQuantity(Harvest harvest) {
        Double totalSold = saleRepository.calculateTotalQuantitySoldByHarvestId(harvest.getId());
        return harvest.getTotalQuantity() - (totalSold != null ? totalSold : 0.0);
    }

    private Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Vente non trouvée avec l'id: " + id));
    }

    private Harvest getHarvestById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Récolte non trouvée avec l'id: " + id));
    }
}