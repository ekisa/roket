package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Ogrenci;
import com.emrekisa.roket.repository.OgrenciRepository;
import com.emrekisa.roket.service.OgrenciService;
import com.emrekisa.roket.service.dto.OgrenciDTO;
import com.emrekisa.roket.service.mapper.OgrenciMapper;
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
 * Test class for the OgrenciResource REST controller.
 *
 * @see OgrenciResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class OgrenciResourceIntTest {

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    @Autowired
    private OgrenciRepository ogrenciRepository;

    @Autowired
    private OgrenciMapper ogrenciMapper;

    @Autowired
    private OgrenciService ogrenciService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOgrenciMockMvc;

    private Ogrenci ogrenci;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OgrenciResource ogrenciResource = new OgrenciResource(ogrenciService);
        this.restOgrenciMockMvc = MockMvcBuilders.standaloneSetup(ogrenciResource)
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
    public static Ogrenci createEntity(EntityManager em) {
        Ogrenci ogrenci = new Ogrenci()
            .adi(DEFAULT_ADI);
        return ogrenci;
    }

    @Before
    public void initTest() {
        ogrenci = createEntity(em);
    }

    @Test
    @Transactional
    public void createOgrenci() throws Exception {
        int databaseSizeBeforeCreate = ogrenciRepository.findAll().size();

        // Create the Ogrenci
        OgrenciDTO ogrenciDTO = ogrenciMapper.toDto(ogrenci);
        restOgrenciMockMvc.perform(post("/api/ogrencis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogrenciDTO)))
            .andExpect(status().isCreated());

        // Validate the Ogrenci in the database
        List<Ogrenci> ogrenciList = ogrenciRepository.findAll();
        assertThat(ogrenciList).hasSize(databaseSizeBeforeCreate + 1);
        Ogrenci testOgrenci = ogrenciList.get(ogrenciList.size() - 1);
        assertThat(testOgrenci.getAdi()).isEqualTo(DEFAULT_ADI);
    }

    @Test
    @Transactional
    public void createOgrenciWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ogrenciRepository.findAll().size();

        // Create the Ogrenci with an existing ID
        ogrenci.setId(1L);
        OgrenciDTO ogrenciDTO = ogrenciMapper.toDto(ogrenci);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOgrenciMockMvc.perform(post("/api/ogrencis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogrenciDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ogrenci in the database
        List<Ogrenci> ogrenciList = ogrenciRepository.findAll();
        assertThat(ogrenciList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = ogrenciRepository.findAll().size();
        // set the field null
        ogrenci.setAdi(null);

        // Create the Ogrenci, which fails.
        OgrenciDTO ogrenciDTO = ogrenciMapper.toDto(ogrenci);

        restOgrenciMockMvc.perform(post("/api/ogrencis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogrenciDTO)))
            .andExpect(status().isBadRequest());

        List<Ogrenci> ogrenciList = ogrenciRepository.findAll();
        assertThat(ogrenciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOgrencis() throws Exception {
        // Initialize the database
        ogrenciRepository.saveAndFlush(ogrenci);

        // Get all the ogrenciList
        restOgrenciMockMvc.perform(get("/api/ogrencis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ogrenci.getId().intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI.toString())));
    }

    @Test
    @Transactional
    public void getOgrenci() throws Exception {
        // Initialize the database
        ogrenciRepository.saveAndFlush(ogrenci);

        // Get the ogrenci
        restOgrenciMockMvc.perform(get("/api/ogrencis/{id}", ogrenci.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ogrenci.getId().intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOgrenci() throws Exception {
        // Get the ogrenci
        restOgrenciMockMvc.perform(get("/api/ogrencis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOgrenci() throws Exception {
        // Initialize the database
        ogrenciRepository.saveAndFlush(ogrenci);
        int databaseSizeBeforeUpdate = ogrenciRepository.findAll().size();

        // Update the ogrenci
        Ogrenci updatedOgrenci = ogrenciRepository.findOne(ogrenci.getId());
        // Disconnect from session so that the updates on updatedOgrenci are not directly saved in db
        em.detach(updatedOgrenci);
        updatedOgrenci
            .adi(UPDATED_ADI);
        OgrenciDTO ogrenciDTO = ogrenciMapper.toDto(updatedOgrenci);

        restOgrenciMockMvc.perform(put("/api/ogrencis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogrenciDTO)))
            .andExpect(status().isOk());

        // Validate the Ogrenci in the database
        List<Ogrenci> ogrenciList = ogrenciRepository.findAll();
        assertThat(ogrenciList).hasSize(databaseSizeBeforeUpdate);
        Ogrenci testOgrenci = ogrenciList.get(ogrenciList.size() - 1);
        assertThat(testOgrenci.getAdi()).isEqualTo(UPDATED_ADI);
    }

    @Test
    @Transactional
    public void updateNonExistingOgrenci() throws Exception {
        int databaseSizeBeforeUpdate = ogrenciRepository.findAll().size();

        // Create the Ogrenci
        OgrenciDTO ogrenciDTO = ogrenciMapper.toDto(ogrenci);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOgrenciMockMvc.perform(put("/api/ogrencis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogrenciDTO)))
            .andExpect(status().isCreated());

        // Validate the Ogrenci in the database
        List<Ogrenci> ogrenciList = ogrenciRepository.findAll();
        assertThat(ogrenciList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOgrenci() throws Exception {
        // Initialize the database
        ogrenciRepository.saveAndFlush(ogrenci);
        int databaseSizeBeforeDelete = ogrenciRepository.findAll().size();

        // Get the ogrenci
        restOgrenciMockMvc.perform(delete("/api/ogrencis/{id}", ogrenci.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ogrenci> ogrenciList = ogrenciRepository.findAll();
        assertThat(ogrenciList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ogrenci.class);
        Ogrenci ogrenci1 = new Ogrenci();
        ogrenci1.setId(1L);
        Ogrenci ogrenci2 = new Ogrenci();
        ogrenci2.setId(ogrenci1.getId());
        assertThat(ogrenci1).isEqualTo(ogrenci2);
        ogrenci2.setId(2L);
        assertThat(ogrenci1).isNotEqualTo(ogrenci2);
        ogrenci1.setId(null);
        assertThat(ogrenci1).isNotEqualTo(ogrenci2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OgrenciDTO.class);
        OgrenciDTO ogrenciDTO1 = new OgrenciDTO();
        ogrenciDTO1.setId(1L);
        OgrenciDTO ogrenciDTO2 = new OgrenciDTO();
        assertThat(ogrenciDTO1).isNotEqualTo(ogrenciDTO2);
        ogrenciDTO2.setId(ogrenciDTO1.getId());
        assertThat(ogrenciDTO1).isEqualTo(ogrenciDTO2);
        ogrenciDTO2.setId(2L);
        assertThat(ogrenciDTO1).isNotEqualTo(ogrenciDTO2);
        ogrenciDTO1.setId(null);
        assertThat(ogrenciDTO1).isNotEqualTo(ogrenciDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ogrenciMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ogrenciMapper.fromId(null)).isNull();
    }
}
