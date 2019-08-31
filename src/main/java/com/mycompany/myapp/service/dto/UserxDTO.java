package com.mycompany.myapp.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Userx} entity.
 */
@ApiModel(description = "用户 @author zhenfuxu@gmail.com.")
public class UserxDTO implements Serializable {

    private Long id;

    /**
     * 登录账号
     */
    @NotNull
    @Size(min = 50)
    @ApiModelProperty(value = "登录账号", required = true)
    private String login;

    /**
     * 登录密码
     */
    @Size(min = 60)
    @ApiModelProperty(value = "登录密码")
    private String password;

    /**
     * 名
     */
    @Size(max = 50)
    @ApiModelProperty(value = "名")
    private String firstName;

    /**
     * 姓
     */
    @Size(max = 50)
    @ApiModelProperty(value = "姓")
    private String lastName;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 是否激活
     */
    @ApiModelProperty(value = "是否激活")
    private Boolean activated;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言")
    private String langKey;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String imageUrl;

    /**
     * 激活密钥
     */
    @ApiModelProperty(value = "激活密钥")
    private String activationKey;

    /**
     * 重置密钥
     */
    @ApiModelProperty(value = "重置密钥")
    private String resetKey;

    /**
     * 重置时间
     */
    @ApiModelProperty(value = "重置时间")
    private Instant resetDate;

    /**
     * The 用户所在机构。
     */
    @ApiModelProperty(value = "The 用户所在机构。")

    private Long organizationId;
    /**
     * 用户对应多个角色。
     */
    @ApiModelProperty(value = "用户对应多个角色。")

    private Set<RoleDTO> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserxDTO userxDTO = (UserxDTO) o;
        if (userxDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userxDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserxDTO{" +
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
            ", organization=" + getOrganizationId() +
            "}";
    }
}
