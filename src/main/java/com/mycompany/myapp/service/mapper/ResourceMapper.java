package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ResourceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Resource} and its DTO {@link ResourceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResourceMapper extends EntityMapper<ResourceDTO, Resource> {

    @Mapping(source = "upper.id", target = "upperId")
    ResourceDTO toDto(Resource resource);

    @Mapping(source = "upperId", target = "upper")
    Resource toEntity(ResourceDTO resourceDTO);

    default Resource fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resource resource = new Resource();
        resource.setId(id);
        return resource;
    }
}
