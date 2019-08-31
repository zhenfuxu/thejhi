package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GetewayApp;
import com.mycompany.myapp.domain.UserRoleOrganization;
import com.mycompany.myapp.domain.Userx;
import com.mycompany.myapp.domain.Role;
import com.mycompany.myapp.domain.Organization;
import com.mycompany.myapp.repository.UserRoleOrganizationRepository;
import com.mycompany.myapp.service.UserRoleOrganizationService;
import com.mycompany.myapp.service.dto.UserRoleOrganizationDTO;
import com.mycompany.myapp.service.mapper.UserRoleOrganizationMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.UserRoleOrganizationCriteria;
import com.mycompany.myapp.service.UserRoleOrganizationQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserRoleOrganizationResource} REST controller.
 */
@SpringBootTest(classes = GetewayApp.class)
public class UserRoleOrganizationResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBBBBBBB";

    @Autowired
    private UserRoleOrganizationRepository userRoleOrganizationRepository;

    @Autowired
    private UserRoleOrganizationMapper userRoleOrganizationMapper;

    @Autowired
    private UserRoleOrganizationService userRoleOrganizationService;

    @Autowired
    private UserRoleOrganizationQueryService userRoleOrganizationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUserRoleOrganizationMockMvc;

    private UserRoleOrganization userRoleOrganization;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserRoleOrganizationResource userRoleOrganizationResource = new UserRoleOrganizationResource(userRoleOrganizationService, userRoleOrganizationQueryService);
        this.restUserRoleOrganizationMockMvc = MockMvcBuilders.standaloneSetup(userRoleOrganizationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRoleOrganization createEntity(EntityManager em) {
        UserRoleOrganization userRoleOrganization = new UserRoleOrganization()
            .userName(DEFAULT_USER_NAME)
            .roleName(DEFAULT_ROLE_NAME)
            .orgName(DEFAULT_ORG_NAME);
        // Add required entity
        Userx userx;
        if (TestUtil.findAll(em, Userx.class).isEmpty()) {
            userx = UserxResourceIT.createEntity(em);
            em.persist(userx);
            em.flush();
        } else {
            userx = TestUtil.findAll(em, Userx.class).get(0);
        }
        userRoleOrganization.setUser(userx);
        // Add required entity
        Role role;
        if (TestUtil.findAll(em, Role.class).isEmpty()) {
            role = RoleResourceIT.createEntity(em);
            em.persist(role);
            em.flush();
        } else {
            role = TestUtil.findAll(em, Role.class).get(0);
        }
        userRoleOrganization.setRole(role);
        return userRoleOrganization;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRoleOrganization createUpdatedEntity(EntityManager em) {
        UserRoleOrganization userRoleOrganization = new UserRoleOrganization()
            .userName(UPDATED_USER_NAME)
            .roleName(UPDATED_ROLE_NAME)
            .orgName(UPDATED_ORG_NAME);
        // Add required entity
        Userx userx;
        if (TestUtil.findAll(em, Userx.class).isEmpty()) {
            userx = UserxResourceIT.createUpdatedEntity(em);
            em.persist(userx);
            em.flush();
        } else {
            userx = TestUtil.findAll(em, Userx.class).get(0);
        }
        userRoleOrganization.setUser(userx);
        // Add required entity
        Role role;
        if (TestUtil.findAll(em, Role.class).isEmpty()) {
            role = RoleResourceIT.createUpdatedEntity(em);
            em.persist(role);
            em.flush();
        } else {
            role = TestUtil.findAll(em, Role.class).get(0);
        }
        userRoleOrganization.setRole(role);
        return userRoleOrganization;
    }

    @BeforeEach
    public void initTest() {
        userRoleOrganization = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserRoleOrganization() throws Exception {
        int databaseSizeBeforeCreate = userRoleOrganizationRepository.findAll().size();

        // Create the UserRoleOrganization
        UserRoleOrganizationDTO userRoleOrganizationDTO = userRoleOrganizationMapper.toDto(userRoleOrganization);
        restUserRoleOrganizationMockMvc.perform(post("/api/user-role-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRoleOrganizationDTO)))
            .andExpect(status().isCreated());

        // Validate the UserRoleOrganization in the database
        List<UserRoleOrganization> userRoleOrganizationList = userRoleOrganizationRepository.findAll();
        assertThat(userRoleOrganizationList).hasSize(databaseSizeBeforeCreate + 1);
        UserRoleOrganization testUserRoleOrganization = userRoleOrganizationList.get(userRoleOrganizationList.size() - 1);
        assertThat(testUserRoleOrganization.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUserRoleOrganization.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testUserRoleOrganization.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
    }

    @Test
    @Transactional
    public void createUserRoleOrganizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRoleOrganizationRepository.findAll().size();

        // Create the UserRoleOrganization with an existing ID
        userRoleOrganization.setId(1L);
        UserRoleOrganizationDTO userRoleOrganizationDTO = userRoleOrganizationMapper.toDto(userRoleOrganization);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRoleOrganizationMockMvc.perform(post("/api/user-role-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRoleOrganizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRoleOrganization in the database
        List<UserRoleOrganization> userRoleOrganizationList = userRoleOrganizationRepository.findAll();
        assertThat(userRoleOrganizationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserRoleOrganizations() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        // Get all the userRoleOrganizationList
        restUserRoleOrganizationMockMvc.perform(get("/api/user-role-organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRoleOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getUserRoleOrganization() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        // Get the userRoleOrganization
        restUserRoleOrganizationMockMvc.perform(get("/api/user-role-organizations/{id}", userRoleOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userRoleOrganization.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        // Get all the userRoleOrganizationList where userName equals to DEFAULT_USER_NAME
        defaultUserRoleOrganizationShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the userRoleOrganizationList where userName equals to UPDATED_USER_NAME
        defaultUserRoleOrganizationShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        // Get all the userRoleOrganizationList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultUserRoleOrganizationShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the userRoleOrganizationList where userName equals to UPDATED_USER_NAME
        defaultUserRoleOrganizationShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        // Get all the userRoleOrganizationList where userName is not null
        defaultUserRoleOrganizationShouldBeFound("userName.specified=true");

        // Get all the userRoleOrganizationList where userName is null
        defaultUserRoleOrganizationShouldNotBeFound("userName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByRoleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        // Get all the userRoleOrganizationList where roleName equals to DEFAULT_ROLE_NAME
        defaultUserRoleOrganizationShouldBeFound("roleName.equals=" + DEFAULT_ROLE_NAME);

        // Get all the userRoleOrganizationList where roleName equals to UPDATED_ROLE_NAME
        defaultUserRoleOrganizationShouldNotBeFound("roleName.equals=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByRoleNameIsInShouldWork() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        // Get all the userRoleOrganizationList where roleName in DEFAULT_ROLE_NAME or UPDATED_ROLE_NAME
        defaultUserRoleOrganizationShouldBeFound("roleName.in=" + DEFAULT_ROLE_NAME + "," + UPDATED_ROLE_NAME);

        // Get all the userRoleOrganizationList where roleName equals to UPDATED_ROLE_NAME
        defaultUserRoleOrganizationShouldNotBeFound("roleName.in=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByRoleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        // Get all the userRoleOrganizationList where roleName is not null
        defaultUserRoleOrganizationShouldBeFound("roleName.specified=true");

        // Get all the userRoleOrganizationList where roleName is null
        defaultUserRoleOrganizationShouldNotBeFound("roleName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByOrgNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        // Get all the userRoleOrganizationList where orgName equals to DEFAULT_ORG_NAME
        defaultUserRoleOrganizationShouldBeFound("orgName.equals=" + DEFAULT_ORG_NAME);

        // Get all the userRoleOrganizationList where orgName equals to UPDATED_ORG_NAME
        defaultUserRoleOrganizationShouldNotBeFound("orgName.equals=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByOrgNameIsInShouldWork() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        // Get all the userRoleOrganizationList where orgName in DEFAULT_ORG_NAME or UPDATED_ORG_NAME
        defaultUserRoleOrganizationShouldBeFound("orgName.in=" + DEFAULT_ORG_NAME + "," + UPDATED_ORG_NAME);

        // Get all the userRoleOrganizationList where orgName equals to UPDATED_ORG_NAME
        defaultUserRoleOrganizationShouldNotBeFound("orgName.in=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByOrgNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        // Get all the userRoleOrganizationList where orgName is not null
        defaultUserRoleOrganizationShouldBeFound("orgName.specified=true");

        // Get all the userRoleOrganizationList where orgName is null
        defaultUserRoleOrganizationShouldNotBeFound("orgName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        Userx user = userRoleOrganization.getUser();
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);
        Long userId = user.getId();

        // Get all the userRoleOrganizationList where user equals to userId
        defaultUserRoleOrganizationShouldBeFound("userId.equals=" + userId);

        // Get all the userRoleOrganizationList where user equals to userId + 1
        defaultUserRoleOrganizationShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByRoleIsEqualToSomething() throws Exception {
        // Get already existing entity
        Role role = userRoleOrganization.getRole();
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);
        Long roleId = role.getId();

        // Get all the userRoleOrganizationList where role equals to roleId
        defaultUserRoleOrganizationShouldBeFound("roleId.equals=" + roleId);

        // Get all the userRoleOrganizationList where role equals to roleId + 1
        defaultUserRoleOrganizationShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }


    @Test
    @Transactional
    public void getAllUserRoleOrganizationsByOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);
        Organization organization = OrganizationResourceIT.createEntity(em);
        em.persist(organization);
        em.flush();
        userRoleOrganization.setOrganization(organization);
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);
        Long organizationId = organization.getId();

        // Get all the userRoleOrganizationList where organization equals to organizationId
        defaultUserRoleOrganizationShouldBeFound("organizationId.equals=" + organizationId);

        // Get all the userRoleOrganizationList where organization equals to organizationId + 1
        defaultUserRoleOrganizationShouldNotBeFound("organizationId.equals=" + (organizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserRoleOrganizationShouldBeFound(String filter) throws Exception {
        restUserRoleOrganizationMockMvc.perform(get("/api/user-role-organizations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRoleOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME)));

        // Check, that the count call also returns 1
        restUserRoleOrganizationMockMvc.perform(get("/api/user-role-organizations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserRoleOrganizationShouldNotBeFound(String filter) throws Exception {
        restUserRoleOrganizationMockMvc.perform(get("/api/user-role-organizations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserRoleOrganizationMockMvc.perform(get("/api/user-role-organizations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUserRoleOrganization() throws Exception {
        // Get the userRoleOrganization
        restUserRoleOrganizationMockMvc.perform(get("/api/user-role-organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserRoleOrganization() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        int databaseSizeBeforeUpdate = userRoleOrganizationRepository.findAll().size();

        // Update the userRoleOrganization
        UserRoleOrganization updatedUserRoleOrganization = userRoleOrganizationRepository.findById(userRoleOrganization.getId()).get();
        // Disconnect from session so that the updates on updatedUserRoleOrganization are not directly saved in db
        em.detach(updatedUserRoleOrganization);
        updatedUserRoleOrganization
            .userName(UPDATED_USER_NAME)
            .roleName(UPDATED_ROLE_NAME)
            .orgName(UPDATED_ORG_NAME);
        UserRoleOrganizationDTO userRoleOrganizationDTO = userRoleOrganizationMapper.toDto(updatedUserRoleOrganization);

        restUserRoleOrganizationMockMvc.perform(put("/api/user-role-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRoleOrganizationDTO)))
            .andExpect(status().isOk());

        // Validate the UserRoleOrganization in the database
        List<UserRoleOrganization> userRoleOrganizationList = userRoleOrganizationRepository.findAll();
        assertThat(userRoleOrganizationList).hasSize(databaseSizeBeforeUpdate);
        UserRoleOrganization testUserRoleOrganization = userRoleOrganizationList.get(userRoleOrganizationList.size() - 1);
        assertThat(testUserRoleOrganization.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserRoleOrganization.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testUserRoleOrganization.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserRoleOrganization() throws Exception {
        int databaseSizeBeforeUpdate = userRoleOrganizationRepository.findAll().size();

        // Create the UserRoleOrganization
        UserRoleOrganizationDTO userRoleOrganizationDTO = userRoleOrganizationMapper.toDto(userRoleOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRoleOrganizationMockMvc.perform(put("/api/user-role-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRoleOrganizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRoleOrganization in the database
        List<UserRoleOrganization> userRoleOrganizationList = userRoleOrganizationRepository.findAll();
        assertThat(userRoleOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserRoleOrganization() throws Exception {
        // Initialize the database
        userRoleOrganizationRepository.saveAndFlush(userRoleOrganization);

        int databaseSizeBeforeDelete = userRoleOrganizationRepository.findAll().size();

        // Delete the userRoleOrganization
        restUserRoleOrganizationMockMvc.perform(delete("/api/user-role-organizations/{id}", userRoleOrganization.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRoleOrganization> userRoleOrganizationList = userRoleOrganizationRepository.findAll();
        assertThat(userRoleOrganizationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRoleOrganization.class);
        UserRoleOrganization userRoleOrganization1 = new UserRoleOrganization();
        userRoleOrganization1.setId(1L);
        UserRoleOrganization userRoleOrganization2 = new UserRoleOrganization();
        userRoleOrganization2.setId(userRoleOrganization1.getId());
        assertThat(userRoleOrganization1).isEqualTo(userRoleOrganization2);
        userRoleOrganization2.setId(2L);
        assertThat(userRoleOrganization1).isNotEqualTo(userRoleOrganization2);
        userRoleOrganization1.setId(null);
        assertThat(userRoleOrganization1).isNotEqualTo(userRoleOrganization2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRoleOrganizationDTO.class);
        UserRoleOrganizationDTO userRoleOrganizationDTO1 = new UserRoleOrganizationDTO();
        userRoleOrganizationDTO1.setId(1L);
        UserRoleOrganizationDTO userRoleOrganizationDTO2 = new UserRoleOrganizationDTO();
        assertThat(userRoleOrganizationDTO1).isNotEqualTo(userRoleOrganizationDTO2);
        userRoleOrganizationDTO2.setId(userRoleOrganizationDTO1.getId());
        assertThat(userRoleOrganizationDTO1).isEqualTo(userRoleOrganizationDTO2);
        userRoleOrganizationDTO2.setId(2L);
        assertThat(userRoleOrganizationDTO1).isNotEqualTo(userRoleOrganizationDTO2);
        userRoleOrganizationDTO1.setId(null);
        assertThat(userRoleOrganizationDTO1).isNotEqualTo(userRoleOrganizationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userRoleOrganizationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userRoleOrganizationMapper.fromId(null)).isNull();
    }
}
