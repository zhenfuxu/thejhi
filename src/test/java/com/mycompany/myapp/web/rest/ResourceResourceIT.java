package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GetewayApp;
import com.mycompany.myapp.domain.Resource;
import com.mycompany.myapp.domain.Resource;
import com.mycompany.myapp.repository.ResourceRepository;
import com.mycompany.myapp.service.ResourceService;
import com.mycompany.myapp.service.dto.ResourceDTO;
import com.mycompany.myapp.service.mapper.ResourceMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.ResourceCriteria;
import com.mycompany.myapp.service.ResourceQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ResourceResource} REST controller.
 */
@SpringBootTest(classes = GetewayApp.class)
public class ResourceResourceIT {

    private static final String DEFAULT_RES_ROUTER_LINK = "AAAAAAAAAA";
    private static final String UPDATED_RES_ROUTER_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_RES_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_RES_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RES_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_RES_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_RES_OPERATE = "AAAAAAAAAA";
    private static final String UPDATED_RES_OPERATE = "BBBBBBBBBB";

    private static final String DEFAULT_RES_HREF = "AAAAAAAAAA";
    private static final String UPDATED_RES_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_RES_SRC = "AAAAAAAAAA";
    private static final String UPDATED_RES_SRC = "BBBBBBBBBB";

    private static final String DEFAULT_RES_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_RES_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_RES_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_RES_CLASS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RES_EFF_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RES_EFF_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_RES_EFF_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_RES_EXP_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RES_EXP_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_RES_EXP_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_RES_LFT = 1L;
    private static final Long UPDATED_RES_LFT = 2L;
    private static final Long SMALLER_RES_LFT = 1L - 1L;

    private static final Long DEFAULT_RES_RGT = 1L;
    private static final Long UPDATED_RES_RGT = 2L;
    private static final Long SMALLER_RES_RGT = 1L - 1L;

    private static final Long DEFAULT_RES_LEVEL = 1L;
    private static final Long UPDATED_RES_LEVEL = 2L;
    private static final Long SMALLER_RES_LEVEL = 1L - 1L;

    private static final Long DEFAULT_RES_ORDER = 1L;
    private static final Long UPDATED_RES_ORDER = 2L;
    private static final Long SMALLER_RES_ORDER = 1L - 1L;

    private static final Boolean DEFAULT_LEAF = false;
    private static final Boolean UPDATED_LEAF = true;

    private static final Boolean DEFAULT_RES_DISABLED = false;
    private static final Boolean UPDATED_RES_DISABLED = true;

    private static final Boolean DEFAULT_RES_CHECKED = false;
    private static final Boolean UPDATED_RES_CHECKED = true;

    private static final Boolean DEFAULT_RES_EXPANDED = false;
    private static final Boolean UPDATED_RES_EXPANDED = true;

    private static final Boolean DEFAULT_RES_SELECTED = false;
    private static final Boolean UPDATED_RES_SELECTED = true;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceQueryService resourceQueryService;

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

    private MockMvc restResourceMockMvc;

