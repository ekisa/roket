package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Mahalle;
import com.emrekisa.roket.repository.MahalleRepository;
import com.emrekisa.roket.service.MahalleService;
import com.emrekisa.roket.service.dto.MahalleDTO;
import com.emrekisa.roket.service.mapper.MahalleMapper;
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
 * Test class for the MahalleResource REST controller.
 *
 * @see MahalleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class MahalleResourceIntTest {

    private static final String DEFAULT_MAHALLE_AD = "AAAAAAAAAA";
    private static final String UPDATED_MAHALLE_AD = "BBBBBBBBBB";

    private static final String DEFAULT_POSTA_KODU = "AAAAAAAAAA";
    private static final String UPDATED_POSTA_KODU = "BBBBBBBBBB";

    private static final String DEFAULT_SEMT = "AAAAAAAAAA";
    private static final String UPDATED_SEMT = "BBBBBBBBBB";

    @Autowired
    private MahalleRepository mahalleRepository;

    @Autowired
    private MahalleMapper mahalleMapper;

    @Autowired
    private MahalleService mahalleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMahalleMockMvc;

    private Mahalle mahalle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MahalleResource mahalleResource = new MahalleResource(mahalleService);
        this.restMahalleMockMvc = MockMvcBuilders.standaloneSetup(mahalleResource)
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
    public static Mahalle createEntity(EntityManager em) {
        Mahalle mahalle = new Mahalle()
            .mahalleAd(DEFAULT_MAHALLE_AD)
            .postaKodu(DEFAULT_POSTA_KODU)
            .semt(DEFAULT_SEMT);
        return mahalle;
    }

    @Before
    public void initTest() {
        mahalle = createEntity(em);
    }

    @Test
    @Transactional
    public void createMahalle() throws Exception {
        int databaseSizeBeforeCreate = mahalleRepository.findAll().size();

        // Create the Mahalle
        MahalleDTO mahalleDTO = mahalleMapper.toDto(mahalle);
        restMahalleMockMvc.perform(post("/api/mahalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahalleDTO)))
            .andExpect(status().isCreated());

        // Validate the Mahalle in the database
        List<Mahalle> mahalleList = mahalleRepository.findAll();
        assertThat(mahalleList).hasSize(databaseSizeBeforeCreate + 1);
        Mahalle testMahalle = mahalleList.get(mahalleList.size() - 1);
        assertThat(testMahalle.getMahalleAd()).isEqualTo(DEFAULT_MAHALLE_AD);
        assertThat(testMahalle.getPostaKodu()).isEqualTo(DEFAULT_POSTA_KODU);
        assertThat(testMahalle.getSemt()).isEqualTo(DEFAULT_SEMT);
    }

    @Test
    @Transactional
    public void createMahalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mahalleRepository.findAll().size();

        // Create the Mahalle with an existing ID
        mahalle.setId(1L);
        MahalleDTO mahalleDTO = mahalleMapper.toDto(mahalle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMahalleMockMvc.perform(post("/api/mahalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahalleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mahalle in the database
        List<Mahalle> mahalleList = mahalleRepository.findAll();
        assertThat(mahalleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMahalleAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = mahalleRepository.findAll().size();
        // set the field null
        mahalle.setMahalleAd(null);

        // Create the Mahalle, which fails.
        MahalleDTO mahalleDTO = mahalleMapper.toDto(mahalle);

        restMahalleMockMvc.perform(post("/api/mahalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahalleDTO)))
            .andExpect(status().isBadRequest());

        List<Mahalle> mahalleList = mahalleRepository.findAll();
        assertThat(mahalleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMahalles() throws Exception {
        // Initialize the database
        mahalleRepository.saveAndFlush(mahalle);

        // Get all the mahalleList
        restMahalleMockMvc.perform(get("/api/mahalles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mahalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].mahalleAd").value(hasItem(DEFAULT_MAHALLE_AD.toString())))
            .andExpect(jsonPath("$.[*].postaKodu").value(hasItem(DEFAULT_POSTA_KODU.toString())))
            .andExpect(jsonPath("$.[*].semt").value(hasItem(DEFAULT_SEMT.toString())));
    }

    @Test
    @Transactional
    public void getMahalle() throws Exception {
        // Initialize the database
        mahalleRepository.saveAndFlush(mahalle);

        // Get the mahalle
        restMahalleMockMvc.perform(get("/api/mahalles/{id}", mahalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mahalle.getId().intValue()))
            .andExpect(jsonPath("$.mahalleAd").value(DEFAULT_MAHALLE_AD.toString()))
            .andExpect(jsonPath("$.postaKodu").value(DEFAULT_POSTA_KODU.toString()))
            .andExpect(jsonPath("$.semt").value(DEFAULT_SEMT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMahalle() throws Exception {
        // Get the mahalle
        restMahalleMockMvc.perform(get("/api/mahalles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMahalle() throws Exception {
        // Initialize the database
        mahalleRepository.saveAndFlush(mahalle);
        int databaseSizeBeforeUpdate = mahalleRepository.findAll().size();

        // Update the mahalle
        Mahalle updatedMahalle = mahalleRepository.findOne(mahalle.getId());
        // Disconnect from session so that the updates on updatedMahalle are not directly saved in db
        em.detach(updatedMahalle);
        updatedMahalle
            .mahalleAd(UPDATED_MAHALLE_AD)
            .postaKodu(UPDATED_POSTA_KODU)
            .semt(UPDATED_SEMT);
        MahalleDTO mahalleDTO = mahalleMapper.toDto(updatedMahalle);

        restMahalleMockMvc.perform(put("/api/mahalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahalleDTO)))
            .andExpect(status().isOk());

        // Validate the Mahalle in the database
        List<Mahalle> mahalleList = mahalleRepository.findAll();
        assertThat(mahalleList).hasSize(databaseSizeBeforeUpdate);
        Mahalle testMahalle = mahalleList.get(mahalleList.size() - 1);
        assertThat(testMahalle.getMahalleAd()).isEqualTo(UPDATED_MAHALLE_AD);
        assertThat(testMahalle.getPostaKodu()).isEqualTo(UPDATED_POSTA_KODU);
        assertThat(testMahalle.getSemt()).isEqualTo(UPDATED_SEMT);
    }

    @Test
    @Transactional
    public void updateNonExistingMahalle() throws Exception {
        int databaseSizeBeforeUpdate = mahalleRepository.findAll().size();

        // Create the Mahalle
        MahalleDTO mahalleDTO = mahalleMapper.toDto(mahalle);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMahalleMockMvc.perform(put("/api/mahalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahalleDTO)))
            .andExpect(status().isCreated());

        // Validate the Mahalle in the database
        List<Mahalle> mahalleList = mahalleRepository.findAll();
        assertThat(mahalleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMahalle() throws Exception {
        // Initialize the database
        mahalleRepository.saveAndFlush(mahalle);
        int databaseSizeBeforeDelete = mahalleRepository.findAll().size();

        // Get the mahalle
        restMahalleMockMvc.perform(delete("/api/mahalles/{id}", mahalle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mahalle> mahalleList = mahalleRepository.findAll();
        assertThat(mahalleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mahalle.class);
        Mahalle mahalle1 = new Mahalle();
        mahalle1.setId(1L);
        Mahalle mahalle2 = new Mahalle();
        mahalle2.setId(mahalle1.getId());
        assertThat(mahalle1).isEqualTo(mahalle2);
        mahalle2.setId(2L);
        assertThat(mahalle1).isNotEqualTo(mahalle2);
        mahalle1.setId(null);
        assertThat(mahalle1).isNotEqualTo(mahalle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MahalleDTO.class);
        MahalleDTO mahalleDTO1 = new MahalleDTO();
        mahalleDTO1.setId(1L);
        MahalleDTO mahalleDTO2 = new MahalleDTO();
        assertThat(mahalleDTO1).isNotEqualTo(mahalleDTO2);
        mahalleDTO2.setId(mahalleDTO1.getId());
        assertThat(mahalleDTO1).isEqualTo(mahalleDTO2);
        mahalleDTO2.setId(2L);
        assertThat(mahalleDTO1).isNotEqualTo(mahalleDTO2);
        mahalleDTO1.setId(null);
        assertThat(mahalleDTO1).isNotEqualTo(mahalleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mahalleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mahalleMapper.fromId(null)).isNull();
    }
}
