package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.UserRoleOrganizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserRoleOrganization} and its DTO {@link UserRoleOrganizationDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserxMapper.class, RoleMapper.class, OrganizationMapper.class})
public interface UserRoleOrganizationMapper extends EntityMapper<UserRoleOrganizationDTO, UserRoleOrganization> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.roleName", target = "roleRoleName")
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organization.orgName", target = "organizationOrgName")
    UserRoleOrganizationDTO toDto(UserRoleOrganization userRoleOrganization);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "roleId", target = "role")
    @Mapping(source = "organizationId", target = "organization")
    UserRoleOrganization toEntity(UserRoleOrganizationDTO userRoleOrganizationDTO);

    default UserRoleOrganization fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserRoleOrganization userRoleOrganization = new UserRoleOrganization();
        userRoleOrganization.setId(id);
        return userRoleOrganization;
    }
}
