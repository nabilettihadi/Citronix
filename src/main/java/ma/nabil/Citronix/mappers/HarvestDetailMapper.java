package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.HarvestDetailRequest;
import ma.nabil.Citronix.dtos.responses.HarvestDetailResponse;
import ma.nabil.Citronix.entities.HarvestDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TreeMapper.class})
public interface HarvestDetailMapper extends GenericMapper<HarvestDetail, HarvestDetailRequest, HarvestDetailResponse> {
    @Override
    HarvestDetail toEntity(HarvestDetailRequest request);

    @Override
    @Mapping(target = "tree", source = "tree")
    HarvestDetailResponse toResponse(HarvestDetail harvestDetail);

    @Override
    void updateEntity(@MappingTarget HarvestDetail harvestDetail, HarvestDetailRequest request);
}