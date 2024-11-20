package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.FieldRequest;
import ma.nabil.Citronix.dtos.responses.FieldResponse;
import ma.nabil.Citronix.entities.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TreeMapper.class, HarvestMapper.class})
public interface FieldMapper {
    
    @Mapping(target = "farm", ignore = true)
    Field toEntity(FieldRequest request);
    
    @Mapping(target = "farmId", source = "farm.id")
    FieldResponse toResponse(Field field);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "farm", ignore = true)
    void updateEntity(@MappingTarget Field field, FieldRequest request);
}