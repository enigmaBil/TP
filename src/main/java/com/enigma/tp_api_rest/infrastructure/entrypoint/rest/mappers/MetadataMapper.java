package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.mappers;

import com.enigma.tp_api_rest.core.domain.Metadata;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.MetadataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MetadataMapper {
    MetadataMapper INSTANCE = Mappers.getMapper(MetadataMapper.class);

    Metadata toDomain(MetadataResponse response);
    MetadataResponse toResponse(Metadata domain);
}
