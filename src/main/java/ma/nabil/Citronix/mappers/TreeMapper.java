package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.TreeRequest;
import ma.nabil.Citronix.dtos.responses.TreeResponse;
import ma.nabil.Citronix.entities.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    
    @Mapping(target = "field", ignore = true)
    @Mapping(target = "harvestDetails", ignore = true)
    Tree toEntity(TreeRequest request);
    
    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "age", expression = "java(tree.getAge())")
    @Mapping(target = "productivity", expression = "java(tree.getProductivity())")
    TreeResponse toResponse(Tree tree);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "field", ignore = true)
    @Mapping(target = "harvestDetails", ignore = true)
    void updateEntity(@MappingTarget Tree tree, TreeRequest request);
}