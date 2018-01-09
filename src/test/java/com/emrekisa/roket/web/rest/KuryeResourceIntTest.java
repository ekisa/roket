package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Kurye;
import com.emrekisa.roket.repository.KuryeRepository;
import com.emrekisa.roket.service.KuryeService;
import com.emrekisa.roket.service.dto.KuryeDTO;
import com.emrekisa.roket.service.mapper.KuryeMapper;
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

import com.emrekisa.roket.domain.enumeration.KURYE_STATU;
/**
 * Test class for the KuryeResource REST controller.
 *
 * @see KuryeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class KuryeResourceIntTest {

    private static final KURYE_STATU DEFAULT_STATU = KURYE_STATU.HAZIR;
    private static final KURYE_STATU UPDATED_STATU = KURYE_STATU.CALISMIYOR;

    @Autowired
    private KuryeRepository kuryeRepository;

    @Autowired
    private KuryeMapper kuryeMapper;

    @Autowired
    private KuryeService kuryeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKuryeMockMvc;

    private Kurye kurye;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KuryeResource kuryeResource = new KuryeResource(kuryeService);
        this.restKuryeMockMvc = MockMvcBuilders.standaloneSetup(kuryeResource)
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
    public static Kurye createEntity(EntityManager em) {
        Kurye kurye = new Kurye()
            .statu(DEFAULT_STATU);
        return kurye;
    }

    @Before
    public void initTest() {
        kurye = createEntity(em);
    }

    @Test
    @Transactional
    public void createKurye() throws Exception {
        int databaseSizeBeforeCreate = kuryeRepository.findAll().size();

        // Create the Kurye
        KuryeDTO kuryeDTO = kuryeMapper.toDto(kurye);
        restKuryeMockMvc.perform(post("/api/kuryes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kuryeDTO)))
            .andExpect(status().isCreated());

        // Validate the Kurye in the database
        List<Kurye> kuryeList = kuryeRepository.findAll();
        assertThat(kuryeList).hasSize(databaseSizeBeforeCreate + 1);
        Kurye testKurye = kuryeList.get(kuryeList.size() - 1);
        assertThat(testKurye.getStatu()).isEqualTo(DEFAULT_STATU);
    }

    @Test
    @Transactional
    public void createKuryeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kuryeRepository.findAll().size();

        // Create the Kurye with an existing ID
        kurye.setId(1L);
        KuryeDTO kuryeDTO = kuryeMapper.toDto(kurye);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKuryeMockMvc.perform(post("/api/kuryes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kuryeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kurye in the database
        List<Kurye> kuryeList = kuryeRepository.findAll();
        assertThat(kuryeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatuIsRequired() throws Exception {
        int databaseSizeBeforeTest = kuryeRepository.findAll().size();
        // set the field null
        kurye.setStatu(null);

        // Create the Kurye, which fails.
        KuryeDTO kuryeDTO = kuryeMapper.toDto(kurye);

        restKuryeMockMvc.perform(post("/api/kuryes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kuryeDTO)))
            .andExpect(status().isBadRequest());

        List<Kurye> kuryeList = kuryeRepository.findAll();
        assertThat(kuryeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKuryes() throws Exception {
        // Initialize the database
        kuryeRepository.saveAndFlush(kurye);

        // Get all the kuryeList
        restKuryeMockMvc.perform(get("/api/kuryes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kurye.getId().intValue())))
            .andExpect(jsonPath("$.[*].statu").value(hasItem(DEFAULT_STATU.toString())));
    }

    @Test
    @Transactional
    public void getKurye() throws Exception {
        // Initialize the database
        kuryeRepository.saveAndFlush(kurye);

        // Get the kurye
        restKuryeMockMvc.perform(get("/api/kuryes/{id}", kurye.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kurye.getId().intValue()))
            .andExpect(jsonPath("$.statu").value(DEFAULT_STATU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKurye() throws Exception {
        // Get the kurye
        restKuryeMockMvc.perform(get("/api/kuryes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKurye() throws Exception {
        // Initialize the database
        kuryeRepository.saveAndFlush(kurye);
        int databaseSizeBeforeUpdate = kuryeRepository.findAll().size();

        // Update the kurye
        Kurye updatedKurye = kuryeRepository.findOne(kurye.getId());
        // Disconnect from session so that the updates on updatedKurye are not directly saved in db
        em.detach(updatedKurye);
        updatedKurye
            .statu(UPDATED_STATU);
        KuryeDTO kuryeDTO = kuryeMapper.toDto(updatedKurye);

        restKuryeMockMvc.perform(put("/api/kuryes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kuryeDTO)))
            .andExpect(status().isOk());

        // Validate the Kurye in the database
        List<Kurye> kuryeList = kuryeRepository.findAll();
        assertThat(kuryeList).hasSize(databaseSizeBeforeUpdate);
        Kurye testKurye = kuryeList.get(kuryeList.size() - 1);
        assertThat(testKurye.getStatu()).isEqualTo(UPDATED_STATU);
    }

    @Test
    @Transactional
    public void updateNonExistingKurye() throws Exception {
        int databaseSizeBeforeUpdate = kuryeRepository.findAll().size();

        // Create the Kurye
        KuryeDTO kuryeDTO = kuryeMapper.toDto(kurye);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKuryeMockMvc.perform(put("/api/kuryes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kuryeDTO)))
            .andExpect(status().isCreated());

        // Validate the Kurye in the database
        List<Kurye> kuryeList = kuryeRepository.findAll();
        assertThat(kuryeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKurye() throws Exception {
        // Initialize the database
        kuryeRepository.saveAndFlush(kurye);
        int databaseSizeBeforeDelete = kuryeRepository.findAll().size();

        // Get the kurye
        restKuryeMockMvc.perform(delete("/api/kuryes/{id}", kurye.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Kurye> kuryeList = kuryeRepository.findAll();
        assertThat(kuryeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kurye.class);
        Kurye kurye1 = new Kurye();
        kurye1.setId(1L);
        Kurye kurye2 = new Kurye();
        kurye2.setId(kurye1.getId());
        assertThat(kurye1).isEqualTo(kurye2);
        kurye2.setId(2L);
        assertThat(kurye1).isNotEqualTo(kurye2);
        kurye1.setId(null);
        assertThat(kurye1).isNotEqualTo(kurye2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KuryeDTO.class);
        KuryeDTO kuryeDTO1 = new KuryeDTO();
        kuryeDTO1.setId(1L);
        KuryeDTO kuryeDTO2 = new KuryeDTO();
        assertThat(kuryeDTO1).isNotEqualTo(kuryeDTO2);
        kuryeDTO2.setId(kuryeDTO1.getId());
        assertThat(kuryeDTO1).isEqualTo(kuryeDTO2);
        kuryeDTO2.setId(2L);
        assertThat(kuryeDTO1).isNotEqualTo(kuryeDTO2);
        kuryeDTO1.setId(null);
        assertThat(kuryeDTO1).isNotEqualTo(kuryeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(kuryeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(kuryeMapper.fromId(null)).isNull();
    }
}
