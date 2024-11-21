package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.FieldRequest;
import ma.nabil.Citronix.dtos.responses.FieldResponse;
import ma.nabil.Citronix.entities.Field;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TreeMapper.class, HarvestMapper.class})
public interface FieldMapper extends GenericMapper<Field, FieldRequest, FieldResponse> {
    @Override
    Field toEntity(FieldRequest request);

    @Override
    FieldResponse toResponse(Field field);

    @Override
    void updateEntity(@MappingTarget Field field, FieldRequest request);
}