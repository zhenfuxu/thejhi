package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A UserRoleOrganization.
 */
@Entity
@Table(name = "t_user_role_organizationx")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserRoleOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * 名字。
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 角色。
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 机构。
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 用户。
     */
    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private Userx user;

    /**
     * 角色。
     */
    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private Role role;

    /**
     * 机构。
     */
    @OneToOne
    @JoinColumn(unique = true)
    private Organization organization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public UserRoleOrganization userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public UserRoleOrganization roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrgName() {
        return orgName;
    }

    public UserRoleOrganization orgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Userx getUser() {
        return user;
    }

    public UserRoleOrganization user(Userx userx) {
        this.user = userx;
        return this;
    }

    public void setUser(Userx userx) {
        this.user = userx;
    }

    public Role getRole() {
        return role;
    }

    public UserRoleOrganization role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Organization getOrganization() {
        return organization;
    }

    public UserRoleOrganization organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRoleOrganization)) {
            return false;
        }
        return id != null && id.equals(((UserRoleOrganization) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserRoleOrganization{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", roleName='" + getRoleName() + "'" +
            ", orgName='" + getOrgName() + "'" +
            "}";
    }
}
