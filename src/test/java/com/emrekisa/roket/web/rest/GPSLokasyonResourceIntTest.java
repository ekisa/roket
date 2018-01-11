package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.GPSLokasyon;
import com.emrekisa.roket.repository.GPSLokasyonRepository;
import com.emrekisa.roket.service.GPSLokasyonService;
import com.emrekisa.roket.service.dto.GPSLokasyonDTO;
import com.emrekisa.roket.service.mapper.GPSLokasyonMapper;
import com.emrekisa.roket.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.emrekisa.roket.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GPSLokasyonResource REST controller.
 *
 * @see GPSLokasyonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class GPSLokasyonResourceIntTest {

    private static final String DEFAULT_ENLEM = "AAAAAAAAAA";
    private static final String UPDATED_ENLEM = "BBBBBBBBBB";

    private static final String DEFAULT_BOYLAM = "AAAAAAAAAA";
    private static final String UPDATED_BOYLAM = "BBBBBBBBBB";

    @Autowired
    private GPSLokasyonRepository gPSLokasyonRepository;

    @Autowired
    private GPSLokasyonMapper gPSLokasyonMapper;

    @Autowired
    private GPSLokasyonService gPSLokasyonService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGPSLokasyonMockMvc;

    private GPSLokasyon gPSLokasyon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GPSLokasyonResource gPSLokasyonResource = new GPSLokasyonResource(gPSLokasyonService);
        this.restGPSLokasyonMockMvc = MockMvcBuilders.standaloneSetup(gPSLokasyonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GPSLokasyon createEntity(EntityManager em) {
        GPSLokasyon gPSLokasyon = new GPSLokasyon()
            .enlem(DEFAULT_ENLEM)
            .boylam(DEFAULT_BOYLAM);
        return gPSLokasyon;
    }

    @Before
    public void initTest() {
        gPSLokasyon = createEntity(em);
    }

    @Test
    @Transactional
    public void createGPSLokasyon() throws Exception {
        int databaseSizeBeforeCreate = gPSLokasyonRepository.findAll().size();

        // Create the GPSLokasyon
        GPSLokasyonDTO gPSLokasyonDTO = gPSLokasyonMapper.toDto(gPSLokasyon);
        restGPSLokasyonMockMvc.perform(post("/api/gps-lokasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSLokasyonDTO)))
            .andExpect(status().isCreated());

        // Validate the GPSLokasyon in the database
        List<GPSLokasyon> gPSLokasyonList = gPSLokasyonRepository.findAll();
        assertThat(gPSLokasyonList).hasSize(databaseSizeBeforeCreate + 1);
        GPSLokasyon testGPSLokasyon = gPSLokasyonList.get(gPSLokasyonList.size() - 1);
        assertThat(testGPSLokasyon.getEnlem()).isEqualTo(DEFAULT_ENLEM);
        assertThat(testGPSLokasyon.getBoylam()).isEqualTo(DEFAULT_BOYLAM);
    }

    @Test
    @Transactional
    public void createGPSLokasyonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gPSLokasyonRepository.findAll().size();

        // Create the GPSLokasyon with an existing ID
        gPSLokasyon.setId(1L);
        GPSLokasyonDTO gPSLokasyonDTO = gPSLokasyonMapper.toDto(gPSLokasyon);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGPSLokasyonMockMvc.perform(post("/api/gps-lokasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSLokasyonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GPSLokasyon in the database
        List<GPSLokasyon> gPSLokasyonList = gPSLokasyonRepository.findAll();
        assertThat(gPSLokasyonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEnlemIsRequired() throws Exception {
        int databaseSizeBeforeTest = gPSLokasyonRepository.findAll().size();
        // set the field null
        gPSLokasyon.setEnlem(null);

        // Create the GPSLokasyon, which fails.
        GPSLokasyonDTO gPSLokasyonDTO = gPSLokasyonMapper.toDto(gPSLokasyon);

        restGPSLokasyonMockMvc.perform(post("/api/gps-lokasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSLokasyonDTO)))
            .andExpect(status().isBadRequest());

        List<GPSLokasyon> gPSLokasyonList = gPSLokasyonRepository.findAll();
        assertThat(gPSLokasyonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoylamIsRequired() throws Exception {
        int databaseSizeBeforeTest = gPSLokasyonRepository.findAll().size();
        // set the field null
        gPSLokasyon.setBoylam(null);

        // Create the GPSLokasyon, which fails.
        GPSLokasyonDTO gPSLokasyonDTO = gPSLokasyonMapper.toDto(gPSLokasyon);

        restGPSLokasyonMockMvc.perform(post("/api/gps-lokasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSLokasyonDTO)))
            .andExpect(status().isBadRequest());

        List<GPSLokasyon> gPSLokasyonList = gPSLokasyonRepository.findAll();
        assertThat(gPSLokasyonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGPSLokasyons() throws Exception {
        // Initialize the database
        gPSLokasyonRepository.saveAndFlush(gPSLokasyon);

        // Get all the gPSLokasyonList
        restGPSLokasyonMockMvc.perform(get("/api/gps-lokasyons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gPSLokasyon.getId().intValue())))
            .andExpect(jsonPath("$.[*].enlem").value(hasItem(DEFAULT_ENLEM.toString())))
            .andExpect(jsonPath("$.[*].boylam").value(hasItem(DEFAULT_BOYLAM.toString())));
    }

    @Test
    @Transactional
    public void getGPSLokasyon() throws Exception {
        // Initialize the database
        gPSLokasyonRepository.saveAndFlush(gPSLokasyon);

        // Get the gPSLokasyon
        restGPSLokasyonMockMvc.perform(get("/api/gps-lokasyons/{id}", gPSLokasyon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gPSLokasyon.getId().intValue()))
            .andExpect(jsonPath("$.enlem").value(DEFAULT_ENLEM.toString()))
            .andExpect(jsonPath("$.boylam").value(DEFAULT_BOYLAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGPSLokasyon() throws Exception {
        // Get the gPSLokasyon
        restGPSLokasyonMockMvc.perform(get("/api/gps-lokasyons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGPSLokasyon() throws Exception {
        // Initialize the database
        gPSLokasyonRepository.saveAndFlush(gPSLokasyon);
        int databaseSizeBeforeUpdate = gPSLokasyonRepository.findAll().size();

        // Update the gPSLokasyon
        GPSLokasyon updatedGPSLokasyon = gPSLokasyonRepository.findOne(gPSLokasyon.getId());
        // Disconnect from session so that the updates on updatedGPSLokasyon are not directly saved in db
        em.detach(updatedGPSLokasyon);
        updatedGPSLokasyon
            .enlem(UPDATED_ENLEM)
            .boylam(UPDATED_BOYLAM);
        GPSLokasyonDTO gPSLokasyonDTO = gPSLokasyonMapper.toDto(updatedGPSLokasyon);

        restGPSLokasyonMockMvc.perform(put("/api/gps-lokasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSLokasyonDTO)))
            .andExpect(status().isOk());

        // Validate the GPSLokasyon in the database
        List<GPSLokasyon> gPSLokasyonList = gPSLokasyonRepository.findAll();
        assertThat(gPSLokasyonList).hasSize(databaseSizeBeforeUpdate);
        GPSLokasyon testGPSLokasyon = gPSLokasyonList.get(gPSLokasyonList.size() - 1);
        assertThat(testGPSLokasyon.getEnlem()).isEqualTo(UPDATED_ENLEM);
        assertThat(testGPSLokasyon.getBoylam()).isEqualTo(UPDATED_BOYLAM);
    }

    @Test
    @Transactional
    public void updateNonExistingGPSLokasyon() throws Exception {
        int databaseSizeBeforeUpdate = gPSLokasyonRepository.findAll().size();

        // Create the GPSLokasyon
        GPSLokasyonDTO gPSLokasyonDTO = gPSLokasyonMapper.toDto(gPSLokasyon);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGPSLokasyonMockMvc.perform(put("/api/gps-lokasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSLokasyonDTO)))
            .andExpect(status().isCreated());

        // Validate the GPSLokasyon in the database
        List<GPSLokasyon> gPSLokasyonList = gPSLokasyonRepository.findAll();
        assertThat(gPSLokasyonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGPSLokasyon() throws Exception {
        // Initialize the database
        gPSLokasyonRepository.saveAndFlush(gPSLokasyon);
        int databaseSizeBeforeDelete = gPSLokasyonRepository.findAll().size();

        // Get the gPSLokasyon
        restGPSLokasyonMockMvc.perform(delete("/api/gps-lokasyons/{id}", gPSLokasyon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GPSLokasyon> gPSLokasyonList = gPSLokasyonRepository.findAll();
        assertThat(gPSLokasyonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GPSLokasyon.class);
        GPSLokasyon gPSLokasyon1 = new GPSLokasyon();
        gPSLokasyon1.setId(1L);
        GPSLokasyon gPSLokasyon2 = new GPSLokasyon();
        gPSLokasyon2.setId(gPSLokasyon1.getId());
        assertThat(gPSLokasyon1).isEqualTo(gPSLokasyon2);
        gPSLokasyon2.setId(2L);
        assertThat(gPSLokasyon1).isNotEqualTo(gPSLokasyon2);
        gPSLokasyon1.setId(null);
        assertThat(gPSLokasyon1).isNotEqualTo(gPSLokasyon2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GPSLokasyonDTO.class);
        GPSLokasyonDTO gPSLokasyonDTO1 = new GPSLokasyonDTO();
        gPSLokasyonDTO1.setId(1L);
        GPSLokasyonDTO gPSLokasyonDTO2 = new GPSLokasyonDTO();
        assertThat(gPSLokasyonDTO1).isNotEqualTo(gPSLokasyonDTO2);
        gPSLokasyonDTO2.setId(gPSLokasyonDTO1.getId());
        assertThat(gPSLokasyonDTO1).isEqualTo(gPSLokasyonDTO2);
        gPSLokasyonDTO2.setId(2L);
        assertThat(gPSLokasyonDTO1).isNotEqualTo(gPSLokasyonDTO2);
        gPSLokasyonDTO1.setId(null);
        assertThat(gPSLokasyonDTO1).isNotEqualTo(gPSLokasyonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gPSLokasyonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gPSLokasyonMapper.fromId(null)).isNull();
    }
}
