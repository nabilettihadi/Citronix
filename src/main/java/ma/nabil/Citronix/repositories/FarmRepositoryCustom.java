package ma.nabil.Citronix.repositories;

import ma.nabil.Citronix.dtos.requests.FarmSearchCriteria;
import ma.nabil.Citronix.entities.Farm;

import java.util.List;

public interface FarmRepositoryCustom {
    List<Farm> searchFarms(FarmSearchCriteria criteria);
}