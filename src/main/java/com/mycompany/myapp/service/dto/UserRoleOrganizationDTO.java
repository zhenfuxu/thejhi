package com.mycompany.myapp.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.UserRoleOrganization} entity.
 */
public class UserRoleOrganizationDTO implements Serializable {

    private Long id;

    /**
     * 名字。
     */
    @ApiModelProperty(value = "名字。")
    private String userName;

    /**
     * 角色。
     */
    @ApiModelProperty(value = "角色。")
    private String roleName;

    /**
     * 机构。
     */
    @ApiModelProperty(value = "机构。")
    private String orgName;

    /**
     * 用户。
     */
    @ApiModelProperty(value = "用户。")

    private Long userId;

    private String userLogin;
    /**
     * 角色。
     */
    @ApiModelProperty(value = "角色。")

    private Long roleId;

    private String roleRoleName;
    /**
     * 机构。
     */
    @ApiModelProperty(value = "机构。")

    private Long organizationId;

    private String organizationOrgName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userxId) {
        this.userId = userxId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userxLogin) {
        this.userLogin = userxLogin;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleRoleName() {
        return roleRoleName;
    }

    public void setRoleRoleName(String roleRoleName) {
        this.roleRoleName = roleRoleName;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationOrgName() {
        return organizationOrgName;
    }

    public void setOrganizationOrgName(String organizationOrgName) {
        this.organizationOrgName = organizationOrgName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserRoleOrganizationDTO userRoleOrganizationDTO = (UserRoleOrganizationDTO) o;
        if (userRoleOrganizationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userRoleOrganizationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserRoleOrganizationDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", roleName='" + getRoleName() + "'" +
            ", orgName='" + getOrgName() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", role=" + getRoleId() +
            ", role='" + getRoleRoleName() + "'" +
            ", organization=" + getOrganizationId() +
            ", organization='" + getOrganizationOrgName() + "'" +
            "}";
    }
}
