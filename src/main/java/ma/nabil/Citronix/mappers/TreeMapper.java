package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.TreeRequest;
import ma.nabil.Citronix.dtos.responses.TreeResponse;
import ma.nabil.Citronix.entities.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TreeMapper extends GenericMapper<Tree, TreeRequest, TreeResponse> {
    @Override
    Tree toEntity(TreeRequest request);

    @Override
    TreeResponse toResponse(Tree tree);

    @Override
    void updateEntity(@MappingTarget Tree tree, TreeRequest request);
}