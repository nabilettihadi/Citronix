package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.FarmRequest;
import ma.nabil.Citronix.dtos.responses.FarmResponse;
import ma.nabil.Citronix.entities.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {FieldMapper.class})
public interface FarmMapper {

    Farm toEntity(FarmRequest request);

    FarmResponse toResponse(Farm farm);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Farm farm, FarmRequest request);
}