package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色 entity.
 * @author zhenfuxu@gmail.com.
 */
@Entity
@Table(name = "t_rolex")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色描述
     */
    @Column(name = "role_description")
    private String roleDescription;

    /**
     * 角色标记
     */
    @Column(name = "role_flag")
    private String roleFlag;

    /**
     * 角色生效时间
     */
    @Column(name = "role_eff_date")
    private ZonedDateTime roleEffDate;

    /**
     * 角色失效时间
     */
    @Column(name = "role_exp_date")
    private ZonedDateTime roleExpDate;

    /**
     * 角色对应多个资源。
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "t_rolex_resources",
               joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "resources_id", referencedColumnName = "id"))
    private Set<Resource> resources = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public Role roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public Role roleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
        return this;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getRoleFlag() {
        return roleFlag;
    }

    public Role roleFlag(String roleFlag) {
        this.roleFlag = roleFlag;
        return this;
    }

    public void setRoleFlag(String roleFlag) {
        this.roleFlag = roleFlag;
    }

    public ZonedDateTime getRoleEffDate() {
        return roleEffDate;
    }

    public Role roleEffDate(ZonedDateTime roleEffDate) {
        this.roleEffDate = roleEffDate;
        return this;
    }

    public void setRoleEffDate(ZonedDateTime roleEffDate) {
        this.roleEffDate = roleEffDate;
    }

    public ZonedDateTime getRoleExpDate() {
        return roleExpDate;
    }

    public Role roleExpDate(ZonedDateTime roleExpDate) {
        this.roleExpDate = roleExpDate;
        return this;
    }

    public void setRoleExpDate(ZonedDateTime roleExpDate) {
        this.roleExpDate = roleExpDate;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public Role resources(Set<Resource> resources) {
        this.resources = resources;
        return this;
    }

    public Role addResources(Resource resource) {
        this.resources.add(resource);
        // resource.getRoles().add(this);
        return this;
    }

    public Role removeResources(Resource resource) {
        this.resources.remove(resource);
        // resource.getRoles().remove(this);
        return this;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            ", roleDescription='" + getRoleDescription() + "'" +
            ", roleFlag='" + getRoleFlag() + "'" +
            ", roleEffDate='" + getRoleEffDate() + "'" +
            ", roleExpDate='" + getRoleExpDate() + "'" +
            "}";
    }
}
