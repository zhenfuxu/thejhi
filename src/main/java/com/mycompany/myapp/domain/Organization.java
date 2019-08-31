package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * 组织机构 entity.
 * @author zhenfuxu@gmail.com.
 */
@Entity
@Table(name = "t_organizationx")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * 机构名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 机构代码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 机构标记
     */
    @Column(name = "org_flag")
    private String orgFlag;

    /**
     * 机构行政区划编码
     */
    @Column(name = "org_area_code")
    private String orgAreaCode;

    /**
     * 机构行政区划名字
     */
    @Column(name = "org_area_name")
    private String orgAreaName;

    /**
     * 机构描述
     */
    @Column(name = "org_description")
    private String orgDescription;

    /**
     * 机构左子树
     */
    @Column(name = "org_lft")
    private Long orgLft;

    /**
     * 机构右子树
     */
    @Column(name = "org_rgt")
    private Long orgRgt;

    /**
     * 机构层级
     */
    @Column(name = "org_level")
    private Long orgLevel;

    /**
     * 机构排序
     */
    @Column(name = "org_order")
    private Long orgOrder;

    /**
     * 是否叶子节点
     */
    @Column(name = "leaf")
    private Boolean leaf;

    /**
     * The 上级。
     */
    @ManyToOne
    @JsonIgnoreProperties("organizations")
    private Organization upper;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public Organization orgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public Organization orgCode(String orgCode) {
        this.orgCode = orgCode;
        return this;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgFlag() {
        return orgFlag;
    }

    public Organization orgFlag(String orgFlag) {
        this.orgFlag = orgFlag;
        return this;
    }

    public void setOrgFlag(String orgFlag) {
        this.orgFlag = orgFlag;
    }

    public String getOrgAreaCode() {
        return orgAreaCode;
    }

    public Organization orgAreaCode(String orgAreaCode) {
        this.orgAreaCode = orgAreaCode;
        return this;
    }

    public void setOrgAreaCode(String orgAreaCode) {
        this.orgAreaCode = orgAreaCode;
    }

    public String getOrgAreaName() {
        return orgAreaName;
    }

    public Organization orgAreaName(String orgAreaName) {
        this.orgAreaName = orgAreaName;
        return this;
    }

    public void setOrgAreaName(String orgAreaName) {
        this.orgAreaName = orgAreaName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public Organization orgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
        return this;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public Long getOrgLft() {
        return orgLft;
    }

    public Organization orgLft(Long orgLft) {
        this.orgLft = orgLft;
        return this;
    }

    public void setOrgLft(Long orgLft) {
        this.orgLft = orgLft;
    }

    public Long getOrgRgt() {
        return orgRgt;
    }

    public Organization orgRgt(Long orgRgt) {
        this.orgRgt = orgRgt;
        return this;
    }

    public void setOrgRgt(Long orgRgt) {
        this.orgRgt = orgRgt;
    }

    public Long getOrgLevel() {
        return orgLevel;
    }

    public Organization orgLevel(Long orgLevel) {
        this.orgLevel = orgLevel;
        return this;
    }

    public void setOrgLevel(Long orgLevel) {
        this.orgLevel = orgLevel;
    }

    public Long getOrgOrder() {
        return orgOrder;
    }

    public Organization orgOrder(Long orgOrder) {
        this.orgOrder = orgOrder;
        return this;
    }

    public void setOrgOrder(Long orgOrder) {
        this.orgOrder = orgOrder;
    }

    public Boolean isLeaf() {
        return leaf;
    }

    public Organization leaf(Boolean leaf) {
        this.leaf = leaf;
        return this;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Organization getUpper() {
        return upper;
    }

    public Organization upper(Organization organization) {
        this.upper = organization;
        return this;
    }

    public void setUpper(Organization organization) {
        this.upper = organization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Organization{" +
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
            "}";
    }
}
