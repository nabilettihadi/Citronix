package ma.nabil.Citronix.mappers;

import org.mapstruct.MappingTarget;

public interface GenericMapper<E, R, S> {
    E toEntity(R request);

    S toResponse(E entity);

    void updateEntity(@MappingTarget E entity, R request);
}