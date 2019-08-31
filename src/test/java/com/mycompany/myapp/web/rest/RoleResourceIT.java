package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GetewayApp;
import com.mycompany.myapp.domain.Role;
import com.mycompany.myapp.domain.Resource;
import com.mycompany.myapp.repository.RoleRepository;
import com.mycompany.myapp.service.RoleService;
import com.mycompany.myapp.service.dto.RoleDTO;
import com.mycompany.myapp.service.mapper.RoleMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.RoleCriteria;
import com.mycompany.myapp.service.RoleQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RoleResource} REST controller.
 */
@SpringBootTest(classes = GetewayApp.class)
public class RoleResourceIT {

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_FLAG = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ROLE_EFF_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ROLE_EFF_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ROLE_EFF_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_ROLE_EXP_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ROLE_EXP_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ROLE_EXP_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private RoleRepository roleRepository;

    @Mock
    private RoleRepository roleRepositoryMock;

    @Autowired
    private RoleMapper roleMapper;

    @Mock
    private RoleService roleServiceMock;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleQueryService roleQueryService;

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

    private MockMvc restRoleMockMvc;

    private Role role;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoleResource roleResource = new RoleResource(roleService, roleQueryService);
        this.restRoleMockMvc = MockMvcBuilders.standaloneSetup(roleResource)
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
    public static Role createEntity(EntityManager em) {
        Role role = new Role()
            .roleName(DEFAULT_ROLE_NAME)
            .roleDescription(DEFAULT_ROLE_DESCRIPTION)
            .roleFlag(DEFAULT_ROLE_FLAG)
            .roleEffDate(DEFAULT_ROLE_EFF_DATE)
            .roleExpDate(DEFAULT_ROLE_EXP_DATE);
        return role;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Role createUpdatedEntity(EntityManager em) {
        Role role = new Role()
            .roleName(UPDATED_ROLE_NAME)
            .roleDescription(UPDATED_ROLE_DESCRIPTION)
            .roleFlag(UPDATED_ROLE_FLAG)
            .roleEffDate(UPDATED_ROLE_EFF_DATE)
            .roleExpDate(UPDATED_ROLE_EXP_DATE);
        return role;
    }

    @BeforeEach
    public void initTest() {
        role = createEntity(em);
    }

    @Test
    @Transactional
    public void createRole() throws Exception {
        int databaseSizeBeforeCreate = roleRepository.findAll().size();

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);
        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isCreated());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeCreate + 1);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testRole.getRoleDescription()).isEqualTo(DEFAULT_ROLE_DESCRIPTION);
        assertThat(testRole.getRoleFlag()).isEqualTo(DEFAULT_ROLE_FLAG);
        assertThat(testRole.getRoleEffDate()).isEqualTo(DEFAULT_ROLE_EFF_DATE);
        assertThat(testRole.getRoleExpDate()).isEqualTo(DEFAULT_ROLE_EXP_DATE);
    }

    @Test
    @Transactional
    public void createRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleRepository.findAll().size();

        // Create the Role with an existing ID
        role.setId(1L);
        RoleDTO roleDTO = roleMapper.toDto(role);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRoles() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(role.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].roleDescription").value(hasItem(DEFAULT_ROLE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].roleFlag").value(hasItem(DEFAULT_ROLE_FLAG.toString())))
            .andExpect(jsonPath("$.[*].roleEffDate").value(hasItem(sameInstant(DEFAULT_ROLE_EFF_DATE))))
            .andExpect(jsonPath("$.[*].roleExpDate").value(hasItem(sameInstant(DEFAULT_ROLE_EXP_DATE))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRolesWithEagerRelationshipsIsEnabled() throws Exception {
        RoleResource roleResource = new RoleResource(roleServiceMock, roleQueryService);
        when(roleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRoleMockMvc = MockMvcBuilders.standaloneSetup(roleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRoleMockMvc.perform(get("/api/roles?eagerload=true"))
        .andExpect(status().isOk());

        verify(roleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRolesWithEagerRelationshipsIsNotEnabled() throws Exception {
        RoleResource roleResource = new RoleResource(roleServiceMock, roleQueryService);
            when(roleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRoleMockMvc = MockMvcBuilders.standaloneSetup(roleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRoleMockMvc.perform(get("/api/roles?eagerload=true"))
        .andExpect(status().isOk());

            verify(roleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", role.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(role.getId().intValue()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.roleDescription").value(DEFAULT_ROLE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.roleFlag").value(DEFAULT_ROLE_FLAG.toString()))
            .andExpect(jsonPath("$.roleEffDate").value(sameInstant(DEFAULT_ROLE_EFF_DATE)))
            .andExpect(jsonPath("$.roleExpDate").value(sameInstant(DEFAULT_ROLE_EXP_DATE)));
    }

    @Test
    @Transactional
    public void getAllRolesByRoleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleName equals to DEFAULT_ROLE_NAME
        defaultRoleShouldBeFound("roleName.equals=" + DEFAULT_ROLE_NAME);

        // Get all the roleList where roleName equals to UPDATED_ROLE_NAME
        defaultRoleShouldNotBeFound("roleName.equals=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleNameIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleName in DEFAULT_ROLE_NAME or UPDATED_ROLE_NAME
        defaultRoleShouldBeFound("roleName.in=" + DEFAULT_ROLE_NAME + "," + UPDATED_ROLE_NAME);

        // Get all the roleList where roleName equals to UPDATED_ROLE_NAME
        defaultRoleShouldNotBeFound("roleName.in=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleName is not null
        defaultRoleShouldBeFound("roleName.specified=true");

        // Get all the roleList where roleName is null
        defaultRoleShouldNotBeFound("roleName.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByRoleDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleDescription equals to DEFAULT_ROLE_DESCRIPTION
        defaultRoleShouldBeFound("roleDescription.equals=" + DEFAULT_ROLE_DESCRIPTION);

        // Get all the roleList where roleDescription equals to UPDATED_ROLE_DESCRIPTION
        defaultRoleShouldNotBeFound("roleDescription.equals=" + UPDATED_ROLE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleDescription in DEFAULT_ROLE_DESCRIPTION or UPDATED_ROLE_DESCRIPTION
        defaultRoleShouldBeFound("roleDescription.in=" + DEFAULT_ROLE_DESCRIPTION + "," + UPDATED_ROLE_DESCRIPTION);

        // Get all the roleList where roleDescription equals to UPDATED_ROLE_DESCRIPTION
        defaultRoleShouldNotBeFound("roleDescription.in=" + UPDATED_ROLE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleDescription is not null
        defaultRoleShouldBeFound("roleDescription.specified=true");

        // Get all the roleList where roleDescription is null
        defaultRoleShouldNotBeFound("roleDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByRoleFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleFlag equals to DEFAULT_ROLE_FLAG
        defaultRoleShouldBeFound("roleFlag.equals=" + DEFAULT_ROLE_FLAG);

        // Get all the roleList where roleFlag equals to UPDATED_ROLE_FLAG
        defaultRoleShouldNotBeFound("roleFlag.equals=" + UPDATED_ROLE_FLAG);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleFlagIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleFlag in DEFAULT_ROLE_FLAG or UPDATED_ROLE_FLAG
        defaultRoleShouldBeFound("roleFlag.in=" + DEFAULT_ROLE_FLAG + "," + UPDATED_ROLE_FLAG);

        // Get all the roleList where roleFlag equals to UPDATED_ROLE_FLAG
        defaultRoleShouldNotBeFound("roleFlag.in=" + UPDATED_ROLE_FLAG);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleFlag is not null
        defaultRoleShouldBeFound("roleFlag.specified=true");

        // Get all the roleList where roleFlag is null
        defaultRoleShouldNotBeFound("roleFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByRoleEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleEffDate equals to DEFAULT_ROLE_EFF_DATE
        defaultRoleShouldBeFound("roleEffDate.equals=" + DEFAULT_ROLE_EFF_DATE);

        // Get all the roleList where roleEffDate equals to UPDATED_ROLE_EFF_DATE
        defaultRoleShouldNotBeFound("roleEffDate.equals=" + UPDATED_ROLE_EFF_DATE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleEffDate in DEFAULT_ROLE_EFF_DATE or UPDATED_ROLE_EFF_DATE
        defaultRoleShouldBeFound("roleEffDate.in=" + DEFAULT_ROLE_EFF_DATE + "," + UPDATED_ROLE_EFF_DATE);

        // Get all the roleList where roleEffDate equals to UPDATED_ROLE_EFF_DATE
        defaultRoleShouldNotBeFound("roleEffDate.in=" + UPDATED_ROLE_EFF_DATE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleEffDate is not null
        defaultRoleShouldBeFound("roleEffDate.specified=true");

        // Get all the roleList where roleEffDate is null
        defaultRoleShouldNotBeFound("roleEffDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByRoleEffDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleEffDate is greater than or equal to DEFAULT_ROLE_EFF_DATE
        defaultRoleShouldBeFound("roleEffDate.greaterThanOrEqual=" + DEFAULT_ROLE_EFF_DATE);

        // Get all the roleList where roleEffDate is greater than or equal to UPDATED_ROLE_EFF_DATE
        defaultRoleShouldNotBeFound("roleEffDate.greaterThanOrEqual=" + UPDATED_ROLE_EFF_DATE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleEffDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleEffDate is less than or equal to DEFAULT_ROLE_EFF_DATE
        defaultRoleShouldBeFound("roleEffDate.lessThanOrEqual=" + DEFAULT_ROLE_EFF_DATE);

        // Get all the roleList where roleEffDate is less than or equal to SMALLER_ROLE_EFF_DATE
        defaultRoleShouldNotBeFound("roleEffDate.lessThanOrEqual=" + SMALLER_ROLE_EFF_DATE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleEffDateIsLessThanSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleEffDate is less than DEFAULT_ROLE_EFF_DATE
        defaultRoleShouldNotBeFound("roleEffDate.lessThan=" + DEFAULT_ROLE_EFF_DATE);

        // Get all the roleList where roleEffDate is less than UPDATED_ROLE_EFF_DATE
        defaultRoleShouldBeFound("roleEffDate.lessThan=" + UPDATED_ROLE_EFF_DATE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleEffDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleEffDate is greater than DEFAULT_ROLE_EFF_DATE
        defaultRoleShouldNotBeFound("roleEffDate.greaterThan=" + DEFAULT_ROLE_EFF_DATE);

        // Get all the roleList where roleEffDate is greater than SMALLER_ROLE_EFF_DATE
        defaultRoleShouldBeFound("roleEffDate.greaterThan=" + SMALLER_ROLE_EFF_DATE);
    }


    @Test
    @Transactional
    public void getAllRolesByRoleExpDateIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleExpDate equals to DEFAULT_ROLE_EXP_DATE
        defaultRoleShouldBeFound("roleExpDate.equals=" + DEFAULT_ROLE_EXP_DATE);

        // Get all the roleList where roleExpDate equals to UPDATED_ROLE_EXP_DATE
        defaultRoleShouldNotBeFound("roleExpDate.equals=" + UPDATED_ROLE_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleExpDateIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleExpDate in DEFAULT_ROLE_EXP_DATE or UPDATED_ROLE_EXP_DATE
        defaultRoleShouldBeFound("roleExpDate.in=" + DEFAULT_ROLE_EXP_DATE + "," + UPDATED_ROLE_EXP_DATE);

        // Get all the roleList where roleExpDate equals to UPDATED_ROLE_EXP_DATE
        defaultRoleShouldNotBeFound("roleExpDate.in=" + UPDATED_ROLE_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleExpDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleExpDate is not null
        defaultRoleShouldBeFound("roleExpDate.specified=true");

        // Get all the roleList where roleExpDate is null
        defaultRoleShouldNotBeFound("roleExpDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByRoleExpDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleExpDate is greater than or equal to DEFAULT_ROLE_EXP_DATE
        defaultRoleShouldBeFound("roleExpDate.greaterThanOrEqual=" + DEFAULT_ROLE_EXP_DATE);

        // Get all the roleList where roleExpDate is greater than or equal to UPDATED_ROLE_EXP_DATE
        defaultRoleShouldNotBeFound("roleExpDate.greaterThanOrEqual=" + UPDATED_ROLE_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleExpDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleExpDate is less than or equal to DEFAULT_ROLE_EXP_DATE
        defaultRoleShouldBeFound("roleExpDate.lessThanOrEqual=" + DEFAULT_ROLE_EXP_DATE);

        // Get all the roleList where roleExpDate is less than or equal to SMALLER_ROLE_EXP_DATE
        defaultRoleShouldNotBeFound("roleExpDate.lessThanOrEqual=" + SMALLER_ROLE_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleExpDateIsLessThanSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleExpDate is less than DEFAULT_ROLE_EXP_DATE
        defaultRoleShouldNotBeFound("roleExpDate.lessThan=" + DEFAULT_ROLE_EXP_DATE);

        // Get all the roleList where roleExpDate is less than UPDATED_ROLE_EXP_DATE
        defaultRoleShouldBeFound("roleExpDate.lessThan=" + UPDATED_ROLE_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleExpDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleExpDate is greater than DEFAULT_ROLE_EXP_DATE
        defaultRoleShouldNotBeFound("roleExpDate.greaterThan=" + DEFAULT_ROLE_EXP_DATE);

        // Get all the roleList where roleExpDate is greater than SMALLER_ROLE_EXP_DATE
        defaultRoleShouldBeFound("roleExpDate.greaterThan=" + SMALLER_ROLE_EXP_DATE);
    }


    @Test
    @Transactional
    public void getAllRolesByResourcesIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);
        Resource resources = ResourceResourceIT.createEntity(em);
        em.persist(resources);
        em.flush();
        role.addResources(resources);
        roleRepository.saveAndFlush(role);
        Long resourcesId = resources.getId();

        // Get all the roleList where resources equals to resourcesId
        defaultRoleShouldBeFound("resourcesId.equals=" + resourcesId);

        // Get all the roleList where resources equals to resourcesId + 1
        defaultRoleShouldNotBeFound("resourcesId.equals=" + (resourcesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoleShouldBeFound(String filter) throws Exception {
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(role.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)))
            .andExpect(jsonPath("$.[*].roleDescription").value(hasItem(DEFAULT_ROLE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].roleFlag").value(hasItem(DEFAULT_ROLE_FLAG)))
            .andExpect(jsonPath("$.[*].roleEffDate").value(hasItem(sameInstant(DEFAULT_ROLE_EFF_DATE))))
            .andExpect(jsonPath("$.[*].roleExpDate").value(hasItem(sameInstant(DEFAULT_ROLE_EXP_DATE))));

        // Check, that the count call also returns 1
        restRoleMockMvc.perform(get("/api/roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoleShouldNotBeFound(String filter) throws Exception {
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoleMockMvc.perform(get("/api/roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRole() throws Exception {
        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Update the role
        Role updatedRole = roleRepository.findById(role.getId()).get();
        // Disconnect from session so that the updates on updatedRole are not directly saved in db
        em.detach(updatedRole);
        updatedRole
            .roleName(UPDATED_ROLE_NAME)
            .roleDescription(UPDATED_ROLE_DESCRIPTION)
            .roleFlag(UPDATED_ROLE_FLAG)
            .roleEffDate(UPDATED_ROLE_EFF_DATE)
            .roleExpDate(UPDATED_ROLE_EXP_DATE);
        RoleDTO roleDTO = roleMapper.toDto(updatedRole);

        restRoleMockMvc.perform(put("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isOk());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testRole.getRoleDescription()).isEqualTo(UPDATED_ROLE_DESCRIPTION);
        assertThat(testRole.getRoleFlag()).isEqualTo(UPDATED_ROLE_FLAG);
        assertThat(testRole.getRoleEffDate()).isEqualTo(UPDATED_ROLE_EFF_DATE);
        assertThat(testRole.getRoleExpDate()).isEqualTo(UPDATED_ROLE_EXP_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRole() throws Exception {
        int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleMockMvc.perform(put("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeDelete = roleRepository.findAll().size();

        // Delete the role
        restRoleMockMvc.perform(delete("/api/roles/{id}", role.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Role.class);
        Role role1 = new Role();
        role1.setId(1L);
        Role role2 = new Role();
        role2.setId(role1.getId());
        assertThat(role1).isEqualTo(role2);
        role2.setId(2L);
        assertThat(role1).isNotEqualTo(role2);
        role1.setId(null);
        assertThat(role1).isNotEqualTo(role2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleDTO.class);
        RoleDTO roleDTO1 = new RoleDTO();
        roleDTO1.setId(1L);
        RoleDTO roleDTO2 = new RoleDTO();
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
        roleDTO2.setId(roleDTO1.getId());
        assertThat(roleDTO1).isEqualTo(roleDTO2);
        roleDTO2.setId(2L);
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
        roleDTO1.setId(null);
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(roleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(roleMapper.fromId(null)).isNull();
    }
}
