package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.HarvestRequest;
import ma.nabil.Citronix.dtos.responses.HarvestResponse;
import ma.nabil.Citronix.entities.Harvest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {HarvestDetailMapper.class, SaleMapper.class})
public interface HarvestMapper {

    @Mapping(target = "field", ignore = true)
    @Mapping(target = "season", ignore = true)
    @Mapping(target = "year", ignore = true)
    Harvest toEntity(HarvestRequest request);

    @Mapping(target = "fieldId", source = "field.id")
    HarvestResponse toResponse(Harvest harvest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "field", ignore = true)
    @Mapping(target = "season", ignore = true)
    @Mapping(target = "year", ignore = true)
    void updateEntity(@MappingTarget Harvest harvest, HarvestRequest request);
}