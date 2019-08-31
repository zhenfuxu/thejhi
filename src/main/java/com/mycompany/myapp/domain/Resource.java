package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 资源 entity.
 * @author zhenfuxu@gmail.com.
 */
@Entity
@Table(name = "t_resourcex")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * 资源连接
     */
    @Column(name = "res_router_link")
    private String resRouterLink;

    /**
     * 资源描述
     */
    @Column(name = "res_description")
    private String resDescription;

    /**
     * 资源标记
     */
    @Column(name = "res_flag")
    private String resFlag;

    /**
     * 资源操作
     */
    @Column(name = "res_operate")
    private String resOperate;

    /**
     * 资源a连接
     */
    @Column(name = "res_href")
    private String resHref;

    /**
     * 资源源地址
     */
    @Column(name = "res_src")
    private String resSrc;

    /**
     * 资源显示文本
     */
    @Column(name = "res_text")
    private String resText;

    /**
     * 资源css类
     */
    @Column(name = "res_class")
    private String resClass;

    /**
     * 资源生效时间
     */
    @Column(name = "res_eff_date")
    private ZonedDateTime resEffDate;

    /**
     * 资源失效时间
     */
    @Column(name = "res_exp_date")
    private ZonedDateTime resExpDate;

    /**
     * 资源左
     */
    @Column(name = "res_lft")
    private Long resLft;

    /**
     * 资源右
     */
    @Column(name = "res_rgt")
    private Long resRgt;

    /**
     * 资源层级
     */
    @Column(name = "res_level")
    private Long resLevel;

    /**
     * 资源顺序
     */
    @Column(name = "res_order")
    private Long resOrder;

    /**
     * 资源是否叶子
     */
    @Column(name = "leaf")
    private Boolean leaf;

    /**
     * 资源禁用
     */
    @Column(name = "res_disabled")
    private Boolean resDisabled;

    /**
     * 资源勾选
     */
    @Column(name = "res_checked")
    private Boolean resChecked;

    /**
     * 资源展开
     */
    @Column(name = "res_expanded")
    private Boolean resExpanded;

    /**
     * 资源选中
     */
    @Column(name = "res_selected")
    private Boolean resSelected;

    /**
     * The 上级。
     */
    @ManyToOne
    @JsonIgnoreProperties("resources")
    private Resource upper;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResRouterLink() {
        return resRouterLink;
    }

    public Resource resRouterLink(String resRouterLink) {
        this.resRouterLink = resRouterLink;
        return this;
    }

    public void setResRouterLink(String resRouterLink) {
        this.resRouterLink = resRouterLink;
    }

    public String getResDescription() {
        return resDescription;
    }

    public Resource resDescription(String resDescription) {
        this.resDescription = resDescription;
        return this;
    }

    public void setResDescription(String resDescription) {
        this.resDescription = resDescription;
    }

    public String getResFlag() {
        return resFlag;
    }

    public Resource resFlag(String resFlag) {
        this.resFlag = resFlag;
        return this;
    }

    public void setResFlag(String resFlag) {
        this.resFlag = resFlag;
    }

    public String getResOperate() {
        return resOperate;
    }

    public Resource resOperate(String resOperate) {
        this.resOperate = resOperate;
        return this;
    }

    public void setResOperate(String resOperate) {
        this.resOperate = resOperate;
    }

    public String getResHref() {
        return resHref;
    }

    public Resource resHref(String resHref) {
        this.resHref = resHref;
        return this;
    }

    public void setResHref(String resHref) {
        this.resHref = resHref;
    }

    public String getResSrc() {
        return resSrc;
    }

    public Resource resSrc(String resSrc) {
        this.resSrc = resSrc;
        return this;
    }

    public void setResSrc(String resSrc) {
        this.resSrc = resSrc;
    }

    public String getResText() {
        return resText;
    }

    public Resource resText(String resText) {
        this.resText = resText;
        return this;
    }

    public void setResText(String resText) {
        this.resText = resText;
    }

    public String getResClass() {
        return resClass;
    }

    public Resource resClass(String resClass) {
        this.resClass = resClass;
        return this;
    }

    public void setResClass(String resClass) {
        this.resClass = resClass;
    }

    public ZonedDateTime getResEffDate() {
        return resEffDate;
    }

    public Resource resEffDate(ZonedDateTime resEffDate) {
        this.resEffDate = resEffDate;
        return this;
    }

    public void setResEffDate(ZonedDateTime resEffDate) {
        this.resEffDate = resEffDate;
    }

    public ZonedDateTime getResExpDate() {
        return resExpDate;
    }

    public Resource resExpDate(ZonedDateTime resExpDate) {
        this.resExpDate = resExpDate;
        return this;
    }

    public void setResExpDate(ZonedDateTime resExpDate) {
        this.resExpDate = resExpDate;
    }

    public Long getResLft() {
        return resLft;
    }

    public Resource resLft(Long resLft) {
        this.resLft = resLft;
        return this;
    }

    public void setResLft(Long resLft) {
        this.resLft = resLft;
    }

    public Long getResRgt() {
        return resRgt;
    }

    public Resource resRgt(Long resRgt) {
        this.resRgt = resRgt;
        return this;
    }

    public void setResRgt(Long resRgt) {
        this.resRgt = resRgt;
    }

    public Long getResLevel() {
        return resLevel;
    }

    public Resource resLevel(Long resLevel) {
        this.resLevel = resLevel;
        return this;
    }

    public void setResLevel(Long resLevel) {
        this.resLevel = resLevel;
    }

    public Long getResOrder() {
        return resOrder;
    }

    public Resource resOrder(Long resOrder) {
        this.resOrder = resOrder;
        return this;
    }

    public void setResOrder(Long resOrder) {
        this.resOrder = resOrder;
    }

    public Boolean isLeaf() {
        return leaf;
    }

    public Resource leaf(Boolean leaf) {
        this.leaf = leaf;
        return this;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean isResDisabled() {
        return resDisabled;
    }

    public Resource resDisabled(Boolean resDisabled) {
        this.resDisabled = resDisabled;
        return this;
    }

    public void setResDisabled(Boolean resDisabled) {
        this.resDisabled = resDisabled;
    }

    public Boolean isResChecked() {
        return resChecked;
    }

    public Resource resChecked(Boolean resChecked) {
        this.resChecked = resChecked;
        return this;
    }

    public void setResChecked(Boolean resChecked) {
        this.resChecked = resChecked;
    }

    public Boolean isResExpanded() {
        return resExpanded;
    }

    public Resource resExpanded(Boolean resExpanded) {
        this.resExpanded = resExpanded;
        return this;
    }

    public void setResExpanded(Boolean resExpanded) {
        this.resExpanded = resExpanded;
    }

    public Boolean isResSelected() {
        return resSelected;
    }

    public Resource resSelected(Boolean resSelected) {
        this.resSelected = resSelected;
        return this;
    }

    public void setResSelected(Boolean resSelected) {
        this.resSelected = resSelected;
    }

    public Resource getUpper() {
        return upper;
    }

    public Resource upper(Resource resource) {
        this.upper = resource;
        return this;
    }

    public void setUpper(Resource resource) {
        this.upper = resource;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource)) {
            return false;
        }
        return id != null && id.equals(((Resource) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Resource{" +
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
            "}";
    }
}
