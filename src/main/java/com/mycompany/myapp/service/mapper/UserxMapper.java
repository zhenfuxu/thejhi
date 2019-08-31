package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.UserxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Userx} and its DTO {@link UserxDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganizationMapper.class, RoleMapper.class})
public interface UserxMapper extends EntityMapper<UserxDTO, Userx> {

    @Mapping(source = "organization.id", target = "organizationId")
    UserxDTO toDto(Userx userx);

    @Mapping(source = "organizationId", target = "organization")
    @Mapping(target = "removeRoles", ignore = true)
    Userx toEntity(UserxDTO userxDTO);

    default Userx fromId(Long id) {
        if (id == null) {
            return null;
        }
        Userx userx = new Userx();
        userx.setId(id);
        return userx;
    }
}
