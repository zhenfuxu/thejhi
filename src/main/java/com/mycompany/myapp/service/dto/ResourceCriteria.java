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
 * Criteria class for the {@link com.mycompany.myapp.domain.Resource} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ResourceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resources?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResourceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter resRouterLink;

    private StringFilter resDescription;

    private StringFilter resFlag;

    private StringFilter resOperate;

    private StringFilter resHref;

    private StringFilter resSrc;

    private StringFilter resText;

    private StringFilter resClass;

    private ZonedDateTimeFilter resEffDate;

    private ZonedDateTimeFilter resExpDate;

    private LongFilter resLft;

    private LongFilter resRgt;

    private LongFilter resLevel;

    private LongFilter resOrder;

    private BooleanFilter leaf;

    private BooleanFilter resDisabled;

    private BooleanFilter resChecked;

    private BooleanFilter resExpanded;

    private BooleanFilter resSelected;

    private LongFilter upperId;

    public ResourceCriteria(){
    }

    public ResourceCriteria(ResourceCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.resRouterLink = other.resRouterLink == null ? null : other.resRouterLink.copy();
        this.resDescription = other.resDescription == null ? null : other.resDescription.copy();
        this.resFlag = other.resFlag == null ? null : other.resFlag.copy();
        this.resOperate = other.resOperate == null ? null : other.resOperate.copy();
        this.resHref = other.resHref == null ? null : other.resHref.copy();
        this.resSrc = other.resSrc == null ? null : other.resSrc.copy();
        this.resText = other.resText == null ? null : other.resText.copy();
        this.resClass = other.resClass == null ? null : other.resClass.copy();
        this.resEffDate = other.resEffDate == null ? null : other.resEffDate.copy();
        this.resExpDate = other.resExpDate == null ? null : other.resExpDate.copy();
        this.resLft = other.resLft == null ? null : other.resLft.copy();
        this.resRgt = other.resRgt == null ? null : other.resRgt.copy();
        this.resLevel = other.resLevel == null ? null : other.resLevel.copy();
        this.resOrder = other.resOrder == null ? null : other.resOrder.copy();
        this.leaf = other.leaf == null ? null : other.leaf.copy();
        this.resDisabled = other.resDisabled == null ? null : other.resDisabled.copy();
        this.resChecked = other.resChecked == null ? null : other.resChecked.copy();
        this.resExpanded = other.resExpanded == null ? null : other.resExpanded.copy();
        this.resSelected = other.resSelected == null ? null : other.resSelected.copy();
        this.upperId = other.upperId == null ? null : other.upperId.copy();
    }

    @Override
    public ResourceCriteria copy() {
        return new ResourceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getResRouterLink() {
        return resRouterLink;
    }

    public void setResRouterLink(StringFilter resRouterLink) {
        this.resRouterLink = resRouterLink;
    }

    public StringFilter getResDescription() {
        return resDescription;
    }

    public void setResDescription(StringFilter resDescription) {
        this.resDescription = resDescription;
    }

    public StringFilter getResFlag() {
        return resFlag;
    }

    public void setResFlag(StringFilter resFlag) {
        this.resFlag = resFlag;
    }

    public StringFilter getResOperate() {
        return resOperate;
    }

    public void setResOperate(StringFilter resOperate) {
        this.resOperate = resOperate;
    }

    public StringFilter getResHref() {
        return resHref;
    }

    public void setResHref(StringFilter resHref) {
        this.resHref = resHref;
    }

    public StringFilter getResSrc() {
        return resSrc;
    }

    public void setResSrc(StringFilter resSrc) {
        this.resSrc = resSrc;
    }

    public StringFilter getResText() {
        return resText;
    }

    public void setResText(StringFilter resText) {
        this.resText = resText;
    }

    public StringFilter getResClass() {
        return resClass;
    }

    public void setResClass(StringFilter resClass) {
        this.resClass = resClass;
    }

    public ZonedDateTimeFilter getResEffDate() {
        return resEffDate;
    }

    public void setResEffDate(ZonedDateTimeFilter resEffDate) {
        this.resEffDate = resEffDate;
    }

    public ZonedDateTimeFilter getResExpDate() {
        return resExpDate;
    }

    public void setResExpDate(ZonedDateTimeFilter resExpDate) {
        this.resExpDate = resExpDate;
    }

    public LongFilter getResLft() {
        return resLft;
    }

    public void setResLft(LongFilter resLft) {
        this.resLft = resLft;
    }

    public LongFilter getResRgt() {
        return resRgt;
    }

    public void setResRgt(LongFilter resRgt) {
        this.resRgt = resRgt;
    }

    public LongFilter getResLevel() {
        return resLevel;
    }

    public void setResLevel(LongFilter resLevel) {
        this.resLevel = resLevel;
    }

    public LongFilter getResOrder() {
        return resOrder;
    }

    public void setResOrder(LongFilter resOrder) {
        this.resOrder = resOrder;
    }

    public BooleanFilter getLeaf() {
        return leaf;
    }

    public void setLeaf(BooleanFilter leaf) {
        this.leaf = leaf;
    }

    public BooleanFilter getResDisabled() {
        return resDisabled;
    }

    public void setResDisabled(BooleanFilter resDisabled) {
        this.resDisabled = resDisabled;
    }

    public BooleanFilter getResChecked() {
        return resChecked;
    }

    public void setResChecked(BooleanFilter resChecked) {
        this.resChecked = resChecked;
    }

    public BooleanFilter getResExpanded() {
        return resExpanded;
    }

    public void setResExpanded(BooleanFilter resExpanded) {
        this.resExpanded = resExpanded;
    }

    public BooleanFilter getResSelected() {
        return resSelected;
    }

    public void setResSelected(BooleanFilter resSelected) {
        this.resSelected = resSelected;
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
        final ResourceCriteria that = (ResourceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(resRouterLink, that.resRouterLink) &&
            Objects.equals(resDescription, that.resDescription) &&
            Objects.equals(resFlag, that.resFlag) &&
            Objects.equals(resOperate, that.resOperate) &&
            Objects.equals(resHref, that.resHref) &&
            Objects.equals(resSrc, that.resSrc) &&
            Objects.equals(resText, that.resText) &&
            Objects.equals(resClass, that.resClass) &&
            Objects.equals(resEffDate, that.resEffDate) &&
            Objects.equals(resExpDate, that.resExpDate) &&
            Objects.equals(resLft, that.resLft) &&
            Objects.equals(resRgt, that.resRgt) &&
            Objects.equals(resLevel, that.resLevel) &&
            Objects.equals(resOrder, that.resOrder) &&
            Objects.equals(leaf, that.leaf) &&
            Objects.equals(resDisabled, that.resDisabled) &&
            Objects.equals(resChecked, that.resChecked) &&
            Objects.equals(resExpanded, that.resExpanded) &&
            Objects.equals(resSelected, that.resSelected) &&
            Objects.equals(upperId, that.upperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        resRouterLink,
        resDescription,
        resFlag,
        resOperate,
        resHref,
        resSrc,
        resText,
        resClass,
        resEffDate,
        resExpDate,
        resLft,
        resRgt,
        resLevel,
        resOrder,
        leaf,
        resDisabled,
        resChecked,
        resExpanded,
        resSelected,
        upperId
        );
    }

    @Override
    public String toString() {
        return "ResourceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (resRouterLink != null ? "resRouterLink=" + resRouterLink + ", " : "") +
                (resDescription != null ? "resDescription=" + resDescription + ", " : "") +
                (resFlag != null ? "resFlag=" + resFlag + ", " : "") +
                (resOperate != null ? "resOperate=" + resOperate + ", " : "") +
                (resHref != null ? "resHref=" + resHref + ", " : "") +
                (resSrc != null ? "resSrc=" + resSrc + ", " : "") +
                (resText != null ? "resText=" + resText + ", " : "") +
                (resClass != null ? "resClass=" + resClass + ", " : "") +
                (resEffDate != null ? "resEffDate=" + resEffDate + ", " : "") +
                (resExpDate != null ? "resExpDate=" + resExpDate + ", " : "") +
                (resLft != null ? "resLft=" + resLft + ", " : "") +
                (resRgt != null ? "resRgt=" + resRgt + ", " : "") +
                (resLevel != null ? "resLevel=" + resLevel + ", " : "") +
                (resOrder != null ? "resOrder=" + resOrder + ", " : "") +
                (leaf != null ? "leaf=" + leaf + ", " : "") +
                (resDisabled != null ? "resDisabled=" + resDisabled + ", " : "") +
                (resChecked != null ? "resChecked=" + resChecked + ", " : "") +
                (resExpanded != null ? "resExpanded=" + resExpanded + ", " : "") +
                (resSelected != null ? "resSelected=" + resSelected + ", " : "") +
                (upperId != null ? "upperId=" + upperId + ", " : "") +
            "}";
    }

}
