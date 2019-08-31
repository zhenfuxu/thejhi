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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Userx} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.UserxResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /userxes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserxCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter login;

    private StringFilter password;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter email;

    private BooleanFilter activated;

    private StringFilter langKey;

    private StringFilter imageUrl;

    private StringFilter activationKey;

    private StringFilter resetKey;

    private InstantFilter resetDate;

    private LongFilter organizationId;

    private LongFilter rolesId;

    public UserxCriteria(){
    }

    public UserxCriteria(UserxCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.login = other.login == null ? null : other.login.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.langKey = other.langKey == null ? null : other.langKey.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.activationKey = other.activationKey == null ? null : other.activationKey.copy();
        this.resetKey = other.resetKey == null ? null : other.resetKey.copy();
        this.resetDate = other.resetDate == null ? null : other.resetDate.copy();
        this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
        this.rolesId = other.rolesId == null ? null : other.rolesId.copy();
    }

    @Override
    public UserxCriteria copy() {
        return new UserxCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getPassword() {
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public StringFilter getLangKey() {
        return langKey;
    }

    public void setLangKey(StringFilter langKey) {
        this.langKey = langKey;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StringFilter getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(StringFilter activationKey) {
        this.activationKey = activationKey;
    }

    public StringFilter getResetKey() {
        return resetKey;
    }

    public void setResetKey(StringFilter resetKey) {
        this.resetKey = resetKey;
    }

    public InstantFilter getResetDate() {
        return resetDate;
    }

    public void setResetDate(InstantFilter resetDate) {
        this.resetDate = resetDate;
    }

    public LongFilter getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(LongFilter organizationId) {
        this.organizationId = organizationId;
    }

    public LongFilter getRolesId() {
        return rolesId;
    }

    public void setRolesId(LongFilter rolesId) {
        this.rolesId = rolesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserxCriteria that = (UserxCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(login, that.login) &&
            Objects.equals(password, that.password) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(langKey, that.langKey) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(activationKey, that.activationKey) &&
            Objects.equals(resetKey, that.resetKey) &&
            Objects.equals(resetDate, that.resetDate) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(rolesId, that.rolesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        login,
        password,
        firstName,
        lastName,
        email,
        activated,
        langKey,
        imageUrl,
        activationKey,
        resetKey,
        resetDate,
        organizationId,
        rolesId
        );
    }

    @Override
    public String toString() {
        return "UserxCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (activated != null ? "activated=" + activated + ", " : "") +
                (langKey != null ? "langKey=" + langKey + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (activationKey != null ? "activationKey=" + activationKey + ", " : "") +
                (resetKey != null ? "resetKey=" + resetKey + ", " : "") +
                (resetDate != null ? "resetDate=" + resetDate + ", " : "") +
                (organizationId != null ? "organizationId=" + organizationId + ", " : "") +
                (rolesId != null ? "rolesId=" + rolesId + ", " : "") +
            "}";
    }

}