    private Resource resource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResourceResource resourceResource = new ResourceResource(resourceService, resourceQueryService);
        this.restResourceMockMvc = MockMvcBuilders.standaloneSetup(resourceResource)
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
    public static Resource createEntity(EntityManager em) {
        Resource resource = new Resource()
            .resRouterLink(DEFAULT_RES_ROUTER_LINK)
            .resDescription(DEFAULT_RES_DESCRIPTION)
            .resFlag(DEFAULT_RES_FLAG)
            .resOperate(DEFAULT_RES_OPERATE)
            .resHref(DEFAULT_RES_HREF)
            .resSrc(DEFAULT_RES_SRC)
            .resText(DEFAULT_RES_TEXT)
            .resClass(DEFAULT_RES_CLASS)
            .resEffDate(DEFAULT_RES_EFF_DATE)
            .resExpDate(DEFAULT_RES_EXP_DATE)
            .resLft(DEFAULT_RES_LFT)
            .resRgt(DEFAULT_RES_RGT)
            .resLevel(DEFAULT_RES_LEVEL)
            .resOrder(DEFAULT_RES_ORDER)
            .leaf(DEFAULT_LEAF)
            .resDisabled(DEFAULT_RES_DISABLED)
            .resChecked(DEFAULT_RES_CHECKED)
            .resExpanded(DEFAULT_RES_EXPANDED)
            .resSelected(DEFAULT_RES_SELECTED);
        return resource;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resource createUpdatedEntity(EntityManager em) {
        Resource resource = new Resource()
            .resRouterLink(UPDATED_RES_ROUTER_LINK)
            .resDescription(UPDATED_RES_DESCRIPTION)
            .resFlag(UPDATED_RES_FLAG)
            .resOperate(UPDATED_RES_OPERATE)
            .resHref(UPDATED_RES_HREF)
            .resSrc(UPDATED_RES_SRC)
            .resText(UPDATED_RES_TEXT)
            .resClass(UPDATED_RES_CLASS)
            .resEffDate(UPDATED_RES_EFF_DATE)
            .resExpDate(UPDATED_RES_EXP_DATE)
            .resLft(UPDATED_RES_LFT)
            .resRgt(UPDATED_RES_RGT)
            .resLevel(UPDATED_RES_LEVEL)
            .resOrder(UPDATED_RES_ORDER)
            .leaf(UPDATED_LEAF)
            .resDisabled(UPDATED_RES_DISABLED)
            .resChecked(UPDATED_RES_CHECKED)
            .resExpanded(UPDATED_RES_EXPANDED)
            .resSelected(UPDATED_RES_SELECTED);
        return resource;
    }

    @BeforeEach
    public void initTest() {
        resource = createEntity(em);
    }

    @Test
    @Transactional
    public void createResource() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // Create the Resource
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);
        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceDTO)))
            .andExpect(status().isCreated());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeCreate + 1);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getResRouterLink()).isEqualTo(DEFAULT_RES_ROUTER_LINK);
        assertThat(testResource.getResDescription()).isEqualTo(DEFAULT_RES_DESCRIPTION);
        assertThat(testResource.getResFlag()).isEqualTo(DEFAULT_RES_FLAG);
        assertThat(testResource.getResOperate()).isEqualTo(DEFAULT_RES_OPERATE);
        assertThat(testResource.getResHref()).isEqualTo(DEFAULT_RES_HREF);
        assertThat(testResource.getResSrc()).isEqualTo(DEFAULT_RES_SRC);
        assertThat(testResource.getResText()).isEqualTo(DEFAULT_RES_TEXT);
        assertThat(testResource.getResClass()).isEqualTo(DEFAULT_RES_CLASS);
        assertThat(testResource.getResEffDate()).isEqualTo(DEFAULT_RES_EFF_DATE);
        assertThat(testResource.getResExpDate()).isEqualTo(DEFAULT_RES_EXP_DATE);
        assertThat(testResource.getResLft()).isEqualTo(DEFAULT_RES_LFT);
        assertThat(testResource.getResRgt()).isEqualTo(DEFAULT_RES_RGT);
        assertThat(testResource.getResLevel()).isEqualTo(DEFAULT_RES_LEVEL);
        assertThat(testResource.getResOrder()).isEqualTo(DEFAULT_RES_ORDER);
        assertThat(testResource.isLeaf()).isEqualTo(DEFAULT_LEAF);
        assertThat(testResource.isResDisabled()).isEqualTo(DEFAULT_RES_DISABLED);
        assertThat(testResource.isResChecked()).isEqualTo(DEFAULT_RES_CHECKED);
        assertThat(testResource.isResExpanded()).isEqualTo(DEFAULT_RES_EXPANDED);
        assertThat(testResource.isResSelected()).isEqualTo(DEFAULT_RES_SELECTED);
    }

    @Test
    @Transactional
    public void createResourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // Create the Resource with an existing ID
        resource.setId(1L);
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllResources() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList
        restResourceMockMvc.perform(get("/api/resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resource.getId().intValue())))
            .andExpect(jsonPath("$.[*].resRouterLink").value(hasItem(DEFAULT_RES_ROUTER_LINK.toString())))
            .andExpect(jsonPath("$.[*].resDescription").value(hasItem(DEFAULT_RES_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].resFlag").value(hasItem(DEFAULT_RES_FLAG.toString())))
            .andExpect(jsonPath("$.[*].resOperate").value(hasItem(DEFAULT_RES_OPERATE.toString())))
            .andExpect(jsonPath("$.[*].resHref").value(hasItem(DEFAULT_RES_HREF.toString())))
            .andExpect(jsonPath("$.[*].resSrc").value(hasItem(DEFAULT_RES_SRC.toString())))
            .andExpect(jsonPath("$.[*].resText").value(hasItem(DEFAULT_RES_TEXT.toString())))
            .andExpect(jsonPath("$.[*].resClass").value(hasItem(DEFAULT_RES_CLASS.toString())))
            .andExpect(jsonPath("$.[*].resEffDate").value(hasItem(sameInstant(DEFAULT_RES_EFF_DATE))))
            .andExpect(jsonPath("$.[*].resExpDate").value(hasItem(sameInstant(DEFAULT_RES_EXP_DATE))))
            .andExpect(jsonPath("$.[*].resLft").value(hasItem(DEFAULT_RES_LFT.intValue())))
            .andExpect(jsonPath("$.[*].resRgt").value(hasItem(DEFAULT_RES_RGT.intValue())))
            .andExpect(jsonPath("$.[*].resLevel").value(hasItem(DEFAULT_RES_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].resOrder").value(hasItem(DEFAULT_RES_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())))
            .andExpect(jsonPath("$.[*].resDisabled").value(hasItem(DEFAULT_RES_DISABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].resChecked").value(hasItem(DEFAULT_RES_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].resExpanded").value(hasItem(DEFAULT_RES_EXPANDED.booleanValue())))
            .andExpect(jsonPath("$.[*].resSelected").value(hasItem(DEFAULT_RES_SELECTED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get the resource
        restResourceMockMvc.perform(get("/api/resources/{id}", resource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resource.getId().intValue()))
            .andExpect(jsonPath("$.resRouterLink").value(DEFAULT_RES_ROUTER_LINK.toString()))
            .andExpect(jsonPath("$.resDescription").value(DEFAULT_RES_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.resFlag").value(DEFAULT_RES_FLAG.toString()))
            .andExpect(jsonPath("$.resOperate").value(DEFAULT_RES_OPERATE.toString()))
            .andExpect(jsonPath("$.resHref").value(DEFAULT_RES_HREF.toString()))
            .andExpect(jsonPath("$.resSrc").value(DEFAULT_RES_SRC.toString()))
            .andExpect(jsonPath("$.resText").value(DEFAULT_RES_TEXT.toString()))
            .andExpect(jsonPath("$.resClass").value(DEFAULT_RES_CLASS.toString()))
            .andExpect(jsonPath("$.resEffDate").value(sameInstant(DEFAULT_RES_EFF_DATE)))
            .andExpect(jsonPath("$.resExpDate").value(sameInstant(DEFAULT_RES_EXP_DATE)))
            .andExpect(jsonPath("$.resLft").value(DEFAULT_RES_LFT.intValue()))
            .andExpect(jsonPath("$.resRgt").value(DEFAULT_RES_RGT.intValue()))
            .andExpect(jsonPath("$.resLevel").value(DEFAULT_RES_LEVEL.intValue()))
            .andExpect(jsonPath("$.resOrder").value(DEFAULT_RES_ORDER.intValue()))
            .andExpect(jsonPath("$.leaf").value(DEFAULT_LEAF.booleanValue()))
            .andExpect(jsonPath("$.resDisabled").value(DEFAULT_RES_DISABLED.booleanValue()))
            .andExpect(jsonPath("$.resChecked").value(DEFAULT_RES_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.resExpanded").value(DEFAULT_RES_EXPANDED.booleanValue()))
            .andExpect(jsonPath("$.resSelected").value(DEFAULT_RES_SELECTED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllResourcesByResRouterLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resRouterLink equals to DEFAULT_RES_ROUTER_LINK
        defaultResourceShouldBeFound("resRouterLink.equals=" + DEFAULT_RES_ROUTER_LINK);

        // Get all the resourceList where resRouterLink equals to UPDATED_RES_ROUTER_LINK
        defaultResourceShouldNotBeFound("resRouterLink.equals=" + UPDATED_RES_ROUTER_LINK);
    }

    @Test
    @Transactional
    public void getAllResourcesByResRouterLinkIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resRouterLink in DEFAULT_RES_ROUTER_LINK or UPDATED_RES_ROUTER_LINK
        defaultResourceShouldBeFound("resRouterLink.in=" + DEFAULT_RES_ROUTER_LINK + "," + UPDATED_RES_ROUTER_LINK);

        // Get all the resourceList where resRouterLink equals to UPDATED_RES_ROUTER_LINK
        defaultResourceShouldNotBeFound("resRouterLink.in=" + UPDATED_RES_ROUTER_LINK);
    }

    @Test
    @Transactional
    public void getAllResourcesByResRouterLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resRouterLink is not null
        defaultResourceShouldBeFound("resRouterLink.specified=true");

        // Get all the resourceList where resRouterLink is null
        defaultResourceShouldNotBeFound("resRouterLink.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resDescription equals to DEFAULT_RES_DESCRIPTION
        defaultResourceShouldBeFound("resDescription.equals=" + DEFAULT_RES_DESCRIPTION);

        // Get all the resourceList where resDescription equals to UPDATED_RES_DESCRIPTION
        defaultResourceShouldNotBeFound("resDescription.equals=" + UPDATED_RES_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllResourcesByResDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resDescription in DEFAULT_RES_DESCRIPTION or UPDATED_RES_DESCRIPTION
        defaultResourceShouldBeFound("resDescription.in=" + DEFAULT_RES_DESCRIPTION + "," + UPDATED_RES_DESCRIPTION);

        // Get all the resourceList where resDescription equals to UPDATED_RES_DESCRIPTION
        defaultResourceShouldNotBeFound("resDescription.in=" + UPDATED_RES_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllResourcesByResDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resDescription is not null
        defaultResourceShouldBeFound("resDescription.specified=true");

        // Get all the resourceList where resDescription is null
        defaultResourceShouldNotBeFound("resDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resFlag equals to DEFAULT_RES_FLAG
        defaultResourceShouldBeFound("resFlag.equals=" + DEFAULT_RES_FLAG);

        // Get all the resourceList where resFlag equals to UPDATED_RES_FLAG
        defaultResourceShouldNotBeFound("resFlag.equals=" + UPDATED_RES_FLAG);
    }

    @Test
    @Transactional
    public void getAllResourcesByResFlagIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resFlag in DEFAULT_RES_FLAG or UPDATED_RES_FLAG
        defaultResourceShouldBeFound("resFlag.in=" + DEFAULT_RES_FLAG + "," + UPDATED_RES_FLAG);

        // Get all the resourceList where resFlag equals to UPDATED_RES_FLAG
        defaultResourceShouldNotBeFound("resFlag.in=" + UPDATED_RES_FLAG);
    }

    @Test
    @Transactional
    public void getAllResourcesByResFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resFlag is not null
        defaultResourceShouldBeFound("resFlag.specified=true");

        // Get all the resourceList where resFlag is null
        defaultResourceShouldNotBeFound("resFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResOperateIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resOperate equals to DEFAULT_RES_OPERATE
        defaultResourceShouldBeFound("resOperate.equals=" + DEFAULT_RES_OPERATE);

        // Get all the resourceList where resOperate equals to UPDATED_RES_OPERATE
        defaultResourceShouldNotBeFound("resOperate.equals=" + UPDATED_RES_OPERATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResOperateIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resOperate in DEFAULT_RES_OPERATE or UPDATED_RES_OPERATE
        defaultResourceShouldBeFound("resOperate.in=" + DEFAULT_RES_OPERATE + "," + UPDATED_RES_OPERATE);

        // Get all the resourceList where resOperate equals to UPDATED_RES_OPERATE
        defaultResourceShouldNotBeFound("resOperate.in=" + UPDATED_RES_OPERATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResOperateIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resOperate is not null
        defaultResourceShouldBeFound("resOperate.specified=true");

        // Get all the resourceList where resOperate is null
        defaultResourceShouldNotBeFound("resOperate.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResHrefIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resHref equals to DEFAULT_RES_HREF
        defaultResourceShouldBeFound("resHref.equals=" + DEFAULT_RES_HREF);

        // Get all the resourceList where resHref equals to UPDATED_RES_HREF
        defaultResourceShouldNotBeFound("resHref.equals=" + UPDATED_RES_HREF);
    }

    @Test
    @Transactional
    public void getAllResourcesByResHrefIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resHref in DEFAULT_RES_HREF or UPDATED_RES_HREF
        defaultResourceShouldBeFound("resHref.in=" + DEFAULT_RES_HREF + "," + UPDATED_RES_HREF);

        // Get all the resourceList where resHref equals to UPDATED_RES_HREF
        defaultResourceShouldNotBeFound("resHref.in=" + UPDATED_RES_HREF);
    }

    @Test
    @Transactional
    public void getAllResourcesByResHrefIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resHref is not null
        defaultResourceShouldBeFound("resHref.specified=true");

        // Get all the resourceList where resHref is null
        defaultResourceShouldNotBeFound("resHref.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResSrcIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resSrc equals to DEFAULT_RES_SRC
        defaultResourceShouldBeFound("resSrc.equals=" + DEFAULT_RES_SRC);

        // Get all the resourceList where resSrc equals to UPDATED_RES_SRC
        defaultResourceShouldNotBeFound("resSrc.equals=" + UPDATED_RES_SRC);
    }

    @Test
    @Transactional
    public void getAllResourcesByResSrcIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resSrc in DEFAULT_RES_SRC or UPDATED_RES_SRC
        defaultResourceShouldBeFound("resSrc.in=" + DEFAULT_RES_SRC + "," + UPDATED_RES_SRC);

        // Get all the resourceList where resSrc equals to UPDATED_RES_SRC
        defaultResourceShouldNotBeFound("resSrc.in=" + UPDATED_RES_SRC);
    }

    @Test
    @Transactional
    public void getAllResourcesByResSrcIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resSrc is not null
        defaultResourceShouldBeFound("resSrc.specified=true");

        // Get all the resourceList where resSrc is null
        defaultResourceShouldNotBeFound("resSrc.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResTextIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resText equals to DEFAULT_RES_TEXT
        defaultResourceShouldBeFound("resText.equals=" + DEFAULT_RES_TEXT);

        // Get all the resourceList where resText equals to UPDATED_RES_TEXT
        defaultResourceShouldNotBeFound("resText.equals=" + UPDATED_RES_TEXT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResTextIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resText in DEFAULT_RES_TEXT or UPDATED_RES_TEXT
        defaultResourceShouldBeFound("resText.in=" + DEFAULT_RES_TEXT + "," + UPDATED_RES_TEXT);

        // Get all the resourceList where resText equals to UPDATED_RES_TEXT
        defaultResourceShouldNotBeFound("resText.in=" + UPDATED_RES_TEXT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resText is not null
        defaultResourceShouldBeFound("resText.specified=true");

        // Get all the resourceList where resText is null
        defaultResourceShouldNotBeFound("resText.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResClassIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resClass equals to DEFAULT_RES_CLASS
        defaultResourceShouldBeFound("resClass.equals=" + DEFAULT_RES_CLASS);

        // Get all the resourceList where resClass equals to UPDATED_RES_CLASS
        defaultResourceShouldNotBeFound("resClass.equals=" + UPDATED_RES_CLASS);
    }

    @Test
    @Transactional
    public void getAllResourcesByResClassIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resClass in DEFAULT_RES_CLASS or UPDATED_RES_CLASS
        defaultResourceShouldBeFound("resClass.in=" + DEFAULT_RES_CLASS + "," + UPDATED_RES_CLASS);

        // Get all the resourceList where resClass equals to UPDATED_RES_CLASS
        defaultResourceShouldNotBeFound("resClass.in=" + UPDATED_RES_CLASS);
    }

    @Test
    @Transactional
    public void getAllResourcesByResClassIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resClass is not null
        defaultResourceShouldBeFound("resClass.specified=true");

        // Get all the resourceList where resClass is null
        defaultResourceShouldNotBeFound("resClass.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resEffDate equals to DEFAULT_RES_EFF_DATE
        defaultResourceShouldBeFound("resEffDate.equals=" + DEFAULT_RES_EFF_DATE);

        // Get all the resourceList where resEffDate equals to UPDATED_RES_EFF_DATE
        defaultResourceShouldNotBeFound("resEffDate.equals=" + UPDATED_RES_EFF_DATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resEffDate in DEFAULT_RES_EFF_DATE or UPDATED_RES_EFF_DATE
        defaultResourceShouldBeFound("resEffDate.in=" + DEFAULT_RES_EFF_DATE + "," + UPDATED_RES_EFF_DATE);

        // Get all the resourceList where resEffDate equals to UPDATED_RES_EFF_DATE
        defaultResourceShouldNotBeFound("resEffDate.in=" + UPDATED_RES_EFF_DATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resEffDate is not null
        defaultResourceShouldBeFound("resEffDate.specified=true");

        // Get all the resourceList where resEffDate is null
        defaultResourceShouldNotBeFound("resEffDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResEffDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resEffDate is greater than or equal to DEFAULT_RES_EFF_DATE
        defaultResourceShouldBeFound("resEffDate.greaterThanOrEqual=" + DEFAULT_RES_EFF_DATE);

        // Get all the resourceList where resEffDate is greater than or equal to UPDATED_RES_EFF_DATE
        defaultResourceShouldNotBeFound("resEffDate.greaterThanOrEqual=" + UPDATED_RES_EFF_DATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResEffDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resEffDate is less than or equal to DEFAULT_RES_EFF_DATE
        defaultResourceShouldBeFound("resEffDate.lessThanOrEqual=" + DEFAULT_RES_EFF_DATE);

        // Get all the resourceList where resEffDate is less than or equal to SMALLER_RES_EFF_DATE
        defaultResourceShouldNotBeFound("resEffDate.lessThanOrEqual=" + SMALLER_RES_EFF_DATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResEffDateIsLessThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resEffDate is less than DEFAULT_RES_EFF_DATE
        defaultResourceShouldNotBeFound("resEffDate.lessThan=" + DEFAULT_RES_EFF_DATE);

        // Get all the resourceList where resEffDate is less than UPDATED_RES_EFF_DATE
        defaultResourceShouldBeFound("resEffDate.lessThan=" + UPDATED_RES_EFF_DATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResEffDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resEffDate is greater than DEFAULT_RES_EFF_DATE
        defaultResourceShouldNotBeFound("resEffDate.greaterThan=" + DEFAULT_RES_EFF_DATE);

        // Get all the resourceList where resEffDate is greater than SMALLER_RES_EFF_DATE
        defaultResourceShouldBeFound("resEffDate.greaterThan=" + SMALLER_RES_EFF_DATE);
    }


    @Test
    @Transactional
    public void getAllResourcesByResExpDateIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resExpDate equals to DEFAULT_RES_EXP_DATE
        defaultResourceShouldBeFound("resExpDate.equals=" + DEFAULT_RES_EXP_DATE);

        // Get all the resourceList where resExpDate equals to UPDATED_RES_EXP_DATE
        defaultResourceShouldNotBeFound("resExpDate.equals=" + UPDATED_RES_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResExpDateIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resExpDate in DEFAULT_RES_EXP_DATE or UPDATED_RES_EXP_DATE
        defaultResourceShouldBeFound("resExpDate.in=" + DEFAULT_RES_EXP_DATE + "," + UPDATED_RES_EXP_DATE);

        // Get all the resourceList where resExpDate equals to UPDATED_RES_EXP_DATE
        defaultResourceShouldNotBeFound("resExpDate.in=" + UPDATED_RES_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResExpDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resExpDate is not null
        defaultResourceShouldBeFound("resExpDate.specified=true");

        // Get all the resourceList where resExpDate is null
        defaultResourceShouldNotBeFound("resExpDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResExpDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resExpDate is greater than or equal to DEFAULT_RES_EXP_DATE
        defaultResourceShouldBeFound("resExpDate.greaterThanOrEqual=" + DEFAULT_RES_EXP_DATE);

        // Get all the resourceList where resExpDate is greater than or equal to UPDATED_RES_EXP_DATE
        defaultResourceShouldNotBeFound("resExpDate.greaterThanOrEqual=" + UPDATED_RES_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResExpDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resExpDate is less than or equal to DEFAULT_RES_EXP_DATE
        defaultResourceShouldBeFound("resExpDate.lessThanOrEqual=" + DEFAULT_RES_EXP_DATE);

        // Get all the resourceList where resExpDate is less than or equal to SMALLER_RES_EXP_DATE
        defaultResourceShouldNotBeFound("resExpDate.lessThanOrEqual=" + SMALLER_RES_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResExpDateIsLessThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resExpDate is less than DEFAULT_RES_EXP_DATE
        defaultResourceShouldNotBeFound("resExpDate.lessThan=" + DEFAULT_RES_EXP_DATE);

        // Get all the resourceList where resExpDate is less than UPDATED_RES_EXP_DATE
        defaultResourceShouldBeFound("resExpDate.lessThan=" + UPDATED_RES_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResExpDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resExpDate is greater than DEFAULT_RES_EXP_DATE
        defaultResourceShouldNotBeFound("resExpDate.greaterThan=" + DEFAULT_RES_EXP_DATE);

        // Get all the resourceList where resExpDate is greater than SMALLER_RES_EXP_DATE
        defaultResourceShouldBeFound("resExpDate.greaterThan=" + SMALLER_RES_EXP_DATE);
    }


    @Test
    @Transactional
    public void getAllResourcesByResLftIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLft equals to DEFAULT_RES_LFT
        defaultResourceShouldBeFound("resLft.equals=" + DEFAULT_RES_LFT);

        // Get all the resourceList where resLft equals to UPDATED_RES_LFT
        defaultResourceShouldNotBeFound("resLft.equals=" + UPDATED_RES_LFT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResLftIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLft in DEFAULT_RES_LFT or UPDATED_RES_LFT
        defaultResourceShouldBeFound("resLft.in=" + DEFAULT_RES_LFT + "," + UPDATED_RES_LFT);

        // Get all the resourceList where resLft equals to UPDATED_RES_LFT
        defaultResourceShouldNotBeFound("resLft.in=" + UPDATED_RES_LFT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResLftIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLft is not null
        defaultResourceShouldBeFound("resLft.specified=true");

        // Get all the resourceList where resLft is null
        defaultResourceShouldNotBeFound("resLft.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResLftIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLft is greater than or equal to DEFAULT_RES_LFT
        defaultResourceShouldBeFound("resLft.greaterThanOrEqual=" + DEFAULT_RES_LFT);

        // Get all the resourceList where resLft is greater than or equal to UPDATED_RES_LFT
        defaultResourceShouldNotBeFound("resLft.greaterThanOrEqual=" + UPDATED_RES_LFT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResLftIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLft is less than or equal to DEFAULT_RES_LFT
        defaultResourceShouldBeFound("resLft.lessThanOrEqual=" + DEFAULT_RES_LFT);

        // Get all the resourceList where resLft is less than or equal to SMALLER_RES_LFT
        defaultResourceShouldNotBeFound("resLft.lessThanOrEqual=" + SMALLER_RES_LFT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResLftIsLessThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLft is less than DEFAULT_RES_LFT
        defaultResourceShouldNotBeFound("resLft.lessThan=" + DEFAULT_RES_LFT);

        // Get all the resourceList where resLft is less than UPDATED_RES_LFT
        defaultResourceShouldBeFound("resLft.lessThan=" + UPDATED_RES_LFT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResLftIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLft is greater than DEFAULT_RES_LFT
        defaultResourceShouldNotBeFound("resLft.greaterThan=" + DEFAULT_RES_LFT);

        // Get all the resourceList where resLft is greater than SMALLER_RES_LFT
        defaultResourceShouldBeFound("resLft.greaterThan=" + SMALLER_RES_LFT);
    }


    @Test
    @Transactional
    public void getAllResourcesByResRgtIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resRgt equals to DEFAULT_RES_RGT
        defaultResourceShouldBeFound("resRgt.equals=" + DEFAULT_RES_RGT);

        // Get all the resourceList where resRgt equals to UPDATED_RES_RGT
        defaultResourceShouldNotBeFound("resRgt.equals=" + UPDATED_RES_RGT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResRgtIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resRgt in DEFAULT_RES_RGT or UPDATED_RES_RGT
        defaultResourceShouldBeFound("resRgt.in=" + DEFAULT_RES_RGT + "," + UPDATED_RES_RGT);

        // Get all the resourceList where resRgt equals to UPDATED_RES_RGT
        defaultResourceShouldNotBeFound("resRgt.in=" + UPDATED_RES_RGT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResRgtIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resRgt is not null
        defaultResourceShouldBeFound("resRgt.specified=true");

        // Get all the resourceList where resRgt is null
        defaultResourceShouldNotBeFound("resRgt.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResRgtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resRgt is greater than or equal to DEFAULT_RES_RGT
        defaultResourceShouldBeFound("resRgt.greaterThanOrEqual=" + DEFAULT_RES_RGT);

        // Get all the resourceList where resRgt is greater than or equal to UPDATED_RES_RGT
        defaultResourceShouldNotBeFound("resRgt.greaterThanOrEqual=" + UPDATED_RES_RGT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResRgtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resRgt is less than or equal to DEFAULT_RES_RGT
        defaultResourceShouldBeFound("resRgt.lessThanOrEqual=" + DEFAULT_RES_RGT);

        // Get all the resourceList where resRgt is less than or equal to SMALLER_RES_RGT
        defaultResourceShouldNotBeFound("resRgt.lessThanOrEqual=" + SMALLER_RES_RGT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResRgtIsLessThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resRgt is less than DEFAULT_RES_RGT
        defaultResourceShouldNotBeFound("resRgt.lessThan=" + DEFAULT_RES_RGT);

        // Get all the resourceList where resRgt is less than UPDATED_RES_RGT
        defaultResourceShouldBeFound("resRgt.lessThan=" + UPDATED_RES_RGT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResRgtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resRgt is greater than DEFAULT_RES_RGT
        defaultResourceShouldNotBeFound("resRgt.greaterThan=" + DEFAULT_RES_RGT);

        // Get all the resourceList where resRgt is greater than SMALLER_RES_RGT
        defaultResourceShouldBeFound("resRgt.greaterThan=" + SMALLER_RES_RGT);
    }


    @Test
    @Transactional
    public void getAllResourcesByResLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLevel equals to DEFAULT_RES_LEVEL
        defaultResourceShouldBeFound("resLevel.equals=" + DEFAULT_RES_LEVEL);

        // Get all the resourceList where resLevel equals to UPDATED_RES_LEVEL
        defaultResourceShouldNotBeFound("resLevel.equals=" + UPDATED_RES_LEVEL);
    }

    @Test
    @Transactional
    public void getAllResourcesByResLevelIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLevel in DEFAULT_RES_LEVEL or UPDATED_RES_LEVEL
        defaultResourceShouldBeFound("resLevel.in=" + DEFAULT_RES_LEVEL + "," + UPDATED_RES_LEVEL);

        // Get all the resourceList where resLevel equals to UPDATED_RES_LEVEL
        defaultResourceShouldNotBeFound("resLevel.in=" + UPDATED_RES_LEVEL);
    }

    @Test
    @Transactional
    public void getAllResourcesByResLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLevel is not null
        defaultResourceShouldBeFound("resLevel.specified=true");

        // Get all the resourceList where resLevel is null
        defaultResourceShouldNotBeFound("resLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLevel is greater than or equal to DEFAULT_RES_LEVEL
        defaultResourceShouldBeFound("resLevel.greaterThanOrEqual=" + DEFAULT_RES_LEVEL);

        // Get all the resourceList where resLevel is greater than or equal to UPDATED_RES_LEVEL
        defaultResourceShouldNotBeFound("resLevel.greaterThanOrEqual=" + UPDATED_RES_LEVEL);
    }

    @Test
    @Transactional
    public void getAllResourcesByResLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLevel is less than or equal to DEFAULT_RES_LEVEL
        defaultResourceShouldBeFound("resLevel.lessThanOrEqual=" + DEFAULT_RES_LEVEL);

        // Get all the resourceList where resLevel is less than or equal to SMALLER_RES_LEVEL
        defaultResourceShouldNotBeFound("resLevel.lessThanOrEqual=" + SMALLER_RES_LEVEL);
    }

    @Test
    @Transactional
    public void getAllResourcesByResLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLevel is less than DEFAULT_RES_LEVEL
        defaultResourceShouldNotBeFound("resLevel.lessThan=" + DEFAULT_RES_LEVEL);

        // Get all the resourceList where resLevel is less than UPDATED_RES_LEVEL
        defaultResourceShouldBeFound("resLevel.lessThan=" + UPDATED_RES_LEVEL);
    }

    @Test
    @Transactional
    public void getAllResourcesByResLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resLevel is greater than DEFAULT_RES_LEVEL
        defaultResourceShouldNotBeFound("resLevel.greaterThan=" + DEFAULT_RES_LEVEL);

        // Get all the resourceList where resLevel is greater than SMALLER_RES_LEVEL
        defaultResourceShouldBeFound("resLevel.greaterThan=" + SMALLER_RES_LEVEL);
    }


    @Test
    @Transactional
    public void getAllResourcesByResOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resOrder equals to DEFAULT_RES_ORDER
        defaultResourceShouldBeFound("resOrder.equals=" + DEFAULT_RES_ORDER);

        // Get all the resourceList where resOrder equals to UPDATED_RES_ORDER
        defaultResourceShouldNotBeFound("resOrder.equals=" + UPDATED_RES_ORDER);
    }

    @Test
    @Transactional
    public void getAllResourcesByResOrderIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resOrder in DEFAULT_RES_ORDER or UPDATED_RES_ORDER
        defaultResourceShouldBeFound("resOrder.in=" + DEFAULT_RES_ORDER + "," + UPDATED_RES_ORDER);

        // Get all the resourceList where resOrder equals to UPDATED_RES_ORDER
        defaultResourceShouldNotBeFound("resOrder.in=" + UPDATED_RES_ORDER);
    }

    @Test
    @Transactional
    public void getAllResourcesByResOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resOrder is not null
        defaultResourceShouldBeFound("resOrder.specified=true");

        // Get all the resourceList where resOrder is null
        defaultResourceShouldNotBeFound("resOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resOrder is greater than or equal to DEFAULT_RES_ORDER
        defaultResourceShouldBeFound("resOrder.greaterThanOrEqual=" + DEFAULT_RES_ORDER);

        // Get all the resourceList where resOrder is greater than or equal to UPDATED_RES_ORDER
        defaultResourceShouldNotBeFound("resOrder.greaterThanOrEqual=" + UPDATED_RES_ORDER);
    }

    @Test
    @Transactional
    public void getAllResourcesByResOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resOrder is less than or equal to DEFAULT_RES_ORDER
        defaultResourceShouldBeFound("resOrder.lessThanOrEqual=" + DEFAULT_RES_ORDER);

        // Get all the resourceList where resOrder is less than or equal to SMALLER_RES_ORDER
        defaultResourceShouldNotBeFound("resOrder.lessThanOrEqual=" + SMALLER_RES_ORDER);
    }

    @Test
    @Transactional
    public void getAllResourcesByResOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resOrder is less than DEFAULT_RES_ORDER
        defaultResourceShouldNotBeFound("resOrder.lessThan=" + DEFAULT_RES_ORDER);

        // Get all the resourceList where resOrder is less than UPDATED_RES_ORDER
        defaultResourceShouldBeFound("resOrder.lessThan=" + UPDATED_RES_ORDER);
    }

    @Test
    @Transactional
    public void getAllResourcesByResOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resOrder is greater than DEFAULT_RES_ORDER
        defaultResourceShouldNotBeFound("resOrder.greaterThan=" + DEFAULT_RES_ORDER);

        // Get all the resourceList where resOrder is greater than SMALLER_RES_ORDER
        defaultResourceShouldBeFound("resOrder.greaterThan=" + SMALLER_RES_ORDER);
    }


    @Test
    @Transactional
    public void getAllResourcesByLeafIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where leaf equals to DEFAULT_LEAF
        defaultResourceShouldBeFound("leaf.equals=" + DEFAULT_LEAF);

        // Get all the resourceList where leaf equals to UPDATED_LEAF
        defaultResourceShouldNotBeFound("leaf.equals=" + UPDATED_LEAF);
    }

    @Test
    @Transactional
    public void getAllResourcesByLeafIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where leaf in DEFAULT_LEAF or UPDATED_LEAF
        defaultResourceShouldBeFound("leaf.in=" + DEFAULT_LEAF + "," + UPDATED_LEAF);

        // Get all the resourceList where leaf equals to UPDATED_LEAF
        defaultResourceShouldNotBeFound("leaf.in=" + UPDATED_LEAF);
    }

    @Test
    @Transactional
    public void getAllResourcesByLeafIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where leaf is not null
        defaultResourceShouldBeFound("leaf.specified=true");

        // Get all the resourceList where leaf is null
        defaultResourceShouldNotBeFound("leaf.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResDisabledIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resDisabled equals to DEFAULT_RES_DISABLED
        defaultResourceShouldBeFound("resDisabled.equals=" + DEFAULT_RES_DISABLED);

        // Get all the resourceList where resDisabled equals to UPDATED_RES_DISABLED
        defaultResourceShouldNotBeFound("resDisabled.equals=" + UPDATED_RES_DISABLED);
    }

    @Test
    @Transactional
    public void getAllResourcesByResDisabledIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resDisabled in DEFAULT_RES_DISABLED or UPDATED_RES_DISABLED
        defaultResourceShouldBeFound("resDisabled.in=" + DEFAULT_RES_DISABLED + "," + UPDATED_RES_DISABLED);

        // Get all the resourceList where resDisabled equals to UPDATED_RES_DISABLED
        defaultResourceShouldNotBeFound("resDisabled.in=" + UPDATED_RES_DISABLED);
    }

    @Test
    @Transactional
    public void getAllResourcesByResDisabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resDisabled is not null
        defaultResourceShouldBeFound("resDisabled.specified=true");

        // Get all the resourceList where resDisabled is null
        defaultResourceShouldNotBeFound("resDisabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResCheckedIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resChecked equals to DEFAULT_RES_CHECKED
        defaultResourceShouldBeFound("resChecked.equals=" + DEFAULT_RES_CHECKED);

        // Get all the resourceList where resChecked equals to UPDATED_RES_CHECKED
        defaultResourceShouldNotBeFound("resChecked.equals=" + UPDATED_RES_CHECKED);
    }

    @Test
    @Transactional
    public void getAllResourcesByResCheckedIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resChecked in DEFAULT_RES_CHECKED or UPDATED_RES_CHECKED
        defaultResourceShouldBeFound("resChecked.in=" + DEFAULT_RES_CHECKED + "," + UPDATED_RES_CHECKED);

        // Get all the resourceList where resChecked equals to UPDATED_RES_CHECKED
        defaultResourceShouldNotBeFound("resChecked.in=" + UPDATED_RES_CHECKED);
    }

    @Test
    @Transactional
    public void getAllResourcesByResCheckedIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resChecked is not null
        defaultResourceShouldBeFound("resChecked.specified=true");

        // Get all the resourceList where resChecked is null
        defaultResourceShouldNotBeFound("resChecked.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResExpandedIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resExpanded equals to DEFAULT_RES_EXPANDED
        defaultResourceShouldBeFound("resExpanded.equals=" + DEFAULT_RES_EXPANDED);

        // Get all the resourceList where resExpanded equals to UPDATED_RES_EXPANDED
        defaultResourceShouldNotBeFound("resExpanded.equals=" + UPDATED_RES_EXPANDED);
    }

    @Test
    @Transactional
    public void getAllResourcesByResExpandedIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resExpanded in DEFAULT_RES_EXPANDED or UPDATED_RES_EXPANDED
        defaultResourceShouldBeFound("resExpanded.in=" + DEFAULT_RES_EXPANDED + "," + UPDATED_RES_EXPANDED);

        // Get all the resourceList where resExpanded equals to UPDATED_RES_EXPANDED
        defaultResourceShouldNotBeFound("resExpanded.in=" + UPDATED_RES_EXPANDED);
    }

    @Test
    @Transactional
    public void getAllResourcesByResExpandedIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resExpanded is not null
        defaultResourceShouldBeFound("resExpanded.specified=true");

        // Get all the resourceList where resExpanded is null
        defaultResourceShouldNotBeFound("resExpanded.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResSelectedIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resSelected equals to DEFAULT_RES_SELECTED
        defaultResourceShouldBeFound("resSelected.equals=" + DEFAULT_RES_SELECTED);

        // Get all the resourceList where resSelected equals to UPDATED_RES_SELECTED
        defaultResourceShouldNotBeFound("resSelected.equals=" + UPDATED_RES_SELECTED);
    }

    @Test
    @Transactional
    public void getAllResourcesByResSelectedIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resSelected in DEFAULT_RES_SELECTED or UPDATED_RES_SELECTED
        defaultResourceShouldBeFound("resSelected.in=" + DEFAULT_RES_SELECTED + "," + UPDATED_RES_SELECTED);

        // Get all the resourceList where resSelected equals to UPDATED_RES_SELECTED
        defaultResourceShouldNotBeFound("resSelected.in=" + UPDATED_RES_SELECTED);
    }

    @Test
    @Transactional
    public void getAllResourcesByResSelectedIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resSelected is not null
        defaultResourceShouldBeFound("resSelected.specified=true");

        // Get all the resourceList where resSelected is null
        defaultResourceShouldNotBeFound("resSelected.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByUpperIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);
        Resource upper = ResourceResourceIT.createEntity(em);
        em.persist(upper);
        em.flush();
        resource.setUpper(upper);
        resourceRepository.saveAndFlush(resource);
        Long upperId = upper.getId();

        // Get all the resourceList where upper equals to upperId
        defaultResourceShouldBeFound("upperId.equals=" + upperId);

        // Get all the resourceList where upper equals to upperId + 1
        defaultResourceShouldNotBeFound("upperId.equals=" + (upperId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResourceShouldBeFound(String filter) throws Exception {
        restResourceMockMvc.perform(get("/api/resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resource.getId().intValue())))
            .andExpect(jsonPath("$.[*].resRouterLink").value(hasItem(DEFAULT_RES_ROUTER_LINK)))
            .andExpect(jsonPath("$.[*].resDescription").value(hasItem(DEFAULT_RES_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].resFlag").value(hasItem(DEFAULT_RES_FLAG)))
            .andExpect(jsonPath("$.[*].resOperate").value(hasItem(DEFAULT_RES_OPERATE)))
            .andExpect(jsonPath("$.[*].resHref").value(hasItem(DEFAULT_RES_HREF)))
            .andExpect(jsonPath("$.[*].resSrc").value(hasItem(DEFAULT_RES_SRC)))
            .andExpect(jsonPath("$.[*].resText").value(hasItem(DEFAULT_RES_TEXT)))
            .andExpect(jsonPath("$.[*].resClass").value(hasItem(DEFAULT_RES_CLASS)))
            .andExpect(jsonPath("$.[*].resEffDate").value(hasItem(sameInstant(DEFAULT_RES_EFF_DATE))))
            .andExpect(jsonPath("$.[*].resExpDate").value(hasItem(sameInstant(DEFAULT_RES_EXP_DATE))))
            .andExpect(jsonPath("$.[*].resLft").value(hasItem(DEFAULT_RES_LFT.intValue())))
            .andExpect(jsonPath("$.[*].resRgt").value(hasItem(DEFAULT_RES_RGT.intValue())))
            .andExpect(jsonPath("$.[*].resLevel").value(hasItem(DEFAULT_RES_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].resOrder").value(hasItem(DEFAULT_RES_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())))
            .andExpect(jsonPath("$.[*].resDisabled").value(hasItem(DEFAULT_RES_DISABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].resChecked").value(hasItem(DEFAULT_RES_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].resExpanded").value(hasItem(DEFAULT_RES_EXPANDED.booleanValue())))
            .andExpect(jsonPath("$.[*].resSelected").value(hasItem(DEFAULT_RES_SELECTED.booleanValue())));

        // Check, that the count call also returns 1
        restResourceMockMvc.perform(get("/api/resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResourceShouldNotBeFound(String filter) throws Exception {
        restResourceMockMvc.perform(get("/api/resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResourceMockMvc.perform(get("/api/resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingResource() throws Exception {
        // Get the resource
        restResourceMockMvc.perform(get("/api/resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Update the resource
        Resource updatedResource = resourceRepository.findById(resource.getId()).get();
        // Disconnect from session so that the updates on updatedResource are not directly saved in db
        em.detach(updatedResource);
        updatedResource
            .resRouterLink(UPDATED_RES_ROUTER_LINK)
            .resDescription(UPDATED_RES_DESCRIPTION)
            .resFlag(UPDATED_RES_FLAG)
            .resOperate(UPDATED_RES_OPERATE)
            .resHref(UPDATED_RES_HREF)
            .resSrc(UPDATED_RES_SRC)
            .resText(UPDATED_RES_TEXT)
            .resClass(UPDATED_RES_CLASS)
            .resEffDate(UPDATED_RES_EFF_DATE)
            .resExpDate(UPDATED_RES_EXP_DATE)
            .resLft(UPDATED_RES_LFT)
            .resRgt(UPDATED_RES_RGT)
            .resLevel(UPDATED_RES_LEVEL)
            .resOrder(UPDATED_RES_ORDER)
            .leaf(UPDATED_LEAF)
            .resDisabled(UPDATED_RES_DISABLED)
            .resChecked(UPDATED_RES_CHECKED)
            .resExpanded(UPDATED_RES_EXPANDED)
            .resSelected(UPDATED_RES_SELECTED);
        ResourceDTO resourceDTO = resourceMapper.toDto(updatedResource);

        restResourceMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceDTO)))
            .andExpect(status().isOk());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getResRouterLink()).isEqualTo(UPDATED_RES_ROUTER_LINK);
        assertThat(testResource.getResDescription()).isEqualTo(UPDATED_RES_DESCRIPTION);
        assertThat(testResource.getResFlag()).isEqualTo(UPDATED_RES_FLAG);
        assertThat(testResource.getResOperate()).isEqualTo(UPDATED_RES_OPERATE);
        assertThat(testResource.getResHref()).isEqualTo(UPDATED_RES_HREF);
        assertThat(testResource.getResSrc()).isEqualTo(UPDATED_RES_SRC);
        assertThat(testResource.getResText()).isEqualTo(UPDATED_RES_TEXT);
        assertThat(testResource.getResClass()).isEqualTo(UPDATED_RES_CLASS);
        assertThat(testResource.getResEffDate()).isEqualTo(UPDATED_RES_EFF_DATE);
        assertThat(testResource.getResExpDate()).isEqualTo(UPDATED_RES_EXP_DATE);
        assertThat(testResource.getResLft()).isEqualTo(UPDATED_RES_LFT);
        assertThat(testResource.getResRgt()).isEqualTo(UPDATED_RES_RGT);
        assertThat(testResource.getResLevel()).isEqualTo(UPDATED_RES_LEVEL);
        assertThat(testResource.getResOrder()).isEqualTo(UPDATED_RES_ORDER);
        assertThat(testResource.isLeaf()).isEqualTo(UPDATED_LEAF);
        assertThat(testResource.isResDisabled()).isEqualTo(UPDATED_RES_DISABLED);
        assertThat(testResource.isResChecked()).isEqualTo(UPDATED_RES_CHECKED);
        assertThat(testResource.isResExpanded()).isEqualTo(UPDATED_RES_EXPANDED);
        assertThat(testResource.isResSelected()).isEqualTo(UPDATED_RES_SELECTED);
    }

    @Test
    @Transactional
    public void updateNonExistingResource() throws Exception {
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Create the Resource
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        int databaseSizeBeforeDelete = resourceRepository.findAll().size();

        // Delete the resource
        restResourceMockMvc.perform(delete("/api/resources/{id}", resource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resource.class);
        Resource resource1 = new Resource();
        resource1.setId(1L);
        Resource resource2 = new Resource();
        resource2.setId(resource1.getId());
        assertThat(resource1).isEqualTo(resource2);
        resource2.setId(2L);
        assertThat(resource1).isNotEqualTo(resource2);
        resource1.setId(null);
        assertThat(resource1).isNotEqualTo(resource2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceDTO.class);
        ResourceDTO resourceDTO1 = new ResourceDTO();
        resourceDTO1.setId(1L);
        ResourceDTO resourceDTO2 = new ResourceDTO();
        assertThat(resourceDTO1).isNotEqualTo(resourceDTO2);
        resourceDTO2.setId(resourceDTO1.getId());
        assertThat(resourceDTO1).isEqualTo(resourceDTO2);
        resourceDTO2.setId(2L);
        assertThat(resourceDTO1).isNotEqualTo(resourceDTO2);
        resourceDTO1.setId(null);
        assertThat(resourceDTO1).isNotEqualTo(resourceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(resourceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(resourceMapper.fromId(null)).isNull();
    }
}
