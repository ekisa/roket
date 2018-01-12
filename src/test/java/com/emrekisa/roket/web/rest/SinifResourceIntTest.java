package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Sinif;
import com.emrekisa.roket.repository.SinifRepository;
import com.emrekisa.roket.service.SinifService;
import com.emrekisa.roket.service.dto.SinifDTO;
import com.emrekisa.roket.service.mapper.SinifMapper;
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
 * Test class for the SinifResource REST controller.
 *
 * @see SinifResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class SinifResourceIntTest {

    private static final String DEFAULT_SINIF_ADI = "AAAAAAAAAA";
    private static final String UPDATED_SINIF_ADI = "BBBBBBBBBB";

    @Autowired
    private SinifRepository sinifRepository;

    @Autowired
    private SinifMapper sinifMapper;

    @Autowired
    private SinifService sinifService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSinifMockMvc;

    private Sinif sinif;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SinifResource sinifResource = new SinifResource(sinifService);
        this.restSinifMockMvc = MockMvcBuilders.standaloneSetup(sinifResource)
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
    public static Sinif createEntity(EntityManager em) {
        Sinif sinif = new Sinif()
            .sinifAdi(DEFAULT_SINIF_ADI);
        return sinif;
    }

    @Before
    public void initTest() {
        sinif = createEntity(em);
    }

    @Test
    @Transactional
    public void createSinif() throws Exception {
        int databaseSizeBeforeCreate = sinifRepository.findAll().size();

        // Create the Sinif
        SinifDTO sinifDTO = sinifMapper.toDto(sinif);
        restSinifMockMvc.perform(post("/api/sinifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinifDTO)))
            .andExpect(status().isCreated());

        // Validate the Sinif in the database
        List<Sinif> sinifList = sinifRepository.findAll();
        assertThat(sinifList).hasSize(databaseSizeBeforeCreate + 1);
        Sinif testSinif = sinifList.get(sinifList.size() - 1);
        assertThat(testSinif.getSinifAdi()).isEqualTo(DEFAULT_SINIF_ADI);
    }

    @Test
    @Transactional
    public void createSinifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sinifRepository.findAll().size();

        // Create the Sinif with an existing ID
        sinif.setId(1L);
        SinifDTO sinifDTO = sinifMapper.toDto(sinif);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSinifMockMvc.perform(post("/api/sinifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinifDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sinif in the database
        List<Sinif> sinifList = sinifRepository.findAll();
        assertThat(sinifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSinifAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = sinifRepository.findAll().size();
        // set the field null
        sinif.setSinifAdi(null);

        // Create the Sinif, which fails.
        SinifDTO sinifDTO = sinifMapper.toDto(sinif);

        restSinifMockMvc.perform(post("/api/sinifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinifDTO)))
            .andExpect(status().isBadRequest());

        List<Sinif> sinifList = sinifRepository.findAll();
        assertThat(sinifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSinifs() throws Exception {
        // Initialize the database
        sinifRepository.saveAndFlush(sinif);

        // Get all the sinifList
        restSinifMockMvc.perform(get("/api/sinifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sinif.getId().intValue())))
            .andExpect(jsonPath("$.[*].sinifAdi").value(hasItem(DEFAULT_SINIF_ADI.toString())));
    }

    @Test
    @Transactional
    public void getSinif() throws Exception {
        // Initialize the database
        sinifRepository.saveAndFlush(sinif);

        // Get the sinif
        restSinifMockMvc.perform(get("/api/sinifs/{id}", sinif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sinif.getId().intValue()))
            .andExpect(jsonPath("$.sinifAdi").value(DEFAULT_SINIF_ADI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSinif() throws Exception {
        // Get the sinif
        restSinifMockMvc.perform(get("/api/sinifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSinif() throws Exception {
        // Initialize the database
        sinifRepository.saveAndFlush(sinif);
        int databaseSizeBeforeUpdate = sinifRepository.findAll().size();

        // Update the sinif
        Sinif updatedSinif = sinifRepository.findOne(sinif.getId());
        // Disconnect from session so that the updates on updatedSinif are not directly saved in db
        em.detach(updatedSinif);
        updatedSinif
            .sinifAdi(UPDATED_SINIF_ADI);
        SinifDTO sinifDTO = sinifMapper.toDto(updatedSinif);

        restSinifMockMvc.perform(put("/api/sinifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinifDTO)))
            .andExpect(status().isOk());

        // Validate the Sinif in the database
        List<Sinif> sinifList = sinifRepository.findAll();
        assertThat(sinifList).hasSize(databaseSizeBeforeUpdate);
        Sinif testSinif = sinifList.get(sinifList.size() - 1);
        assertThat(testSinif.getSinifAdi()).isEqualTo(UPDATED_SINIF_ADI);
    }

    @Test
    @Transactional
    public void updateNonExistingSinif() throws Exception {
        int databaseSizeBeforeUpdate = sinifRepository.findAll().size();

        // Create the Sinif
        SinifDTO sinifDTO = sinifMapper.toDto(sinif);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSinifMockMvc.perform(put("/api/sinifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinifDTO)))
            .andExpect(status().isCreated());

        // Validate the Sinif in the database
        List<Sinif> sinifList = sinifRepository.findAll();
        assertThat(sinifList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSinif() throws Exception {
        // Initialize the database
        sinifRepository.saveAndFlush(sinif);
        int databaseSizeBeforeDelete = sinifRepository.findAll().size();

        // Get the sinif
        restSinifMockMvc.perform(delete("/api/sinifs/{id}", sinif.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sinif> sinifList = sinifRepository.findAll();
        assertThat(sinifList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sinif.class);
        Sinif sinif1 = new Sinif();
        sinif1.setId(1L);
        Sinif sinif2 = new Sinif();
        sinif2.setId(sinif1.getId());
        assertThat(sinif1).isEqualTo(sinif2);
        sinif2.setId(2L);
        assertThat(sinif1).isNotEqualTo(sinif2);
        sinif1.setId(null);
        assertThat(sinif1).isNotEqualTo(sinif2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SinifDTO.class);
        SinifDTO sinifDTO1 = new SinifDTO();
        sinifDTO1.setId(1L);
        SinifDTO sinifDTO2 = new SinifDTO();
        assertThat(sinifDTO1).isNotEqualTo(sinifDTO2);
        sinifDTO2.setId(sinifDTO1.getId());
        assertThat(sinifDTO1).isEqualTo(sinifDTO2);
        sinifDTO2.setId(2L);
        assertThat(sinifDTO1).isNotEqualTo(sinifDTO2);
        sinifDTO1.setId(null);
        assertThat(sinifDTO1).isNotEqualTo(sinifDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sinifMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sinifMapper.fromId(null)).isNull();
    }
}
