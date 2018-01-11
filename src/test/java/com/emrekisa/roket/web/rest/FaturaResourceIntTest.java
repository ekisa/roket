package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Fatura;
import com.emrekisa.roket.repository.FaturaRepository;
import com.emrekisa.roket.service.FaturaService;
import com.emrekisa.roket.service.dto.FaturaDTO;
import com.emrekisa.roket.service.mapper.FaturaMapper;
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
 * Test class for the FaturaResource REST controller.
 *
 * @see FaturaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class FaturaResourceIntTest {

    private static final String DEFAULT_YIL = "AAAAAAAAAA";
    private static final String UPDATED_YIL = "BBBBBBBBBB";

    private static final String DEFAULT_AY = "AAAAAAAAAA";
    private static final String UPDATED_AY = "BBBBBBBBBB";

    @Autowired
    private FaturaRepository faturaRepository;

    @Autowired
    private FaturaMapper faturaMapper;

    @Autowired
    private FaturaService faturaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFaturaMockMvc;

    private Fatura fatura;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FaturaResource faturaResource = new FaturaResource(faturaService);
        this.restFaturaMockMvc = MockMvcBuilders.standaloneSetup(faturaResource)
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
    public static Fatura createEntity(EntityManager em) {
        Fatura fatura = new Fatura()
            .yil(DEFAULT_YIL)
            .ay(DEFAULT_AY);
        return fatura;
    }

    @Before
    public void initTest() {
        fatura = createEntity(em);
    }

    @Test
    @Transactional
    public void createFatura() throws Exception {
        int databaseSizeBeforeCreate = faturaRepository.findAll().size();

        // Create the Fatura
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);
        restFaturaMockMvc.perform(post("/api/faturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faturaDTO)))
            .andExpect(status().isCreated());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeCreate + 1);
        Fatura testFatura = faturaList.get(faturaList.size() - 1);
        assertThat(testFatura.getYil()).isEqualTo(DEFAULT_YIL);
        assertThat(testFatura.getAy()).isEqualTo(DEFAULT_AY);
    }

    @Test
    @Transactional
    public void createFaturaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faturaRepository.findAll().size();

        // Create the Fatura with an existing ID
        fatura.setId(1L);
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaturaMockMvc.perform(post("/api/faturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faturaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkYilIsRequired() throws Exception {
        int databaseSizeBeforeTest = faturaRepository.findAll().size();
        // set the field null
        fatura.setYil(null);

        // Create the Fatura, which fails.
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);

        restFaturaMockMvc.perform(post("/api/faturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faturaDTO)))
            .andExpect(status().isBadRequest());

        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAyIsRequired() throws Exception {
        int databaseSizeBeforeTest = faturaRepository.findAll().size();
        // set the field null
        fatura.setAy(null);

        // Create the Fatura, which fails.
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);

        restFaturaMockMvc.perform(post("/api/faturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faturaDTO)))
            .andExpect(status().isBadRequest());

        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaturas() throws Exception {
        // Initialize the database
        faturaRepository.saveAndFlush(fatura);

        // Get all the faturaList
        restFaturaMockMvc.perform(get("/api/faturas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fatura.getId().intValue())))
            .andExpect(jsonPath("$.[*].yil").value(hasItem(DEFAULT_YIL.toString())))
            .andExpect(jsonPath("$.[*].ay").value(hasItem(DEFAULT_AY.toString())));
    }

    @Test
    @Transactional
    public void getFatura() throws Exception {
        // Initialize the database
        faturaRepository.saveAndFlush(fatura);

        // Get the fatura
        restFaturaMockMvc.perform(get("/api/faturas/{id}", fatura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fatura.getId().intValue()))
            .andExpect(jsonPath("$.yil").value(DEFAULT_YIL.toString()))
            .andExpect(jsonPath("$.ay").value(DEFAULT_AY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFatura() throws Exception {
        // Get the fatura
        restFaturaMockMvc.perform(get("/api/faturas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFatura() throws Exception {
        // Initialize the database
        faturaRepository.saveAndFlush(fatura);
        int databaseSizeBeforeUpdate = faturaRepository.findAll().size();

        // Update the fatura
        Fatura updatedFatura = faturaRepository.findOne(fatura.getId());
        // Disconnect from session so that the updates on updatedFatura are not directly saved in db
        em.detach(updatedFatura);
        updatedFatura
            .yil(UPDATED_YIL)
            .ay(UPDATED_AY);
        FaturaDTO faturaDTO = faturaMapper.toDto(updatedFatura);

        restFaturaMockMvc.perform(put("/api/faturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faturaDTO)))
            .andExpect(status().isOk());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeUpdate);
        Fatura testFatura = faturaList.get(faturaList.size() - 1);
        assertThat(testFatura.getYil()).isEqualTo(UPDATED_YIL);
        assertThat(testFatura.getAy()).isEqualTo(UPDATED_AY);
    }

    @Test
    @Transactional
    public void updateNonExistingFatura() throws Exception {
        int databaseSizeBeforeUpdate = faturaRepository.findAll().size();

        // Create the Fatura
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFaturaMockMvc.perform(put("/api/faturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faturaDTO)))
            .andExpect(status().isCreated());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFatura() throws Exception {
        // Initialize the database
        faturaRepository.saveAndFlush(fatura);
        int databaseSizeBeforeDelete = faturaRepository.findAll().size();

        // Get the fatura
        restFaturaMockMvc.perform(delete("/api/faturas/{id}", fatura.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fatura.class);
        Fatura fatura1 = new Fatura();
        fatura1.setId(1L);
        Fatura fatura2 = new Fatura();
        fatura2.setId(fatura1.getId());
        assertThat(fatura1).isEqualTo(fatura2);
        fatura2.setId(2L);
        assertThat(fatura1).isNotEqualTo(fatura2);
        fatura1.setId(null);
        assertThat(fatura1).isNotEqualTo(fatura2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaturaDTO.class);
        FaturaDTO faturaDTO1 = new FaturaDTO();
        faturaDTO1.setId(1L);
        FaturaDTO faturaDTO2 = new FaturaDTO();
        assertThat(faturaDTO1).isNotEqualTo(faturaDTO2);
        faturaDTO2.setId(faturaDTO1.getId());
        assertThat(faturaDTO1).isEqualTo(faturaDTO2);
        faturaDTO2.setId(2L);
        assertThat(faturaDTO1).isNotEqualTo(faturaDTO2);
        faturaDTO1.setId(null);
        assertThat(faturaDTO1).isNotEqualTo(faturaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(faturaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(faturaMapper.fromId(null)).isNull();
    }
}
