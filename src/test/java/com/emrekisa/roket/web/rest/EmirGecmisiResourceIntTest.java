package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.EmirGecmisi;
import com.emrekisa.roket.repository.EmirGecmisiRepository;
import com.emrekisa.roket.service.EmirGecmisiService;
import com.emrekisa.roket.service.dto.EmirGecmisiDTO;
import com.emrekisa.roket.service.mapper.EmirGecmisiMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.emrekisa.roket.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emrekisa.roket.domain.enumeration.EMIR_STATU;
/**
 * Test class for the EmirGecmisiResource REST controller.
 *
 * @see EmirGecmisiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class EmirGecmisiResourceIntTest {

    private static final Instant DEFAULT_ZAMAN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ZAMAN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EMIR_STATU DEFAULT_STATU = EMIR_STATU.HAZIR;
    private static final EMIR_STATU UPDATED_STATU = EMIR_STATU.DAGITIMDA;

    @Autowired
    private EmirGecmisiRepository emirGecmisiRepository;

    @Autowired
    private EmirGecmisiMapper emirGecmisiMapper;

    @Autowired
    private EmirGecmisiService emirGecmisiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmirGecmisiMockMvc;

    private EmirGecmisi emirGecmisi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmirGecmisiResource emirGecmisiResource = new EmirGecmisiResource(emirGecmisiService);
        this.restEmirGecmisiMockMvc = MockMvcBuilders.standaloneSetup(emirGecmisiResource)
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
    public static EmirGecmisi createEntity(EntityManager em) {
        EmirGecmisi emirGecmisi = new EmirGecmisi()
            .zaman(DEFAULT_ZAMAN)
            .statu(DEFAULT_STATU);
        return emirGecmisi;
    }

    @Before
    public void initTest() {
        emirGecmisi = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmirGecmisi() throws Exception {
        int databaseSizeBeforeCreate = emirGecmisiRepository.findAll().size();

        // Create the EmirGecmisi
        EmirGecmisiDTO emirGecmisiDTO = emirGecmisiMapper.toDto(emirGecmisi);
        restEmirGecmisiMockMvc.perform(post("/api/emir-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emirGecmisiDTO)))
            .andExpect(status().isCreated());

        // Validate the EmirGecmisi in the database
        List<EmirGecmisi> emirGecmisiList = emirGecmisiRepository.findAll();
        assertThat(emirGecmisiList).hasSize(databaseSizeBeforeCreate + 1);
        EmirGecmisi testEmirGecmisi = emirGecmisiList.get(emirGecmisiList.size() - 1);
        assertThat(testEmirGecmisi.getZaman()).isEqualTo(DEFAULT_ZAMAN);
        assertThat(testEmirGecmisi.getStatu()).isEqualTo(DEFAULT_STATU);
    }

    @Test
    @Transactional
    public void createEmirGecmisiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emirGecmisiRepository.findAll().size();

        // Create the EmirGecmisi with an existing ID
        emirGecmisi.setId(1L);
        EmirGecmisiDTO emirGecmisiDTO = emirGecmisiMapper.toDto(emirGecmisi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmirGecmisiMockMvc.perform(post("/api/emir-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emirGecmisiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmirGecmisi in the database
        List<EmirGecmisi> emirGecmisiList = emirGecmisiRepository.findAll();
        assertThat(emirGecmisiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkZamanIsRequired() throws Exception {
        int databaseSizeBeforeTest = emirGecmisiRepository.findAll().size();
        // set the field null
        emirGecmisi.setZaman(null);

        // Create the EmirGecmisi, which fails.
        EmirGecmisiDTO emirGecmisiDTO = emirGecmisiMapper.toDto(emirGecmisi);

        restEmirGecmisiMockMvc.perform(post("/api/emir-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emirGecmisiDTO)))
            .andExpect(status().isBadRequest());

        List<EmirGecmisi> emirGecmisiList = emirGecmisiRepository.findAll();
        assertThat(emirGecmisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatuIsRequired() throws Exception {
        int databaseSizeBeforeTest = emirGecmisiRepository.findAll().size();
        // set the field null
        emirGecmisi.setStatu(null);

        // Create the EmirGecmisi, which fails.
        EmirGecmisiDTO emirGecmisiDTO = emirGecmisiMapper.toDto(emirGecmisi);

        restEmirGecmisiMockMvc.perform(post("/api/emir-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emirGecmisiDTO)))
            .andExpect(status().isBadRequest());

        List<EmirGecmisi> emirGecmisiList = emirGecmisiRepository.findAll();
        assertThat(emirGecmisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmirGecmisis() throws Exception {
        // Initialize the database
        emirGecmisiRepository.saveAndFlush(emirGecmisi);

        // Get all the emirGecmisiList
        restEmirGecmisiMockMvc.perform(get("/api/emir-gecmisis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emirGecmisi.getId().intValue())))
            .andExpect(jsonPath("$.[*].zaman").value(hasItem(DEFAULT_ZAMAN.toString())))
            .andExpect(jsonPath("$.[*].statu").value(hasItem(DEFAULT_STATU.toString())));
    }

    @Test
    @Transactional
    public void getEmirGecmisi() throws Exception {
        // Initialize the database
        emirGecmisiRepository.saveAndFlush(emirGecmisi);

        // Get the emirGecmisi
        restEmirGecmisiMockMvc.perform(get("/api/emir-gecmisis/{id}", emirGecmisi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emirGecmisi.getId().intValue()))
            .andExpect(jsonPath("$.zaman").value(DEFAULT_ZAMAN.toString()))
            .andExpect(jsonPath("$.statu").value(DEFAULT_STATU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmirGecmisi() throws Exception {
        // Get the emirGecmisi
        restEmirGecmisiMockMvc.perform(get("/api/emir-gecmisis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmirGecmisi() throws Exception {
        // Initialize the database
        emirGecmisiRepository.saveAndFlush(emirGecmisi);
        int databaseSizeBeforeUpdate = emirGecmisiRepository.findAll().size();

        // Update the emirGecmisi
        EmirGecmisi updatedEmirGecmisi = emirGecmisiRepository.findOne(emirGecmisi.getId());
        // Disconnect from session so that the updates on updatedEmirGecmisi are not directly saved in db
        em.detach(updatedEmirGecmisi);
        updatedEmirGecmisi
            .zaman(UPDATED_ZAMAN)
            .statu(UPDATED_STATU);
        EmirGecmisiDTO emirGecmisiDTO = emirGecmisiMapper.toDto(updatedEmirGecmisi);

        restEmirGecmisiMockMvc.perform(put("/api/emir-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emirGecmisiDTO)))
            .andExpect(status().isOk());

        // Validate the EmirGecmisi in the database
        List<EmirGecmisi> emirGecmisiList = emirGecmisiRepository.findAll();
        assertThat(emirGecmisiList).hasSize(databaseSizeBeforeUpdate);
        EmirGecmisi testEmirGecmisi = emirGecmisiList.get(emirGecmisiList.size() - 1);
        assertThat(testEmirGecmisi.getZaman()).isEqualTo(UPDATED_ZAMAN);
        assertThat(testEmirGecmisi.getStatu()).isEqualTo(UPDATED_STATU);
    }

    @Test
    @Transactional
    public void updateNonExistingEmirGecmisi() throws Exception {
        int databaseSizeBeforeUpdate = emirGecmisiRepository.findAll().size();

        // Create the EmirGecmisi
        EmirGecmisiDTO emirGecmisiDTO = emirGecmisiMapper.toDto(emirGecmisi);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmirGecmisiMockMvc.perform(put("/api/emir-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emirGecmisiDTO)))
            .andExpect(status().isCreated());

        // Validate the EmirGecmisi in the database
        List<EmirGecmisi> emirGecmisiList = emirGecmisiRepository.findAll();
        assertThat(emirGecmisiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmirGecmisi() throws Exception {
        // Initialize the database
        emirGecmisiRepository.saveAndFlush(emirGecmisi);
        int databaseSizeBeforeDelete = emirGecmisiRepository.findAll().size();

        // Get the emirGecmisi
        restEmirGecmisiMockMvc.perform(delete("/api/emir-gecmisis/{id}", emirGecmisi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmirGecmisi> emirGecmisiList = emirGecmisiRepository.findAll();
        assertThat(emirGecmisiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmirGecmisi.class);
        EmirGecmisi emirGecmisi1 = new EmirGecmisi();
        emirGecmisi1.setId(1L);
        EmirGecmisi emirGecmisi2 = new EmirGecmisi();
        emirGecmisi2.setId(emirGecmisi1.getId());
        assertThat(emirGecmisi1).isEqualTo(emirGecmisi2);
        emirGecmisi2.setId(2L);
        assertThat(emirGecmisi1).isNotEqualTo(emirGecmisi2);
        emirGecmisi1.setId(null);
        assertThat(emirGecmisi1).isNotEqualTo(emirGecmisi2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmirGecmisiDTO.class);
        EmirGecmisiDTO emirGecmisiDTO1 = new EmirGecmisiDTO();
        emirGecmisiDTO1.setId(1L);
        EmirGecmisiDTO emirGecmisiDTO2 = new EmirGecmisiDTO();
        assertThat(emirGecmisiDTO1).isNotEqualTo(emirGecmisiDTO2);
        emirGecmisiDTO2.setId(emirGecmisiDTO1.getId());
        assertThat(emirGecmisiDTO1).isEqualTo(emirGecmisiDTO2);
        emirGecmisiDTO2.setId(2L);
        assertThat(emirGecmisiDTO1).isNotEqualTo(emirGecmisiDTO2);
        emirGecmisiDTO1.setId(null);
        assertThat(emirGecmisiDTO1).isNotEqualTo(emirGecmisiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emirGecmisiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emirGecmisiMapper.fromId(null)).isNull();
    }
}
