package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.UserRoleOrganization} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.UserRoleOrganizationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-role-organizations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserRoleOrganizationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userName;

    private StringFilter roleName;

    private StringFilter orgName;

    private LongFilter userId;

    private LongFilter roleId;

    private LongFilter organizationId;

    public UserRoleOrganizationCriteria(){
    }

    public UserRoleOrganizationCriteria(UserRoleOrganizationCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.roleName = other.roleName == null ? null : other.roleName.copy();
        this.orgName = other.orgName == null ? null : other.orgName.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.roleId = other.roleId == null ? null : other.roleId.copy();
        this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
    }

    @Override
    public UserRoleOrganizationCriteria copy() {
        return new UserRoleOrganizationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public StringFilter getRoleName() {
        return roleName;
    }

    public void setRoleName(StringFilter roleName) {
        this.roleName = roleName;
    }

    public StringFilter getOrgName() {
        return orgName;
    }

    public void setOrgName(StringFilter orgName) {
        this.orgName = orgName;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getRoleId() {
        return roleId;
    }

    public void setRoleId(LongFilter roleId) {
        this.roleId = roleId;
    }

    public LongFilter getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(LongFilter organizationId) {
        this.organizationId = organizationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserRoleOrganizationCriteria that = (UserRoleOrganizationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(roleName, that.roleName) &&
            Objects.equals(orgName, that.orgName) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(roleId, that.roleId) &&
            Objects.equals(organizationId, that.organizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userName,
        roleName,
        orgName,
        userId,
        roleId,
        organizationId
        );
    }

    @Override
    public String toString() {
        return "UserRoleOrganizationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (roleName != null ? "roleName=" + roleName + ", " : "") +
                (orgName != null ? "orgName=" + orgName + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (roleId != null ? "roleId=" + roleId + ", " : "") +
                (organizationId != null ? "organizationId=" + organizationId + ", " : "") +
            "}";
    }

}
