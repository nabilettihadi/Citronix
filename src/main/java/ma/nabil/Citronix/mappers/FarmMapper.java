package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.FarmRequest;
import ma.nabil.Citronix.dtos.responses.FarmResponse;
import ma.nabil.Citronix.entities.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {FieldMapper.class})
public interface FarmMapper extends GenericMapper<Farm, FarmRequest, FarmResponse> {
    @Override
    Farm toEntity(FarmRequest request);

    @Override
    FarmResponse toResponse(Farm farm);

    @Override
    void updateEntity(@MappingTarget Farm farm, FarmRequest request);
}