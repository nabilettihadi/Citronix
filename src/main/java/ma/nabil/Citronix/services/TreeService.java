package ma.nabil.Citronix.services;

import ma.nabil.Citronix.dtos.requests.TreeRequest;
import ma.nabil.Citronix.dtos.responses.TreeResponse;

import java.util.List;

public interface TreeService {
    TreeResponse create(Long fieldId, TreeRequest request);
    
    TreeResponse getById(Long id);
    
    List<TreeResponse> getByFieldId(Long fieldId);
    
    Double calculateProductivity(Long id);
    
    Integer calculateAge(Long id);
    
    void delete(Long id);
    
    void validateTreeDensity(Long fieldId);
}