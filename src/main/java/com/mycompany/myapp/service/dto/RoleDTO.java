package com.mycompany.myapp.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Role} entity.
 */
@ApiModel(description = "角色 entity. @author zhenfuxu@gmail.com.")
public class RoleDTO implements Serializable {

    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String roleDescription;

    /**
     * 角色标记
     */
    @ApiModelProperty(value = "角色标记")
    private String roleFlag;

    /**
     * 角色生效时间
     */
    @ApiModelProperty(value = "角色生效时间")
    private ZonedDateTime roleEffDate;

    /**
     * 角色失效时间
     */
    @ApiModelProperty(value = "角色失效时间")
    private ZonedDateTime roleExpDate;

    /**
     * 角色对应多个资源。
     */
    @ApiModelProperty(value = "角色对应多个资源。")

    private Set<ResourceDTO> resources = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(String roleFlag) {
        this.roleFlag = roleFlag;
    }

    public ZonedDateTime getRoleEffDate() {
        return roleEffDate;
    }

    public void setRoleEffDate(ZonedDateTime roleEffDate) {
        this.roleEffDate = roleEffDate;
    }

    public ZonedDateTime getRoleExpDate() {
        return roleExpDate;
    }

    public void setRoleExpDate(ZonedDateTime roleExpDate) {
        this.roleExpDate = roleExpDate;
    }

    public Set<ResourceDTO> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceDTO> resources) {
        this.resources = resources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoleDTO roleDTO = (RoleDTO) o;
        if (roleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            ", roleDescription='" + getRoleDescription() + "'" +
            ", roleFlag='" + getRoleFlag() + "'" +
            ", roleEffDate='" + getRoleEffDate() + "'" +
            ", roleExpDate='" + getRoleExpDate() + "'" +
            "}";
    }
}
