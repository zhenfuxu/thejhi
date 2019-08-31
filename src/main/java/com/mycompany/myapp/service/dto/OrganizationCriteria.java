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
 * Criteria class for the {@link com.mycompany.myapp.domain.Organization} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.OrganizationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /organizations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrganizationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter orgName;

    private StringFilter orgCode;

    private StringFilter orgFlag;

    private StringFilter orgAreaCode;

    private StringFilter orgAreaName;

    private StringFilter orgDescription;

    private LongFilter orgLft;

    private LongFilter orgRgt;

    private LongFilter orgLevel;

    private LongFilter orgOrder;

    private BooleanFilter leaf;

    private LongFilter upperId;

    public OrganizationCriteria(){
    }

    public OrganizationCriteria(OrganizationCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.orgName = other.orgName == null ? null : other.orgName.copy();
        this.orgCode = other.orgCode == null ? null : other.orgCode.copy();
        this.orgFlag = other.orgFlag == null ? null : other.orgFlag.copy();
        this.orgAreaCode = other.orgAreaCode == null ? null : other.orgAreaCode.copy();
        this.orgAreaName = other.orgAreaName == null ? null : other.orgAreaName.copy();
        this.orgDescription = other.orgDescription == null ? null : other.orgDescription.copy();
        this.orgLft = other.orgLft == null ? null : other.orgLft.copy();
        this.orgRgt = other.orgRgt == null ? null : other.orgRgt.copy();
        this.orgLevel = other.orgLevel == null ? null : other.orgLevel.copy();
        this.orgOrder = other.orgOrder == null ? null : other.orgOrder.copy();
        this.leaf = other.leaf == null ? null : other.leaf.copy();
        this.upperId = other.upperId == null ? null : other.upperId.copy();
    }

    @Override
    public OrganizationCriteria copy() {
        return new OrganizationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOrgName() {
        return orgName;
    }

    public void setOrgName(StringFilter orgName) {
        this.orgName = orgName;
    }

    public StringFilter getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(StringFilter orgCode) {
        this.orgCode = orgCode;
    }

    public StringFilter getOrgFlag() {
        return orgFlag;
    }

    public void setOrgFlag(StringFilter orgFlag) {
        this.orgFlag = orgFlag;
    }

    public StringFilter getOrgAreaCode() {
        return orgAreaCode;
    }

    public void setOrgAreaCode(StringFilter orgAreaCode) {
        this.orgAreaCode = orgAreaCode;
    }

    public StringFilter getOrgAreaName() {
        return orgAreaName;
    }

    public void setOrgAreaName(StringFilter orgAreaName) {
        this.orgAreaName = orgAreaName;
    }

    public StringFilter getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(StringFilter orgDescription) {
        this.orgDescription = orgDescription;
    }

    public LongFilter getOrgLft() {
        return orgLft;
    }

    public void setOrgLft(LongFilter orgLft) {
        this.orgLft = orgLft;
    }

    public LongFilter getOrgRgt() {
        return orgRgt;
    }

    public void setOrgRgt(LongFilter orgRgt) {
        this.orgRgt = orgRgt;
    }

    public LongFilter getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(LongFilter orgLevel) {
        this.orgLevel = orgLevel;
    }

    public LongFilter getOrgOrder() {
        return orgOrder;
    }

    public void setOrgOrder(LongFilter orgOrder) {
        this.orgOrder = orgOrder;
    }

    public BooleanFilter getLeaf() {
        return leaf;
    }

    public void setLeaf(BooleanFilter leaf) {
        this.leaf = leaf;
    }

    public LongFilter getUpperId() {
        return upperId;
    }

    public void setUpperId(LongFilter upperId) {
        this.upperId = upperId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrganizationCriteria that = (OrganizationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(orgName, that.orgName) &&
            Objects.equals(orgCode, that.orgCode) &&
            Objects.equals(orgFlag, that.orgFlag) &&
            Objects.equals(orgAreaCode, that.orgAreaCode) &&
            Objects.equals(orgAreaName, that.orgAreaName) &&
            Objects.equals(orgDescription, that.orgDescription) &&
            Objects.equals(orgLft, that.orgLft) &&
            Objects.equals(orgRgt, that.orgRgt) &&
            Objects.equals(orgLevel, that.orgLevel) &&
            Objects.equals(orgOrder, that.orgOrder) &&
            Objects.equals(leaf, that.leaf) &&
            Objects.equals(upperId, that.upperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orgName,
        orgCode,
        orgFlag,
        orgAreaCode,
        orgAreaName,
        orgDescription,
        orgLft,
        orgRgt,
        orgLevel,
        orgOrder,
        leaf,
        upperId
        );
    }

    @Override
    public String toString() {
        return "OrganizationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orgName != null ? "orgName=" + orgName + ", " : "") +
                (orgCode != null ? "orgCode=" + orgCode + ", " : "") +
                (orgFlag != null ? "orgFlag=" + orgFlag + ", " : "") +
                (orgAreaCode != null ? "orgAreaCode=" + orgAreaCode + ", " : "") +
                (orgAreaName != null ? "orgAreaName=" + orgAreaName + ", " : "") +
                (orgDescription != null ? "orgDescription=" + orgDescription + ", " : "") +
                (orgLft != null ? "orgLft=" + orgLft + ", " : "") +
                (orgRgt != null ? "orgRgt=" + orgRgt + ", " : "") +
                (orgLevel != null ? "orgLevel=" + orgLevel + ", " : "") +
                (orgOrder != null ? "orgOrder=" + orgOrder + ", " : "") +
                (leaf != null ? "leaf=" + leaf + ", " : "") +
                (upperId != null ? "upperId=" + upperId + ", " : "") +
            "}";
    }

}
