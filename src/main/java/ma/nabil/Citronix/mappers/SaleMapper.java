package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.SaleRequest;
import ma.nabil.Citronix.dtos.responses.SaleResponse;
import ma.nabil.Citronix.entities.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    
    @Mapping(target = "harvest", ignore = true)
    @Mapping(target = "revenue", ignore = true)
    Sale toEntity(SaleRequest request);
    
    @Mapping(target = "harvestId", source = "harvest.id")
    SaleResponse toResponse(Sale sale);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvest", ignore = true)
    @Mapping(target = "revenue", ignore = true)
    void updateEntity(@MappingTarget Sale sale, SaleRequest request);
}