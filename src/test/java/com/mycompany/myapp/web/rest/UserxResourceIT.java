package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GetewayApp;
import com.mycompany.myapp.domain.Userx;
import com.mycompany.myapp.domain.Organization;
import com.mycompany.myapp.domain.Role;
import com.mycompany.myapp.repository.UserxRepository;
import com.mycompany.myapp.service.UserxService;
import com.mycompany.myapp.service.dto.UserxDTO;
import com.mycompany.myapp.service.mapper.UserxMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.UserxCriteria;
import com.mycompany.myapp.service.UserxQueryService;

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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserxResource} REST controller.
 */
@SpringBootTest(classes = GetewayApp.class)
public class UserxResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_LANG_KEY = "AAAAAAAAAA";
    private static final String UPDATED_LANG_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_RESET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_RESET_KEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESET_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESET_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_RESET_DATE = Instant.ofEpochMilli(-1L);

    @Autowired
    private UserxRepository userxRepository;

    @Mock
    private UserxRepository userxRepositoryMock;

    @Autowired
    private UserxMapper userxMapper;

    @Mock
    private UserxService userxServiceMock;

    @Autowired
    private UserxService userxService;

    @Autowired
    private UserxQueryService userxQueryService;

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

    private MockMvc restUserxMockMvc;

    private Userx userx;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserxResource userxResource = new UserxResource(userxService, userxQueryService);
        this.restUserxMockMvc = MockMvcBuilders.standaloneSetup(userxResource)
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
    public static Userx createEntity(EntityManager em) {
        Userx userx = new Userx()
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .activated(DEFAULT_ACTIVATED)
            .langKey(DEFAULT_LANG_KEY)
            .imageUrl(DEFAULT_IMAGE_URL)
            .activationKey(DEFAULT_ACTIVATION_KEY)
            .resetKey(DEFAULT_RESET_KEY)
            .resetDate(DEFAULT_RESET_DATE);
        return userx;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Userx createUpdatedEntity(EntityManager em) {
        Userx userx = new Userx()
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .activated(UPDATED_ACTIVATED)
            .langKey(UPDATED_LANG_KEY)
            .imageUrl(UPDATED_IMAGE_URL)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE);
        return userx;
    }

    @BeforeEach
    public void initTest() {
        userx = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserx() throws Exception {
        int databaseSizeBeforeCreate = userxRepository.findAll().size();

        // Create the Userx
        UserxDTO userxDTO = userxMapper.toDto(userx);
        restUserxMockMvc.perform(post("/api/userxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userxDTO)))
            .andExpect(status().isCreated());

        // Validate the Userx in the database
        List<Userx> userxList = userxRepository.findAll();
        assertThat(userxList).hasSize(databaseSizeBeforeCreate + 1);
        Userx testUserx = userxList.get(userxList.size() - 1);
        assertThat(testUserx.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testUserx.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserx.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUserx.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserx.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserx.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testUserx.getLangKey()).isEqualTo(DEFAULT_LANG_KEY);
        assertThat(testUserx.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testUserx.getActivationKey()).isEqualTo(DEFAULT_ACTIVATION_KEY);
        assertThat(testUserx.getResetKey()).isEqualTo(DEFAULT_RESET_KEY);
        assertThat(testUserx.getResetDate()).isEqualTo(DEFAULT_RESET_DATE);
    }

    @Test
    @Transactional
    public void createUserxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userxRepository.findAll().size();

        // Create the Userx with an existing ID
        userx.setId(1L);
        UserxDTO userxDTO = userxMapper.toDto(userx);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserxMockMvc.perform(post("/api/userxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Userx in the database
        List<Userx> userxList = userxRepository.findAll();
        assertThat(userxList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = userxRepository.findAll().size();
        // set the field null
        userx.setLogin(null);

        // Create the Userx, which fails.
        UserxDTO userxDTO = userxMapper.toDto(userx);

        restUserxMockMvc.perform(post("/api/userxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userxDTO)))
            .andExpect(status().isBadRequest());

        List<Userx> userxList = userxRepository.findAll();
        assertThat(userxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserxes() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList
        restUserxMockMvc.perform(get("/api/userxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userx.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY.toString())))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY.toString())))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(DEFAULT_RESET_DATE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUserxesWithEagerRelationshipsIsEnabled() throws Exception {
        UserxResource userxResource = new UserxResource(userxServiceMock, userxQueryService);
        when(userxServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUserxMockMvc = MockMvcBuilders.standaloneSetup(userxResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserxMockMvc.perform(get("/api/userxes?eagerload=true"))
        .andExpect(status().isOk());

        verify(userxServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserxesWithEagerRelationshipsIsNotEnabled() throws Exception {
        UserxResource userxResource = new UserxResource(userxServiceMock, userxQueryService);
            when(userxServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUserxMockMvc = MockMvcBuilders.standaloneSetup(userxResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserxMockMvc.perform(get("/api/userxes?eagerload=true"))
        .andExpect(status().isOk());

            verify(userxServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserx() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get the userx
        restUserxMockMvc.perform(get("/api/userxes/{id}", userx.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userx.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.langKey").value(DEFAULT_LANG_KEY.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.activationKey").value(DEFAULT_ACTIVATION_KEY.toString()))
            .andExpect(jsonPath("$.resetKey").value(DEFAULT_RESET_KEY.toString()))
            .andExpect(jsonPath("$.resetDate").value(DEFAULT_RESET_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllUserxesByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where login equals to DEFAULT_LOGIN
        defaultUserxShouldBeFound("login.equals=" + DEFAULT_LOGIN);

        // Get all the userxList where login equals to UPDATED_LOGIN
        defaultUserxShouldNotBeFound("login.equals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserxesByLoginIsInShouldWork() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where login in DEFAULT_LOGIN or UPDATED_LOGIN
        defaultUserxShouldBeFound("login.in=" + DEFAULT_LOGIN + "," + UPDATED_LOGIN);

        // Get all the userxList where login equals to UPDATED_LOGIN
        defaultUserxShouldNotBeFound("login.in=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserxesByLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where login is not null
        defaultUserxShouldBeFound("login.specified=true");

        // Get all the userxList where login is null
        defaultUserxShouldNotBeFound("login.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserxesByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where password equals to DEFAULT_PASSWORD
        defaultUserxShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the userxList where password equals to UPDATED_PASSWORD
        defaultUserxShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllUserxesByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultUserxShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the userxList where password equals to UPDATED_PASSWORD
        defaultUserxShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllUserxesByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where password is not null
        defaultUserxShouldBeFound("password.specified=true");

        // Get all the userxList where password is null
        defaultUserxShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserxesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where firstName equals to DEFAULT_FIRST_NAME
        defaultUserxShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the userxList where firstName equals to UPDATED_FIRST_NAME
        defaultUserxShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserxesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultUserxShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the userxList where firstName equals to UPDATED_FIRST_NAME
        defaultUserxShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserxesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where firstName is not null
        defaultUserxShouldBeFound("firstName.specified=true");

        // Get all the userxList where firstName is null
        defaultUserxShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserxesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where lastName equals to DEFAULT_LAST_NAME
        defaultUserxShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the userxList where lastName equals to UPDATED_LAST_NAME
        defaultUserxShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserxesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultUserxShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the userxList where lastName equals to UPDATED_LAST_NAME
        defaultUserxShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserxesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where lastName is not null
        defaultUserxShouldBeFound("lastName.specified=true");

        // Get all the userxList where lastName is null
        defaultUserxShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserxesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where email equals to DEFAULT_EMAIL
        defaultUserxShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the userxList where email equals to UPDATED_EMAIL
        defaultUserxShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUserxesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultUserxShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the userxList where email equals to UPDATED_EMAIL
        defaultUserxShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUserxesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where email is not null
        defaultUserxShouldBeFound("email.specified=true");

        // Get all the userxList where email is null
        defaultUserxShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserxesByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where activated equals to DEFAULT_ACTIVATED
        defaultUserxShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the userxList where activated equals to UPDATED_ACTIVATED
        defaultUserxShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllUserxesByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultUserxShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the userxList where activated equals to UPDATED_ACTIVATED
        defaultUserxShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllUserxesByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where activated is not null
        defaultUserxShouldBeFound("activated.specified=true");

        // Get all the userxList where activated is null
        defaultUserxShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserxesByLangKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where langKey equals to DEFAULT_LANG_KEY
        defaultUserxShouldBeFound("langKey.equals=" + DEFAULT_LANG_KEY);

        // Get all the userxList where langKey equals to UPDATED_LANG_KEY
        defaultUserxShouldNotBeFound("langKey.equals=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    public void getAllUserxesByLangKeyIsInShouldWork() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where langKey in DEFAULT_LANG_KEY or UPDATED_LANG_KEY
        defaultUserxShouldBeFound("langKey.in=" + DEFAULT_LANG_KEY + "," + UPDATED_LANG_KEY);

        // Get all the userxList where langKey equals to UPDATED_LANG_KEY
        defaultUserxShouldNotBeFound("langKey.in=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    public void getAllUserxesByLangKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where langKey is not null
        defaultUserxShouldBeFound("langKey.specified=true");

        // Get all the userxList where langKey is null
        defaultUserxShouldNotBeFound("langKey.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserxesByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultUserxShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the userxList where imageUrl equals to UPDATED_IMAGE_URL
        defaultUserxShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllUserxesByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultUserxShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the userxList where imageUrl equals to UPDATED_IMAGE_URL
        defaultUserxShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllUserxesByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where imageUrl is not null
        defaultUserxShouldBeFound("imageUrl.specified=true");

        // Get all the userxList where imageUrl is null
        defaultUserxShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserxesByActivationKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where activationKey equals to DEFAULT_ACTIVATION_KEY
        defaultUserxShouldBeFound("activationKey.equals=" + DEFAULT_ACTIVATION_KEY);

        // Get all the userxList where activationKey equals to UPDATED_ACTIVATION_KEY
        defaultUserxShouldNotBeFound("activationKey.equals=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    public void getAllUserxesByActivationKeyIsInShouldWork() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where activationKey in DEFAULT_ACTIVATION_KEY or UPDATED_ACTIVATION_KEY
        defaultUserxShouldBeFound("activationKey.in=" + DEFAULT_ACTIVATION_KEY + "," + UPDATED_ACTIVATION_KEY);

        // Get all the userxList where activationKey equals to UPDATED_ACTIVATION_KEY
        defaultUserxShouldNotBeFound("activationKey.in=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    public void getAllUserxesByActivationKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where activationKey is not null
        defaultUserxShouldBeFound("activationKey.specified=true");

        // Get all the userxList where activationKey is null
        defaultUserxShouldNotBeFound("activationKey.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserxesByResetKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where resetKey equals to DEFAULT_RESET_KEY
        defaultUserxShouldBeFound("resetKey.equals=" + DEFAULT_RESET_KEY);

        // Get all the userxList where resetKey equals to UPDATED_RESET_KEY
        defaultUserxShouldNotBeFound("resetKey.equals=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    public void getAllUserxesByResetKeyIsInShouldWork() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where resetKey in DEFAULT_RESET_KEY or UPDATED_RESET_KEY
        defaultUserxShouldBeFound("resetKey.in=" + DEFAULT_RESET_KEY + "," + UPDATED_RESET_KEY);

        // Get all the userxList where resetKey equals to UPDATED_RESET_KEY
        defaultUserxShouldNotBeFound("resetKey.in=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    public void getAllUserxesByResetKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where resetKey is not null
        defaultUserxShouldBeFound("resetKey.specified=true");

        // Get all the userxList where resetKey is null
        defaultUserxShouldNotBeFound("resetKey.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserxesByResetDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where resetDate equals to DEFAULT_RESET_DATE
        defaultUserxShouldBeFound("resetDate.equals=" + DEFAULT_RESET_DATE);

        // Get all the userxList where resetDate equals to UPDATED_RESET_DATE
        defaultUserxShouldNotBeFound("resetDate.equals=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void getAllUserxesByResetDateIsInShouldWork() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where resetDate in DEFAULT_RESET_DATE or UPDATED_RESET_DATE
        defaultUserxShouldBeFound("resetDate.in=" + DEFAULT_RESET_DATE + "," + UPDATED_RESET_DATE);

        // Get all the userxList where resetDate equals to UPDATED_RESET_DATE
        defaultUserxShouldNotBeFound("resetDate.in=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void getAllUserxesByResetDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        // Get all the userxList where resetDate is not null
        defaultUserxShouldBeFound("resetDate.specified=true");

        // Get all the userxList where resetDate is null
        defaultUserxShouldNotBeFound("resetDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserxesByOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);
        Organization organization = OrganizationResourceIT.createEntity(em);
        em.persist(organization);
        em.flush();
        userx.setOrganization(organization);
        userxRepository.saveAndFlush(userx);
        Long organizationId = organization.getId();

        // Get all the userxList where organization equals to organizationId
        defaultUserxShouldBeFound("organizationId.equals=" + organizationId);

        // Get all the userxList where organization equals to organizationId + 1
        defaultUserxShouldNotBeFound("organizationId.equals=" + (organizationId + 1));
    }


    @Test
    @Transactional
    public void getAllUserxesByRolesIsEqualToSomething() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);
        Role roles = RoleResourceIT.createEntity(em);
        em.persist(roles);
        em.flush();
        userx.addRoles(roles);
        userxRepository.saveAndFlush(userx);
        Long rolesId = roles.getId();

        // Get all the userxList where roles equals to rolesId
        defaultUserxShouldBeFound("rolesId.equals=" + rolesId);

        // Get all the userxList where roles equals to rolesId + 1
        defaultUserxShouldNotBeFound("rolesId.equals=" + (rolesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserxShouldBeFound(String filter) throws Exception {
        restUserxMockMvc.perform(get("/api/userxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userx.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY)))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY)))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(DEFAULT_RESET_DATE.toString())));

        // Check, that the count call also returns 1
        restUserxMockMvc.perform(get("/api/userxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserxShouldNotBeFound(String filter) throws Exception {
        restUserxMockMvc.perform(get("/api/userxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserxMockMvc.perform(get("/api/userxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUserx() throws Exception {
        // Get the userx
        restUserxMockMvc.perform(get("/api/userxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserx() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        int databaseSizeBeforeUpdate = userxRepository.findAll().size();

        // Update the userx
        Userx updatedUserx = userxRepository.findById(userx.getId()).get();
        // Disconnect from session so that the updates on updatedUserx are not directly saved in db
        em.detach(updatedUserx);
        updatedUserx
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .activated(UPDATED_ACTIVATED)
            .langKey(UPDATED_LANG_KEY)
            .imageUrl(UPDATED_IMAGE_URL)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE);
        UserxDTO userxDTO = userxMapper.toDto(updatedUserx);

        restUserxMockMvc.perform(put("/api/userxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userxDTO)))
            .andExpect(status().isOk());

        // Validate the Userx in the database
        List<Userx> userxList = userxRepository.findAll();
        assertThat(userxList).hasSize(databaseSizeBeforeUpdate);
        Userx testUserx = userxList.get(userxList.size() - 1);
        assertThat(testUserx.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testUserx.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserx.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUserx.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserx.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserx.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testUserx.getLangKey()).isEqualTo(UPDATED_LANG_KEY);
        assertThat(testUserx.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testUserx.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testUserx.getResetKey()).isEqualTo(UPDATED_RESET_KEY);
        assertThat(testUserx.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserx() throws Exception {
        int databaseSizeBeforeUpdate = userxRepository.findAll().size();

        // Create the Userx
        UserxDTO userxDTO = userxMapper.toDto(userx);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserxMockMvc.perform(put("/api/userxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Userx in the database
        List<Userx> userxList = userxRepository.findAll();
        assertThat(userxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserx() throws Exception {
        // Initialize the database
        userxRepository.saveAndFlush(userx);

        int databaseSizeBeforeDelete = userxRepository.findAll().size();

        // Delete the userx
        restUserxMockMvc.perform(delete("/api/userxes/{id}", userx.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Userx> userxList = userxRepository.findAll();
        assertThat(userxList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Userx.class);
        Userx userx1 = new Userx();
        userx1.setId(1L);
        Userx userx2 = new Userx();
        userx2.setId(userx1.getId());
        assertThat(userx1).isEqualTo(userx2);
        userx2.setId(2L);
        assertThat(userx1).isNotEqualTo(userx2);
        userx1.setId(null);
        assertThat(userx1).isNotEqualTo(userx2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserxDTO.class);
        UserxDTO userxDTO1 = new UserxDTO();
        userxDTO1.setId(1L);
        UserxDTO userxDTO2 = new UserxDTO();
        assertThat(userxDTO1).isNotEqualTo(userxDTO2);
        userxDTO2.setId(userxDTO1.getId());
        assertThat(userxDTO1).isEqualTo(userxDTO2);
        userxDTO2.setId(2L);
        assertThat(userxDTO1).isNotEqualTo(userxDTO2);
        userxDTO1.setId(null);
        assertThat(userxDTO1).isNotEqualTo(userxDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userxMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userxMapper.fromId(null)).isNull();
    }
}
