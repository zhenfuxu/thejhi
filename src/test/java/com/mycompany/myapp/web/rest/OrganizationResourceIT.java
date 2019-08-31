package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GetewayApp;
import com.mycompany.myapp.domain.Organization;
import com.mycompany.myapp.domain.Organization;
import com.mycompany.myapp.repository.OrganizationRepository;
import com.mycompany.myapp.service.OrganizationService;
import com.mycompany.myapp.service.dto.OrganizationDTO;
import com.mycompany.myapp.service.mapper.OrganizationMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.OrganizationCriteria;
import com.mycompany.myapp.service.OrganizationQueryService;

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
 * Integration tests for the {@link OrganizationResource} REST controller.
 */
@SpringBootTest(classes = GetewayApp.class)
public class OrganizationResourceIT {

    private static final String DEFAULT_ORG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORG_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_ORG_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_AREA_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORG_AREA_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_AREA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_AREA_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ORG_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_ORG_LFT = 1L;
    private static final Long UPDATED_ORG_LFT = 2L;
    private static final Long SMALLER_ORG_LFT = 1L - 1L;

    private static final Long DEFAULT_ORG_RGT = 1L;
    private static final Long UPDATED_ORG_RGT = 2L;
    private static final Long SMALLER_ORG_RGT = 1L - 1L;

    private static final Long DEFAULT_ORG_LEVEL = 1L;
    private static final Long UPDATED_ORG_LEVEL = 2L;
    private static final Long SMALLER_ORG_LEVEL = 1L - 1L;

    private static final Long DEFAULT_ORG_ORDER = 1L;
    private static final Long UPDATED_ORG_ORDER = 2L;
    private static final Long SMALLER_ORG_ORDER = 1L - 1L;

