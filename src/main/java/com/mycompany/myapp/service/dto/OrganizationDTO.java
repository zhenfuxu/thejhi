package com.mycompany.myapp.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Organization} entity.
 */
@ApiModel(description = "组织机构 entity. @author zhenfuxu@gmail.com.")
public class OrganizationDTO implements Serializable {

    private Long id;

    /**
     * 机构名称
     */
    @ApiModelProperty(value = "机构名称")
    private String orgName;

    /**
     * 机构代码
     */
    @ApiModelProperty(value = "机构代码")
    private String orgCode;

    /**
     * 机构标记
     */
    @ApiModelProperty(value = "机构标记")
    private String orgFlag;

    /**
     * 机构行政区划编码
     */
    @ApiModelProperty(value = "机构行政区划编码")
    private String orgAreaCode;

    /**
     * 机构行政区划名字
     */
    @ApiModelProperty(value = "机构行政区划名字")
    private String orgAreaName;

    /**
     * 机构描述
     */
    @ApiModelProperty(value = "机构描述")
    private String orgDescription;

    /**
     * 机构左子树
     */
    @ApiModelProperty(value = "机构左子树")
    private Long orgLft;

    /**
     * 机构右子树
     */
    @ApiModelProperty(value = "机构右子树")
    private Long orgRgt;

    /**
     * 机构层级
     */
    @ApiModelProperty(value = "机构层级")
    private Long orgLevel;

    /**
     * 机构排序
     */
    @ApiModelProperty(value = "机构排序")
    private Long orgOrder;

    /**
     * 是否叶子节点
     */
    @ApiModelProperty(value = "是否叶子节点")
    private Boolean leaf;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgFlag() {
        return orgFlag;
    }

    public void setOrgFlag(String orgFlag) {
        this.orgFlag = orgFlag;
    }

    public String getOrgAreaCode() {
        return orgAreaCode;
    }

    public void setOrgAreaCode(String orgAreaCode) {
        this.orgAreaCode = orgAreaCode;
    }

    public String getOrgAreaName() {
        return orgAreaName;
    }

    public void setOrgAreaName(String orgAreaName) {
        this.orgAreaName = orgAreaName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public Long getOrgLft() {
        return orgLft;
    }

    public void setOrgLft(Long orgLft) {
        this.orgLft = orgLft;
    }

    public Long getOrgRgt() {
        return orgRgt;
    }

    public void setOrgRgt(Long orgRgt) {
        this.orgRgt = orgRgt;
    }

    public Long getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Long orgLevel) {
        this.orgLevel = orgLevel;
    }

    public Long getOrgOrder() {
        return orgOrder;
    }

    public void setOrgOrder(Long orgOrder) {
        this.orgOrder = orgOrder;
    }

    public Boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Long getUpperId() {
        return upperId;
    }

    public void setUpperId(Long organizationId) {
        this.upperId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganizationDTO organizationDTO = (OrganizationDTO) o;
        if (organizationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organizationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrganizationDTO{" +
            "id=" + getId() +
            ", orgName='" + getOrgName() + "'" +
            ", orgCode='" + getOrgCode() + "'" +
            ", orgFlag='" + getOrgFlag() + "'" +
            ", orgAreaCode='" + getOrgAreaCode() + "'" +
            ", orgAreaName='" + getOrgAreaName() + "'" +
            ", orgDescription='" + getOrgDescription() + "'" +
            ", orgLft=" + getOrgLft() +
            ", orgRgt=" + getOrgRgt() +
            ", orgLevel=" + getOrgLevel() +
            ", orgOrder=" + getOrgOrder() +
            ", leaf='" + isLeaf() + "'" +
            ", upper=" + getUpperId() +
            "}";
    }
}
