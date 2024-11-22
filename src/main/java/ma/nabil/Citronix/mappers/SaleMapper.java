package ma.nabil.Citronix.mappers;

import ma.nabil.Citronix.dtos.requests.SaleRequest;
import ma.nabil.Citronix.dtos.responses.SaleResponse;
import ma.nabil.Citronix.entities.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SaleMapper extends GenericMapper<Sale, SaleRequest, SaleResponse> {
    @Override
    Sale toEntity(SaleRequest request);

    @Override
    @Mapping(target = "revenue", expression = "java(sale.getQuantity() * sale.getUnitPrice())")
    SaleResponse toResponse(Sale sale);

    @Override
    void updateEntity(@MappingTarget Sale sale, SaleRequest request);
}