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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Role} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.RoleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter roleName;

    private StringFilter roleDescription;

    private StringFilter roleFlag;

    private ZonedDateTimeFilter roleEffDate;

    private ZonedDateTimeFilter roleExpDate;

    private LongFilter resourcesId;

    public RoleCriteria(){
    }

    public RoleCriteria(RoleCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.roleName = other.roleName == null ? null : other.roleName.copy();
        this.roleDescription = other.roleDescription == null ? null : other.roleDescription.copy();
        this.roleFlag = other.roleFlag == null ? null : other.roleFlag.copy();
        this.roleEffDate = other.roleEffDate == null ? null : other.roleEffDate.copy();
        this.roleExpDate = other.roleExpDate == null ? null : other.roleExpDate.copy();
        this.resourcesId = other.resourcesId == null ? null : other.resourcesId.copy();
    }

    @Override
    public RoleCriteria copy() {
        return new RoleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRoleName() {
        return roleName;
    }

    public void setRoleName(StringFilter roleName) {
        this.roleName = roleName;
    }

    public StringFilter getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(StringFilter roleDescription) {
        this.roleDescription = roleDescription;
    }

    public StringFilter getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(StringFilter roleFlag) {
        this.roleFlag = roleFlag;
    }

    public ZonedDateTimeFilter getRoleEffDate() {
        return roleEffDate;
    }

    public void setRoleEffDate(ZonedDateTimeFilter roleEffDate) {
        this.roleEffDate = roleEffDate;
    }

    public ZonedDateTimeFilter getRoleExpDate() {
        return roleExpDate;
    }

    public void setRoleExpDate(ZonedDateTimeFilter roleExpDate) {
        this.roleExpDate = roleExpDate;
    }

    public LongFilter getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(LongFilter resourcesId) {
        this.resourcesId = resourcesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RoleCriteria that = (RoleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(roleName, that.roleName) &&
            Objects.equals(roleDescription, that.roleDescription) &&
            Objects.equals(roleFlag, that.roleFlag) &&
            Objects.equals(roleEffDate, that.roleEffDate) &&
            Objects.equals(roleExpDate, that.roleExpDate) &&
            Objects.equals(resourcesId, that.resourcesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        roleName,
        roleDescription,
        roleFlag,
        roleEffDate,
        roleExpDate,
        resourcesId
        );
    }

    @Override
    public String toString() {
        return "RoleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (roleName != null ? "roleName=" + roleName + ", " : "") +
                (roleDescription != null ? "roleDescription=" + roleDescription + ", " : "") +
                (roleFlag != null ? "roleFlag=" + roleFlag + ", " : "") +
                (roleEffDate != null ? "roleEffDate=" + roleEffDate + ", " : "") +
                (roleExpDate != null ? "roleExpDate=" + roleExpDate + ", " : "") +
                (resourcesId != null ? "resourcesId=" + resourcesId + ", " : "") +
            "}";
    }

}
