package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.KuryeGecmisi;
import com.emrekisa.roket.repository.KuryeGecmisiRepository;
import com.emrekisa.roket.service.KuryeGecmisiService;
import com.emrekisa.roket.service.dto.KuryeGecmisiDTO;
import com.emrekisa.roket.service.mapper.KuryeGecmisiMapper;
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

import com.emrekisa.roket.domain.enumeration.KURYE_STATU;
/**
 * Test class for the KuryeGecmisiResource REST controller.
 *
 * @see KuryeGecmisiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class KuryeGecmisiResourceIntTest {

    private static final Instant DEFAULT_ZAMAN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ZAMAN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final KURYE_STATU DEFAULT_STATU = KURYE_STATU.HAZIR;
    private static final KURYE_STATU UPDATED_STATU = KURYE_STATU.CALISMIYOR;

    @Autowired
    private KuryeGecmisiRepository kuryeGecmisiRepository;

    @Autowired
    private KuryeGecmisiMapper kuryeGecmisiMapper;

    @Autowired
    private KuryeGecmisiService kuryeGecmisiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKuryeGecmisiMockMvc;

    private KuryeGecmisi kuryeGecmisi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KuryeGecmisiResource kuryeGecmisiResource = new KuryeGecmisiResource(kuryeGecmisiService);
        this.restKuryeGecmisiMockMvc = MockMvcBuilders.standaloneSetup(kuryeGecmisiResource)
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
    public static KuryeGecmisi createEntity(EntityManager em) {
        KuryeGecmisi kuryeGecmisi = new KuryeGecmisi()
            .zaman(DEFAULT_ZAMAN)
            .statu(DEFAULT_STATU);
        return kuryeGecmisi;
    }

    @Before
    public void initTest() {
        kuryeGecmisi = createEntity(em);
    }

    @Test
    @Transactional
    public void createKuryeGecmisi() throws Exception {
        int databaseSizeBeforeCreate = kuryeGecmisiRepository.findAll().size();

        // Create the KuryeGecmisi
        KuryeGecmisiDTO kuryeGecmisiDTO = kuryeGecmisiMapper.toDto(kuryeGecmisi);
        restKuryeGecmisiMockMvc.perform(post("/api/kurye-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kuryeGecmisiDTO)))
            .andExpect(status().isCreated());

        // Validate the KuryeGecmisi in the database
        List<KuryeGecmisi> kuryeGecmisiList = kuryeGecmisiRepository.findAll();
        assertThat(kuryeGecmisiList).hasSize(databaseSizeBeforeCreate + 1);
        KuryeGecmisi testKuryeGecmisi = kuryeGecmisiList.get(kuryeGecmisiList.size() - 1);
        assertThat(testKuryeGecmisi.getZaman()).isEqualTo(DEFAULT_ZAMAN);
        assertThat(testKuryeGecmisi.getStatu()).isEqualTo(DEFAULT_STATU);
    }

    @Test
    @Transactional
    public void createKuryeGecmisiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kuryeGecmisiRepository.findAll().size();

        // Create the KuryeGecmisi with an existing ID
        kuryeGecmisi.setId(1L);
        KuryeGecmisiDTO kuryeGecmisiDTO = kuryeGecmisiMapper.toDto(kuryeGecmisi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKuryeGecmisiMockMvc.perform(post("/api/kurye-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kuryeGecmisiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KuryeGecmisi in the database
        List<KuryeGecmisi> kuryeGecmisiList = kuryeGecmisiRepository.findAll();
        assertThat(kuryeGecmisiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkZamanIsRequired() throws Exception {
        int databaseSizeBeforeTest = kuryeGecmisiRepository.findAll().size();
        // set the field null
        kuryeGecmisi.setZaman(null);

        // Create the KuryeGecmisi, which fails.
        KuryeGecmisiDTO kuryeGecmisiDTO = kuryeGecmisiMapper.toDto(kuryeGecmisi);

        restKuryeGecmisiMockMvc.perform(post("/api/kurye-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kuryeGecmisiDTO)))
            .andExpect(status().isBadRequest());

        List<KuryeGecmisi> kuryeGecmisiList = kuryeGecmisiRepository.findAll();
        assertThat(kuryeGecmisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatuIsRequired() throws Exception {
        int databaseSizeBeforeTest = kuryeGecmisiRepository.findAll().size();
        // set the field null
        kuryeGecmisi.setStatu(null);

        // Create the KuryeGecmisi, which fails.
        KuryeGecmisiDTO kuryeGecmisiDTO = kuryeGecmisiMapper.toDto(kuryeGecmisi);

        restKuryeGecmisiMockMvc.perform(post("/api/kurye-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kuryeGecmisiDTO)))
            .andExpect(status().isBadRequest());

        List<KuryeGecmisi> kuryeGecmisiList = kuryeGecmisiRepository.findAll();
        assertThat(kuryeGecmisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKuryeGecmisis() throws Exception {
        // Initialize the database
        kuryeGecmisiRepository.saveAndFlush(kuryeGecmisi);

        // Get all the kuryeGecmisiList
        restKuryeGecmisiMockMvc.perform(get("/api/kurye-gecmisis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kuryeGecmisi.getId().intValue())))
            .andExpect(jsonPath("$.[*].zaman").value(hasItem(DEFAULT_ZAMAN.toString())))
            .andExpect(jsonPath("$.[*].statu").value(hasItem(DEFAULT_STATU.toString())));
    }

    @Test
    @Transactional
    public void getKuryeGecmisi() throws Exception {
        // Initialize the database
        kuryeGecmisiRepository.saveAndFlush(kuryeGecmisi);

        // Get the kuryeGecmisi
        restKuryeGecmisiMockMvc.perform(get("/api/kurye-gecmisis/{id}", kuryeGecmisi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kuryeGecmisi.getId().intValue()))
            .andExpect(jsonPath("$.zaman").value(DEFAULT_ZAMAN.toString()))
            .andExpect(jsonPath("$.statu").value(DEFAULT_STATU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKuryeGecmisi() throws Exception {
        // Get the kuryeGecmisi
        restKuryeGecmisiMockMvc.perform(get("/api/kurye-gecmisis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKuryeGecmisi() throws Exception {
        // Initialize the database
        kuryeGecmisiRepository.saveAndFlush(kuryeGecmisi);
        int databaseSizeBeforeUpdate = kuryeGecmisiRepository.findAll().size();

        // Update the kuryeGecmisi
        KuryeGecmisi updatedKuryeGecmisi = kuryeGecmisiRepository.findOne(kuryeGecmisi.getId());
        // Disconnect from session so that the updates on updatedKuryeGecmisi are not directly saved in db
        em.detach(updatedKuryeGecmisi);
        updatedKuryeGecmisi
            .zaman(UPDATED_ZAMAN)
            .statu(UPDATED_STATU);
        KuryeGecmisiDTO kuryeGecmisiDTO = kuryeGecmisiMapper.toDto(updatedKuryeGecmisi);

        restKuryeGecmisiMockMvc.perform(put("/api/kurye-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kuryeGecmisiDTO)))
            .andExpect(status().isOk());

        // Validate the KuryeGecmisi in the database
        List<KuryeGecmisi> kuryeGecmisiList = kuryeGecmisiRepository.findAll();
        assertThat(kuryeGecmisiList).hasSize(databaseSizeBeforeUpdate);
        KuryeGecmisi testKuryeGecmisi = kuryeGecmisiList.get(kuryeGecmisiList.size() - 1);
        assertThat(testKuryeGecmisi.getZaman()).isEqualTo(UPDATED_ZAMAN);
        assertThat(testKuryeGecmisi.getStatu()).isEqualTo(UPDATED_STATU);
    }

    @Test
    @Transactional
    public void updateNonExistingKuryeGecmisi() throws Exception {
        int databaseSizeBeforeUpdate = kuryeGecmisiRepository.findAll().size();

        // Create the KuryeGecmisi
        KuryeGecmisiDTO kuryeGecmisiDTO = kuryeGecmisiMapper.toDto(kuryeGecmisi);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKuryeGecmisiMockMvc.perform(put("/api/kurye-gecmisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kuryeGecmisiDTO)))
            .andExpect(status().isCreated());

        // Validate the KuryeGecmisi in the database
        List<KuryeGecmisi> kuryeGecmisiList = kuryeGecmisiRepository.findAll();
        assertThat(kuryeGecmisiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKuryeGecmisi() throws Exception {
        // Initialize the database
        kuryeGecmisiRepository.saveAndFlush(kuryeGecmisi);
        int databaseSizeBeforeDelete = kuryeGecmisiRepository.findAll().size();

        // Get the kuryeGecmisi
        restKuryeGecmisiMockMvc.perform(delete("/api/kurye-gecmisis/{id}", kuryeGecmisi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KuryeGecmisi> kuryeGecmisiList = kuryeGecmisiRepository.findAll();
        assertThat(kuryeGecmisiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KuryeGecmisi.class);
        KuryeGecmisi kuryeGecmisi1 = new KuryeGecmisi();
        kuryeGecmisi1.setId(1L);
        KuryeGecmisi kuryeGecmisi2 = new KuryeGecmisi();
        kuryeGecmisi2.setId(kuryeGecmisi1.getId());
        assertThat(kuryeGecmisi1).isEqualTo(kuryeGecmisi2);
        kuryeGecmisi2.setId(2L);
        assertThat(kuryeGecmisi1).isNotEqualTo(kuryeGecmisi2);
        kuryeGecmisi1.setId(null);
        assertThat(kuryeGecmisi1).isNotEqualTo(kuryeGecmisi2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KuryeGecmisiDTO.class);
        KuryeGecmisiDTO kuryeGecmisiDTO1 = new KuryeGecmisiDTO();
        kuryeGecmisiDTO1.setId(1L);
        KuryeGecmisiDTO kuryeGecmisiDTO2 = new KuryeGecmisiDTO();
        assertThat(kuryeGecmisiDTO1).isNotEqualTo(kuryeGecmisiDTO2);
        kuryeGecmisiDTO2.setId(kuryeGecmisiDTO1.getId());
        assertThat(kuryeGecmisiDTO1).isEqualTo(kuryeGecmisiDTO2);
        kuryeGecmisiDTO2.setId(2L);
        assertThat(kuryeGecmisiDTO1).isNotEqualTo(kuryeGecmisiDTO2);
        kuryeGecmisiDTO1.setId(null);
        assertThat(kuryeGecmisiDTO1).isNotEqualTo(kuryeGecmisiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(kuryeGecmisiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(kuryeGecmisiMapper.fromId(null)).isNull();
    }
}
