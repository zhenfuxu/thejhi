package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户
 * @author zhenfuxu@gmail.com.
 */
@Entity
@Table(name = "t_userx")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Userx implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * 登录账号
     */
    @NotNull
    @Size(min = 50)
    @Column(name = "login", nullable = false)
    private String login;

    /**
     * 登录密码
     */
    @Size(min = 60)
    @Column(name = "password")
    private String password;

    /**
     * 名
     */
    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    /**
     * 姓
     */
    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 是否激活
     */
    @Column(name = "activated")
    private Boolean activated;

    /**
     * 语言
     */
    @Column(name = "lang_key")
    private String langKey;

    /**
     * 头像
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 激活密钥
     */
    @Column(name = "activation_key")
    private String activationKey;

    /**
     * 重置密钥
     */
    @Column(name = "reset_key")
    private String resetKey;

    /**
     * 重置时间
     */
    @Column(name = "reset_date")
    private Instant resetDate;

    /**
     * The 用户所在机构。
     */
    @ManyToOne
    @JsonIgnoreProperties("userxes")
    private Organization organization;

    /**
     * 用户对应多个角色。
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "t_userx_roles",
               joinColumns = @JoinColumn(name = "userx_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public Userx login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public Userx password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public Userx firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Userx lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Userx email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActivated() {
        return activated;
    }

    public Userx activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public Userx langKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Userx imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public Userx activationKey(String activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public Userx resetKey(String resetKey) {
        this.resetKey = resetKey;
        return this;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public Userx resetDate(Instant resetDate) {
        this.resetDate = resetDate;
        return this;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Userx organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Userx roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Userx addRoles(Role role) {
        this.roles.add(role);
        // role.getUserxes().add(this);
        return this;
    }

    public Userx removeRoles(Role role) {
        this.roles.remove(role);
        // role.getUserxes().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Userx)) {
            return false;
        }
        return id != null && id.equals(((Userx) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Userx{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", activated='" + isActivated() + "'" +
            ", langKey='" + getLangKey() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", activationKey='" + getActivationKey() + "'" +
            ", resetKey='" + getResetKey() + "'" +
            ", resetDate='" + getResetDate() + "'" +
            "}";
    }
}
