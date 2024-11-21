package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.HarvestRequest;
import ma.nabil.Citronix.dtos.responses.HarvestResponse;
import ma.nabil.Citronix.entities.Harvest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {HarvestDetailMapper.class, SaleMapper.class})
public interface HarvestMapper extends GenericMapper<Harvest, HarvestRequest, HarvestResponse> {
    @Override
    Harvest toEntity(HarvestRequest request);

    @Override
    HarvestResponse toResponse(Harvest harvest);

    @Override
    void updateEntity(@MappingTarget Harvest harvest, HarvestRequest request);
}