    private static final Boolean DEFAULT_LEAF = false;
    private static final Boolean UPDATED_LEAF = true;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationQueryService organizationQueryService;

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

    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganizationResource organizationResource = new OrganizationResource(organizationService, organizationQueryService);
        this.restOrganizationMockMvc = MockMvcBuilders.standaloneSetup(organizationResource)
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
    public static Organization createEntity(EntityManager em) {
        Organization organization = new Organization()
            .orgName(DEFAULT_ORG_NAME)
            .orgCode(DEFAULT_ORG_CODE)
            .orgFlag(DEFAULT_ORG_FLAG)
            .orgAreaCode(DEFAULT_ORG_AREA_CODE)
            .orgAreaName(DEFAULT_ORG_AREA_NAME)
            .orgDescription(DEFAULT_ORG_DESCRIPTION)
            .orgLft(DEFAULT_ORG_LFT)
            .orgRgt(DEFAULT_ORG_RGT)
            .orgLevel(DEFAULT_ORG_LEVEL)
            .orgOrder(DEFAULT_ORG_ORDER)
            .leaf(DEFAULT_LEAF);
        return organization;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createUpdatedEntity(EntityManager em) {
        Organization organization = new Organization()
            .orgName(UPDATED_ORG_NAME)
            .orgCode(UPDATED_ORG_CODE)
            .orgFlag(UPDATED_ORG_FLAG)
            .orgAreaCode(UPDATED_ORG_AREA_CODE)
            .orgAreaName(UPDATED_ORG_AREA_NAME)
            .orgDescription(UPDATED_ORG_DESCRIPTION)
            .orgLft(UPDATED_ORG_LFT)
            .orgRgt(UPDATED_ORG_RGT)
            .orgLevel(UPDATED_ORG_LEVEL)
            .orgOrder(UPDATED_ORG_ORDER)
            .leaf(UPDATED_LEAF);
        return organization;
    }

    @BeforeEach
    public void initTest() {
        organization = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);
        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testOrganization.getOrgCode()).isEqualTo(DEFAULT_ORG_CODE);
        assertThat(testOrganization.getOrgFlag()).isEqualTo(DEFAULT_ORG_FLAG);
        assertThat(testOrganization.getOrgAreaCode()).isEqualTo(DEFAULT_ORG_AREA_CODE);
        assertThat(testOrganization.getOrgAreaName()).isEqualTo(DEFAULT_ORG_AREA_NAME);
        assertThat(testOrganization.getOrgDescription()).isEqualTo(DEFAULT_ORG_DESCRIPTION);
        assertThat(testOrganization.getOrgLft()).isEqualTo(DEFAULT_ORG_LFT);
        assertThat(testOrganization.getOrgRgt()).isEqualTo(DEFAULT_ORG_RGT);
        assertThat(testOrganization.getOrgLevel()).isEqualTo(DEFAULT_ORG_LEVEL);
        assertThat(testOrganization.getOrgOrder()).isEqualTo(DEFAULT_ORG_ORDER);
        assertThat(testOrganization.isLeaf()).isEqualTo(DEFAULT_LEAF);
    }

    @Test
    @Transactional
    public void createOrganizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization with an existing ID
        organization.setId(1L);
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList
        restOrganizationMockMvc.perform(get("/api/organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME.toString())))
            .andExpect(jsonPath("$.[*].orgCode").value(hasItem(DEFAULT_ORG_CODE.toString())))
            .andExpect(jsonPath("$.[*].orgFlag").value(hasItem(DEFAULT_ORG_FLAG.toString())))
            .andExpect(jsonPath("$.[*].orgAreaCode").value(hasItem(DEFAULT_ORG_AREA_CODE.toString())))
            .andExpect(jsonPath("$.[*].orgAreaName").value(hasItem(DEFAULT_ORG_AREA_NAME.toString())))
            .andExpect(jsonPath("$.[*].orgDescription").value(hasItem(DEFAULT_ORG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].orgLft").value(hasItem(DEFAULT_ORG_LFT.intValue())))
            .andExpect(jsonPath("$.[*].orgRgt").value(hasItem(DEFAULT_ORG_RGT.intValue())))
            .andExpect(jsonPath("$.[*].orgLevel").value(hasItem(DEFAULT_ORG_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].orgOrder").value(hasItem(DEFAULT_ORG_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId().intValue()))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME.toString()))
            .andExpect(jsonPath("$.orgCode").value(DEFAULT_ORG_CODE.toString()))
            .andExpect(jsonPath("$.orgFlag").value(DEFAULT_ORG_FLAG.toString()))
            .andExpect(jsonPath("$.orgAreaCode").value(DEFAULT_ORG_AREA_CODE.toString()))
            .andExpect(jsonPath("$.orgAreaName").value(DEFAULT_ORG_AREA_NAME.toString()))
            .andExpect(jsonPath("$.orgDescription").value(DEFAULT_ORG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.orgLft").value(DEFAULT_ORG_LFT.intValue()))
            .andExpect(jsonPath("$.orgRgt").value(DEFAULT_ORG_RGT.intValue()))
            .andExpect(jsonPath("$.orgLevel").value(DEFAULT_ORG_LEVEL.intValue()))
            .andExpect(jsonPath("$.orgOrder").value(DEFAULT_ORG_ORDER.intValue()))
            .andExpect(jsonPath("$.leaf").value(DEFAULT_LEAF.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgNameIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgName equals to DEFAULT_ORG_NAME
        defaultOrganizationShouldBeFound("orgName.equals=" + DEFAULT_ORG_NAME);

        // Get all the organizationList where orgName equals to UPDATED_ORG_NAME
        defaultOrganizationShouldNotBeFound("orgName.equals=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgNameIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgName in DEFAULT_ORG_NAME or UPDATED_ORG_NAME
        defaultOrganizationShouldBeFound("orgName.in=" + DEFAULT_ORG_NAME + "," + UPDATED_ORG_NAME);

        // Get all the organizationList where orgName equals to UPDATED_ORG_NAME
        defaultOrganizationShouldNotBeFound("orgName.in=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgName is not null
        defaultOrganizationShouldBeFound("orgName.specified=true");

        // Get all the organizationList where orgName is null
        defaultOrganizationShouldNotBeFound("orgName.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgCode equals to DEFAULT_ORG_CODE
        defaultOrganizationShouldBeFound("orgCode.equals=" + DEFAULT_ORG_CODE);

        // Get all the organizationList where orgCode equals to UPDATED_ORG_CODE
        defaultOrganizationShouldNotBeFound("orgCode.equals=" + UPDATED_ORG_CODE);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgCodeIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgCode in DEFAULT_ORG_CODE or UPDATED_ORG_CODE
        defaultOrganizationShouldBeFound("orgCode.in=" + DEFAULT_ORG_CODE + "," + UPDATED_ORG_CODE);

        // Get all the organizationList where orgCode equals to UPDATED_ORG_CODE
        defaultOrganizationShouldNotBeFound("orgCode.in=" + UPDATED_ORG_CODE);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgCode is not null
        defaultOrganizationShouldBeFound("orgCode.specified=true");

        // Get all the organizationList where orgCode is null
        defaultOrganizationShouldNotBeFound("orgCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgFlag equals to DEFAULT_ORG_FLAG
        defaultOrganizationShouldBeFound("orgFlag.equals=" + DEFAULT_ORG_FLAG);

        // Get all the organizationList where orgFlag equals to UPDATED_ORG_FLAG
        defaultOrganizationShouldNotBeFound("orgFlag.equals=" + UPDATED_ORG_FLAG);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgFlagIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgFlag in DEFAULT_ORG_FLAG or UPDATED_ORG_FLAG
        defaultOrganizationShouldBeFound("orgFlag.in=" + DEFAULT_ORG_FLAG + "," + UPDATED_ORG_FLAG);

        // Get all the organizationList where orgFlag equals to UPDATED_ORG_FLAG
        defaultOrganizationShouldNotBeFound("orgFlag.in=" + UPDATED_ORG_FLAG);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgFlag is not null
        defaultOrganizationShouldBeFound("orgFlag.specified=true");

        // Get all the organizationList where orgFlag is null
        defaultOrganizationShouldNotBeFound("orgFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgAreaCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgAreaCode equals to DEFAULT_ORG_AREA_CODE
        defaultOrganizationShouldBeFound("orgAreaCode.equals=" + DEFAULT_ORG_AREA_CODE);

        // Get all the organizationList where orgAreaCode equals to UPDATED_ORG_AREA_CODE
        defaultOrganizationShouldNotBeFound("orgAreaCode.equals=" + UPDATED_ORG_AREA_CODE);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgAreaCodeIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgAreaCode in DEFAULT_ORG_AREA_CODE or UPDATED_ORG_AREA_CODE
        defaultOrganizationShouldBeFound("orgAreaCode.in=" + DEFAULT_ORG_AREA_CODE + "," + UPDATED_ORG_AREA_CODE);

        // Get all the organizationList where orgAreaCode equals to UPDATED_ORG_AREA_CODE
        defaultOrganizationShouldNotBeFound("orgAreaCode.in=" + UPDATED_ORG_AREA_CODE);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgAreaCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgAreaCode is not null
        defaultOrganizationShouldBeFound("orgAreaCode.specified=true");

        // Get all the organizationList where orgAreaCode is null
        defaultOrganizationShouldNotBeFound("orgAreaCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgAreaNameIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgAreaName equals to DEFAULT_ORG_AREA_NAME
        defaultOrganizationShouldBeFound("orgAreaName.equals=" + DEFAULT_ORG_AREA_NAME);

        // Get all the organizationList where orgAreaName equals to UPDATED_ORG_AREA_NAME
        defaultOrganizationShouldNotBeFound("orgAreaName.equals=" + UPDATED_ORG_AREA_NAME);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgAreaNameIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgAreaName in DEFAULT_ORG_AREA_NAME or UPDATED_ORG_AREA_NAME
        defaultOrganizationShouldBeFound("orgAreaName.in=" + DEFAULT_ORG_AREA_NAME + "," + UPDATED_ORG_AREA_NAME);

        // Get all the organizationList where orgAreaName equals to UPDATED_ORG_AREA_NAME
        defaultOrganizationShouldNotBeFound("orgAreaName.in=" + UPDATED_ORG_AREA_NAME);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgAreaNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgAreaName is not null
        defaultOrganizationShouldBeFound("orgAreaName.specified=true");

        // Get all the organizationList where orgAreaName is null
        defaultOrganizationShouldNotBeFound("orgAreaName.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgDescription equals to DEFAULT_ORG_DESCRIPTION
        defaultOrganizationShouldBeFound("orgDescription.equals=" + DEFAULT_ORG_DESCRIPTION);

        // Get all the organizationList where orgDescription equals to UPDATED_ORG_DESCRIPTION
        defaultOrganizationShouldNotBeFound("orgDescription.equals=" + UPDATED_ORG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgDescription in DEFAULT_ORG_DESCRIPTION or UPDATED_ORG_DESCRIPTION
        defaultOrganizationShouldBeFound("orgDescription.in=" + DEFAULT_ORG_DESCRIPTION + "," + UPDATED_ORG_DESCRIPTION);

        // Get all the organizationList where orgDescription equals to UPDATED_ORG_DESCRIPTION
        defaultOrganizationShouldNotBeFound("orgDescription.in=" + UPDATED_ORG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgDescription is not null
        defaultOrganizationShouldBeFound("orgDescription.specified=true");

        // Get all the organizationList where orgDescription is null
        defaultOrganizationShouldNotBeFound("orgDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLftIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLft equals to DEFAULT_ORG_LFT
        defaultOrganizationShouldBeFound("orgLft.equals=" + DEFAULT_ORG_LFT);

        // Get all the organizationList where orgLft equals to UPDATED_ORG_LFT
        defaultOrganizationShouldNotBeFound("orgLft.equals=" + UPDATED_ORG_LFT);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLftIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLft in DEFAULT_ORG_LFT or UPDATED_ORG_LFT
        defaultOrganizationShouldBeFound("orgLft.in=" + DEFAULT_ORG_LFT + "," + UPDATED_ORG_LFT);

        // Get all the organizationList where orgLft equals to UPDATED_ORG_LFT
        defaultOrganizationShouldNotBeFound("orgLft.in=" + UPDATED_ORG_LFT);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLftIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLft is not null
        defaultOrganizationShouldBeFound("orgLft.specified=true");

        // Get all the organizationList where orgLft is null
        defaultOrganizationShouldNotBeFound("orgLft.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLftIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLft is greater than or equal to DEFAULT_ORG_LFT
        defaultOrganizationShouldBeFound("orgLft.greaterThanOrEqual=" + DEFAULT_ORG_LFT);

        // Get all the organizationList where orgLft is greater than or equal to UPDATED_ORG_LFT
        defaultOrganizationShouldNotBeFound("orgLft.greaterThanOrEqual=" + UPDATED_ORG_LFT);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLftIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLft is less than or equal to DEFAULT_ORG_LFT
        defaultOrganizationShouldBeFound("orgLft.lessThanOrEqual=" + DEFAULT_ORG_LFT);

        // Get all the organizationList where orgLft is less than or equal to SMALLER_ORG_LFT
        defaultOrganizationShouldNotBeFound("orgLft.lessThanOrEqual=" + SMALLER_ORG_LFT);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLftIsLessThanSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLft is less than DEFAULT_ORG_LFT
        defaultOrganizationShouldNotBeFound("orgLft.lessThan=" + DEFAULT_ORG_LFT);

        // Get all the organizationList where orgLft is less than UPDATED_ORG_LFT
        defaultOrganizationShouldBeFound("orgLft.lessThan=" + UPDATED_ORG_LFT);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLftIsGreaterThanSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLft is greater than DEFAULT_ORG_LFT
        defaultOrganizationShouldNotBeFound("orgLft.greaterThan=" + DEFAULT_ORG_LFT);

        // Get all the organizationList where orgLft is greater than SMALLER_ORG_LFT
        defaultOrganizationShouldBeFound("orgLft.greaterThan=" + SMALLER_ORG_LFT);
    }


    @Test
    @Transactional
    public void getAllOrganizationsByOrgRgtIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgRgt equals to DEFAULT_ORG_RGT
        defaultOrganizationShouldBeFound("orgRgt.equals=" + DEFAULT_ORG_RGT);

        // Get all the organizationList where orgRgt equals to UPDATED_ORG_RGT
        defaultOrganizationShouldNotBeFound("orgRgt.equals=" + UPDATED_ORG_RGT);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgRgtIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgRgt in DEFAULT_ORG_RGT or UPDATED_ORG_RGT
        defaultOrganizationShouldBeFound("orgRgt.in=" + DEFAULT_ORG_RGT + "," + UPDATED_ORG_RGT);

        // Get all the organizationList where orgRgt equals to UPDATED_ORG_RGT
        defaultOrganizationShouldNotBeFound("orgRgt.in=" + UPDATED_ORG_RGT);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgRgtIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgRgt is not null
        defaultOrganizationShouldBeFound("orgRgt.specified=true");

        // Get all the organizationList where orgRgt is null
        defaultOrganizationShouldNotBeFound("orgRgt.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgRgtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgRgt is greater than or equal to DEFAULT_ORG_RGT
        defaultOrganizationShouldBeFound("orgRgt.greaterThanOrEqual=" + DEFAULT_ORG_RGT);

        // Get all the organizationList where orgRgt is greater than or equal to UPDATED_ORG_RGT
        defaultOrganizationShouldNotBeFound("orgRgt.greaterThanOrEqual=" + UPDATED_ORG_RGT);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgRgtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgRgt is less than or equal to DEFAULT_ORG_RGT
        defaultOrganizationShouldBeFound("orgRgt.lessThanOrEqual=" + DEFAULT_ORG_RGT);

        // Get all the organizationList where orgRgt is less than or equal to SMALLER_ORG_RGT
        defaultOrganizationShouldNotBeFound("orgRgt.lessThanOrEqual=" + SMALLER_ORG_RGT);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgRgtIsLessThanSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgRgt is less than DEFAULT_ORG_RGT
        defaultOrganizationShouldNotBeFound("orgRgt.lessThan=" + DEFAULT_ORG_RGT);

        // Get all the organizationList where orgRgt is less than UPDATED_ORG_RGT
        defaultOrganizationShouldBeFound("orgRgt.lessThan=" + UPDATED_ORG_RGT);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgRgtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgRgt is greater than DEFAULT_ORG_RGT
        defaultOrganizationShouldNotBeFound("orgRgt.greaterThan=" + DEFAULT_ORG_RGT);

        // Get all the organizationList where orgRgt is greater than SMALLER_ORG_RGT
        defaultOrganizationShouldBeFound("orgRgt.greaterThan=" + SMALLER_ORG_RGT);
    }


    @Test
    @Transactional
    public void getAllOrganizationsByOrgLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLevel equals to DEFAULT_ORG_LEVEL
        defaultOrganizationShouldBeFound("orgLevel.equals=" + DEFAULT_ORG_LEVEL);

        // Get all the organizationList where orgLevel equals to UPDATED_ORG_LEVEL
        defaultOrganizationShouldNotBeFound("orgLevel.equals=" + UPDATED_ORG_LEVEL);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLevelIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLevel in DEFAULT_ORG_LEVEL or UPDATED_ORG_LEVEL
        defaultOrganizationShouldBeFound("orgLevel.in=" + DEFAULT_ORG_LEVEL + "," + UPDATED_ORG_LEVEL);

        // Get all the organizationList where orgLevel equals to UPDATED_ORG_LEVEL
        defaultOrganizationShouldNotBeFound("orgLevel.in=" + UPDATED_ORG_LEVEL);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLevel is not null
        defaultOrganizationShouldBeFound("orgLevel.specified=true");

        // Get all the organizationList where orgLevel is null
        defaultOrganizationShouldNotBeFound("orgLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLevel is greater than or equal to DEFAULT_ORG_LEVEL
        defaultOrganizationShouldBeFound("orgLevel.greaterThanOrEqual=" + DEFAULT_ORG_LEVEL);

        // Get all the organizationList where orgLevel is greater than or equal to UPDATED_ORG_LEVEL
        defaultOrganizationShouldNotBeFound("orgLevel.greaterThanOrEqual=" + UPDATED_ORG_LEVEL);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLevel is less than or equal to DEFAULT_ORG_LEVEL
        defaultOrganizationShouldBeFound("orgLevel.lessThanOrEqual=" + DEFAULT_ORG_LEVEL);

        // Get all the organizationList where orgLevel is less than or equal to SMALLER_ORG_LEVEL
        defaultOrganizationShouldNotBeFound("orgLevel.lessThanOrEqual=" + SMALLER_ORG_LEVEL);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLevel is less than DEFAULT_ORG_LEVEL
        defaultOrganizationShouldNotBeFound("orgLevel.lessThan=" + DEFAULT_ORG_LEVEL);

        // Get all the organizationList where orgLevel is less than UPDATED_ORG_LEVEL
        defaultOrganizationShouldBeFound("orgLevel.lessThan=" + UPDATED_ORG_LEVEL);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgLevel is greater than DEFAULT_ORG_LEVEL
        defaultOrganizationShouldNotBeFound("orgLevel.greaterThan=" + DEFAULT_ORG_LEVEL);

        // Get all the organizationList where orgLevel is greater than SMALLER_ORG_LEVEL
        defaultOrganizationShouldBeFound("orgLevel.greaterThan=" + SMALLER_ORG_LEVEL);
    }


    @Test
    @Transactional
    public void getAllOrganizationsByOrgOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgOrder equals to DEFAULT_ORG_ORDER
        defaultOrganizationShouldBeFound("orgOrder.equals=" + DEFAULT_ORG_ORDER);

        // Get all the organizationList where orgOrder equals to UPDATED_ORG_ORDER
        defaultOrganizationShouldNotBeFound("orgOrder.equals=" + UPDATED_ORG_ORDER);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgOrderIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgOrder in DEFAULT_ORG_ORDER or UPDATED_ORG_ORDER
        defaultOrganizationShouldBeFound("orgOrder.in=" + DEFAULT_ORG_ORDER + "," + UPDATED_ORG_ORDER);

        // Get all the organizationList where orgOrder equals to UPDATED_ORG_ORDER
        defaultOrganizationShouldNotBeFound("orgOrder.in=" + UPDATED_ORG_ORDER);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgOrder is not null
        defaultOrganizationShouldBeFound("orgOrder.specified=true");

        // Get all the organizationList where orgOrder is null
        defaultOrganizationShouldNotBeFound("orgOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgOrder is greater than or equal to DEFAULT_ORG_ORDER
        defaultOrganizationShouldBeFound("orgOrder.greaterThanOrEqual=" + DEFAULT_ORG_ORDER);

        // Get all the organizationList where orgOrder is greater than or equal to UPDATED_ORG_ORDER
        defaultOrganizationShouldNotBeFound("orgOrder.greaterThanOrEqual=" + UPDATED_ORG_ORDER);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgOrder is less than or equal to DEFAULT_ORG_ORDER
        defaultOrganizationShouldBeFound("orgOrder.lessThanOrEqual=" + DEFAULT_ORG_ORDER);

        // Get all the organizationList where orgOrder is less than or equal to SMALLER_ORG_ORDER
        defaultOrganizationShouldNotBeFound("orgOrder.lessThanOrEqual=" + SMALLER_ORG_ORDER);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgOrder is less than DEFAULT_ORG_ORDER
        defaultOrganizationShouldNotBeFound("orgOrder.lessThan=" + DEFAULT_ORG_ORDER);

        // Get all the organizationList where orgOrder is less than UPDATED_ORG_ORDER
        defaultOrganizationShouldBeFound("orgOrder.lessThan=" + UPDATED_ORG_ORDER);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByOrgOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgOrder is greater than DEFAULT_ORG_ORDER
        defaultOrganizationShouldNotBeFound("orgOrder.greaterThan=" + DEFAULT_ORG_ORDER);

        // Get all the organizationList where orgOrder is greater than SMALLER_ORG_ORDER
        defaultOrganizationShouldBeFound("orgOrder.greaterThan=" + SMALLER_ORG_ORDER);
    }


    @Test
    @Transactional
    public void getAllOrganizationsByLeafIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where leaf equals to DEFAULT_LEAF
        defaultOrganizationShouldBeFound("leaf.equals=" + DEFAULT_LEAF);

        // Get all the organizationList where leaf equals to UPDATED_LEAF
        defaultOrganizationShouldNotBeFound("leaf.equals=" + UPDATED_LEAF);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByLeafIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where leaf in DEFAULT_LEAF or UPDATED_LEAF
        defaultOrganizationShouldBeFound("leaf.in=" + DEFAULT_LEAF + "," + UPDATED_LEAF);

        // Get all the organizationList where leaf equals to UPDATED_LEAF
        defaultOrganizationShouldNotBeFound("leaf.in=" + UPDATED_LEAF);
    }

    @Test
    @Transactional
    public void getAllOrganizationsByLeafIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where leaf is not null
        defaultOrganizationShouldBeFound("leaf.specified=true");

        // Get all the organizationList where leaf is null
        defaultOrganizationShouldNotBeFound("leaf.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrganizationsByUpperIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);
        Organization upper = OrganizationResourceIT.createEntity(em);
        em.persist(upper);
        em.flush();
        organization.setUpper(upper);
        organizationRepository.saveAndFlush(organization);
        Long upperId = upper.getId();

        // Get all the organizationList where upper equals to upperId
        defaultOrganizationShouldBeFound("upperId.equals=" + upperId);

        // Get all the organizationList where upper equals to upperId + 1
        defaultOrganizationShouldNotBeFound("upperId.equals=" + (upperId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrganizationShouldBeFound(String filter) throws Exception {
        restOrganizationMockMvc.perform(get("/api/organizations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME)))
            .andExpect(jsonPath("$.[*].orgCode").value(hasItem(DEFAULT_ORG_CODE)))
            .andExpect(jsonPath("$.[*].orgFlag").value(hasItem(DEFAULT_ORG_FLAG)))
            .andExpect(jsonPath("$.[*].orgAreaCode").value(hasItem(DEFAULT_ORG_AREA_CODE)))
            .andExpect(jsonPath("$.[*].orgAreaName").value(hasItem(DEFAULT_ORG_AREA_NAME)))
            .andExpect(jsonPath("$.[*].orgDescription").value(hasItem(DEFAULT_ORG_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].orgLft").value(hasItem(DEFAULT_ORG_LFT.intValue())))
            .andExpect(jsonPath("$.[*].orgRgt").value(hasItem(DEFAULT_ORG_RGT.intValue())))
            .andExpect(jsonPath("$.[*].orgLevel").value(hasItem(DEFAULT_ORG_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].orgOrder").value(hasItem(DEFAULT_ORG_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())));

        // Check, that the count call also returns 1
        restOrganizationMockMvc.perform(get("/api/organizations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrganizationShouldNotBeFound(String filter) throws Exception {
        restOrganizationMockMvc.perform(get("/api/organizations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrganizationMockMvc.perform(get("/api/organizations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findById(organization.getId()).get();
        // Disconnect from session so that the updates on updatedOrganization are not directly saved in db
        em.detach(updatedOrganization);
        updatedOrganization
            .orgName(UPDATED_ORG_NAME)
            .orgCode(UPDATED_ORG_CODE)
            .orgFlag(UPDATED_ORG_FLAG)
            .orgAreaCode(UPDATED_ORG_AREA_CODE)
            .orgAreaName(UPDATED_ORG_AREA_NAME)
            .orgDescription(UPDATED_ORG_DESCRIPTION)
            .orgLft(UPDATED_ORG_LFT)
            .orgRgt(UPDATED_ORG_RGT)
            .orgLevel(UPDATED_ORG_LEVEL)
            .orgOrder(UPDATED_ORG_ORDER)
            .leaf(UPDATED_LEAF);
        OrganizationDTO organizationDTO = organizationMapper.toDto(updatedOrganization);

        restOrganizationMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testOrganization.getOrgCode()).isEqualTo(UPDATED_ORG_CODE);
        assertThat(testOrganization.getOrgFlag()).isEqualTo(UPDATED_ORG_FLAG);
        assertThat(testOrganization.getOrgAreaCode()).isEqualTo(UPDATED_ORG_AREA_CODE);
        assertThat(testOrganization.getOrgAreaName()).isEqualTo(UPDATED_ORG_AREA_NAME);
        assertThat(testOrganization.getOrgDescription()).isEqualTo(UPDATED_ORG_DESCRIPTION);
        assertThat(testOrganization.getOrgLft()).isEqualTo(UPDATED_ORG_LFT);
        assertThat(testOrganization.getOrgRgt()).isEqualTo(UPDATED_ORG_RGT);
        assertThat(testOrganization.getOrgLevel()).isEqualTo(UPDATED_ORG_LEVEL);
        assertThat(testOrganization.getOrgOrder()).isEqualTo(UPDATED_ORG_ORDER);
        assertThat(testOrganization.isLeaf()).isEqualTo(UPDATED_LEAF);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Delete the organization
        restOrganizationMockMvc.perform(delete("/api/organizations/{id}", organization.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organization.class);
        Organization organization1 = new Organization();
        organization1.setId(1L);
        Organization organization2 = new Organization();
        organization2.setId(organization1.getId());
        assertThat(organization1).isEqualTo(organization2);
        organization2.setId(2L);
        assertThat(organization1).isNotEqualTo(organization2);
        organization1.setId(null);
        assertThat(organization1).isNotEqualTo(organization2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationDTO.class);
        OrganizationDTO organizationDTO1 = new OrganizationDTO();
        organizationDTO1.setId(1L);
        OrganizationDTO organizationDTO2 = new OrganizationDTO();
        assertThat(organizationDTO1).isNotEqualTo(organizationDTO2);
        organizationDTO2.setId(organizationDTO1.getId());
        assertThat(organizationDTO1).isEqualTo(organizationDTO2);
        organizationDTO2.setId(2L);
        assertThat(organizationDTO1).isNotEqualTo(organizationDTO2);
        organizationDTO1.setId(null);
        assertThat(organizationDTO1).isNotEqualTo(organizationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(organizationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(organizationMapper.fromId(null)).isNull();
    }
}
