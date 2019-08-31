package com.mycompany.myapp.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Resource} entity.
 */
@ApiModel(description = "资源 entity. @author zhenfuxu@gmail.com.")
public class ResourceDTO implements Serializable {

    private Long id;

    /**
     * 资源连接
     */
    @ApiModelProperty(value = "资源连接")
    private String resRouterLink;

    /**
     * 资源描述
     */
    @ApiModelProperty(value = "资源描述")
    private String resDescription;

    /**
     * 资源标记
     */
    @ApiModelProperty(value = "资源标记")
    private String resFlag;

    /**
     * 资源操作
     */
    @ApiModelProperty(value = "资源操作")
    private String resOperate;

    /**
     * 资源a连接
     */
    @ApiModelProperty(value = "资源a连接")
    private String resHref;

    /**
     * 资源源地址
     */
    @ApiModelProperty(value = "资源源地址")
    private String resSrc;

    /**
     * 资源显示文本
     */
    @ApiModelProperty(value = "资源显示文本")
    private String resText;

    /**
     * 资源css类
     */
    @ApiModelProperty(value = "资源css类")
    private String resClass;

    /**
     * 资源生效时间
     */
    @ApiModelProperty(value = "资源生效时间")
    private ZonedDateTime resEffDate;

    /**
     * 资源失效时间
     */
    @ApiModelProperty(value = "资源失效时间")
    private ZonedDateTime resExpDate;

    /**
     * 资源左
     */
    @ApiModelProperty(value = "资源左")
    private Long resLft;

    /**
     * 资源右
     */
    @ApiModelProperty(value = "资源右")
    private Long resRgt;

    /**
     * 资源层级
     */
    @ApiModelProperty(value = "资源层级")
    private Long resLevel;

    /**
     * 资源顺序
     */
    @ApiModelProperty(value = "资源顺序")
    private Long resOrder;

    /**
     * 资源是否叶子
     */
    @ApiModelProperty(value = "资源是否叶子")
    private Boolean leaf;

    /**
     * 资源禁用
     */
    @ApiModelProperty(value = "资源禁用")
    private Boolean resDisabled;

    /**
     * 资源勾选
     */
    @ApiModelProperty(value = "资源勾选")
    private Boolean resChecked;

    /**
     * 资源展开
     */
    @ApiModelProperty(value = "资源展开")
    private Boolean resExpanded;

    /**
     * 资源选中
     */
    @ApiModelProperty(value = "资源选中")
    private Boolean resSelected;

    /**
     * The 上级。
     */
    @ApiModelProperty(value = "The 上级。")

    private Long upperId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResRouterLink() {
        return resRouterLink;
    }

    public void setResRouterLink(String resRouterLink) {
        this.resRouterLink = resRouterLink;
    }

    public String getResDescription() {
        return resDescription;
    }

    public void setResDescription(String resDescription) {
        this.resDescription = resDescription;
    }

    public String getResFlag() {
        return resFlag;
    }

    public void setResFlag(String resFlag) {
        this.resFlag = resFlag;
    }

    public String getResOperate() {
        return resOperate;
    }

    public void setResOperate(String resOperate) {
        this.resOperate = resOperate;
    }

    public String getResHref() {
        return resHref;
    }

    public void setResHref(String resHref) {
        this.resHref = resHref;
    }

    public String getResSrc() {
        return resSrc;
    }

    public void setResSrc(String resSrc) {
        this.resSrc = resSrc;
    }

    public String getResText() {
        return resText;
    }

    public void setResText(String resText) {
        this.resText = resText;
    }

    public String getResClass() {
        return resClass;
    }

    public void setResClass(String resClass) {
        this.resClass = resClass;
    }

    public ZonedDateTime getResEffDate() {
        return resEffDate;
    }

    public void setResEffDate(ZonedDateTime resEffDate) {
        this.resEffDate = resEffDate;
    }

    public ZonedDateTime getResExpDate() {
        return resExpDate;
    }

    public void setResExpDate(ZonedDateTime resExpDate) {
        this.resExpDate = resExpDate;
    }

    public Long getResLft() {
        return resLft;
    }

    public void setResLft(Long resLft) {
        this.resLft = resLft;
    }

    public Long getResRgt() {
        return resRgt;
    }

    public void setResRgt(Long resRgt) {
        this.resRgt = resRgt;
    }

    public Long getResLevel() {
        return resLevel;
    }

    public void setResLevel(Long resLevel) {
        this.resLevel = resLevel;
    }

    public Long getResOrder() {
        return resOrder;
    }

    public void setResOrder(Long resOrder) {
        this.resOrder = resOrder;
    }

    public Boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean isResDisabled() {
        return resDisabled;
    }

    public void setResDisabled(Boolean resDisabled) {
        this.resDisabled = resDisabled;
    }

    public Boolean isResChecked() {
        return resChecked;
    }

    public void setResChecked(Boolean resChecked) {
        this.resChecked = resChecked;
    }

    public Boolean isResExpanded() {
        return resExpanded;
    }

    public void setResExpanded(Boolean resExpanded) {
        this.resExpanded = resExpanded;
    }

    public Boolean isResSelected() {
        return resSelected;
    }

    public void setResSelected(Boolean resSelected) {
        this.resSelected = resSelected;
    }

    public Long getUpperId() {
        return upperId;
    }

    public void setUpperId(Long resourceId) {
        this.upperId = resourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourceDTO resourceDTO = (ResourceDTO) o;
        if (resourceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resourceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResourceDTO{" +
            "id=" + getId() +
            ", resRouterLink='" + getResRouterLink() + "'" +
            ", resDescription='" + getResDescription() + "'" +
            ", resFlag='" + getResFlag() + "'" +
            ", resOperate='" + getResOperate() + "'" +
            ", resHref='" + getResHref() + "'" +
            ", resSrc='" + getResSrc() + "'" +
            ", resText='" + getResText() + "'" +
            ", resClass='" + getResClass() + "'" +
            ", resEffDate='" + getResEffDate() + "'" +
            ", resExpDate='" + getResExpDate() + "'" +
            ", resLft=" + getResLft() +
            ", resRgt=" + getResRgt() +
            ", resLevel=" + getResLevel() +
            ", resOrder=" + getResOrder() +
            ", leaf='" + isLeaf() + "'" +
            ", resDisabled='" + isResDisabled() + "'" +
            ", resChecked='" + isResChecked() + "'" +
            ", resExpanded='" + isResExpanded() + "'" +
            ", resSelected='" + isResSelected() + "'" +
            ", upper=" + getUpperId() +
            "}";
    }
}
