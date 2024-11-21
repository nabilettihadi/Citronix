package ma.nabil.Citronix.services;

import ma.nabil.Citronix.dtos.requests.SaleRequest;
import ma.nabil.Citronix.dtos.responses.SaleResponse;

import java.time.LocalDate;
import java.util.List;

public interface SaleService {
    SaleResponse create(Long harvestId, SaleRequest request);
    
    SaleResponse getById(Long id);
    
    List<SaleResponse> getByHarvestId(Long harvestId);
    
    List<SaleResponse> getByDateRange(LocalDate startDate, LocalDate endDate);
    
    List<SaleResponse> getByClient(String client);
    
    Double calculateTotalRevenue(Long harvestId);
    
    void delete(Long id);
}