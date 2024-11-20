package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.HarvestDetailRequest;
import ma.nabil.Citronix.dtos.responses.HarvestDetailResponse;
import ma.nabil.Citronix.entities.HarvestDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TreeMapper.class})
public interface HarvestDetailMapper {

    @Mapping(target = "harvest", ignore = true)
    HarvestDetail toEntity(HarvestDetailRequest request);

    @Mapping(target = "harvestId", source = "harvest.id")
    @Mapping(target = "treeId", source = "tree.id")
    HarvestDetailResponse toResponse(HarvestDetail harvestDetail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvest", ignore = true)
    void updateEntity(@MappingTarget HarvestDetail harvestDetail, HarvestDetailRequest request);
}