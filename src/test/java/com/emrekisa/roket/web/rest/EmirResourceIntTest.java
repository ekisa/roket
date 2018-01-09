package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Emir;
import com.emrekisa.roket.repository.EmirRepository;
import com.emrekisa.roket.service.EmirService;
import com.emrekisa.roket.service.dto.EmirDTO;
import com.emrekisa.roket.service.mapper.EmirMapper;
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

import com.emrekisa.roket.domain.enumeration.EMIR_STATU;
/**
 * Test class for the EmirResource REST controller.
 *
 * @see EmirResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class EmirResourceIntTest {

    private static final EMIR_STATU DEFAULT_STATU = EMIR_STATU.HAZIR;
    private static final EMIR_STATU UPDATED_STATU = EMIR_STATU.DAGITIMDA;

    @Autowired
    private EmirRepository emirRepository;

    @Autowired
    private EmirMapper emirMapper;

    @Autowired
    private EmirService emirService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmirMockMvc;

    private Emir emir;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmirResource emirResource = new EmirResource(emirService);
        this.restEmirMockMvc = MockMvcBuilders.standaloneSetup(emirResource)
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
    public static Emir createEntity(EntityManager em) {
        Emir emir = new Emir()
            .statu(DEFAULT_STATU);
        return emir;
    }

    @Before
    public void initTest() {
        emir = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmir() throws Exception {
        int databaseSizeBeforeCreate = emirRepository.findAll().size();

        // Create the Emir
        EmirDTO emirDTO = emirMapper.toDto(emir);
        restEmirMockMvc.perform(post("/api/emirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emirDTO)))
            .andExpect(status().isCreated());

        // Validate the Emir in the database
        List<Emir> emirList = emirRepository.findAll();
        assertThat(emirList).hasSize(databaseSizeBeforeCreate + 1);
        Emir testEmir = emirList.get(emirList.size() - 1);
        assertThat(testEmir.getStatu()).isEqualTo(DEFAULT_STATU);
    }

    @Test
    @Transactional
    public void createEmirWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emirRepository.findAll().size();

        // Create the Emir with an existing ID
        emir.setId(1L);
        EmirDTO emirDTO = emirMapper.toDto(emir);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmirMockMvc.perform(post("/api/emirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emirDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Emir in the database
        List<Emir> emirList = emirRepository.findAll();
        assertThat(emirList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatuIsRequired() throws Exception {
        int databaseSizeBeforeTest = emirRepository.findAll().size();
        // set the field null
        emir.setStatu(null);

        // Create the Emir, which fails.
        EmirDTO emirDTO = emirMapper.toDto(emir);

        restEmirMockMvc.perform(post("/api/emirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emirDTO)))
            .andExpect(status().isBadRequest());

        List<Emir> emirList = emirRepository.findAll();
        assertThat(emirList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmirs() throws Exception {
        // Initialize the database
        emirRepository.saveAndFlush(emir);

        // Get all the emirList
        restEmirMockMvc.perform(get("/api/emirs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emir.getId().intValue())))
            .andExpect(jsonPath("$.[*].statu").value(hasItem(DEFAULT_STATU.toString())));
    }

    @Test
    @Transactional
    public void getEmir() throws Exception {
        // Initialize the database
        emirRepository.saveAndFlush(emir);

        // Get the emir
        restEmirMockMvc.perform(get("/api/emirs/{id}", emir.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emir.getId().intValue()))
            .andExpect(jsonPath("$.statu").value(DEFAULT_STATU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmir() throws Exception {
        // Get the emir
        restEmirMockMvc.perform(get("/api/emirs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmir() throws Exception {
        // Initialize the database
        emirRepository.saveAndFlush(emir);
        int databaseSizeBeforeUpdate = emirRepository.findAll().size();

        // Update the emir
        Emir updatedEmir = emirRepository.findOne(emir.getId());
        // Disconnect from session so that the updates on updatedEmir are not directly saved in db
        em.detach(updatedEmir);
        updatedEmir
            .statu(UPDATED_STATU);
        EmirDTO emirDTO = emirMapper.toDto(updatedEmir);

        restEmirMockMvc.perform(put("/api/emirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emirDTO)))
            .andExpect(status().isOk());

        // Validate the Emir in the database
        List<Emir> emirList = emirRepository.findAll();
        assertThat(emirList).hasSize(databaseSizeBeforeUpdate);
        Emir testEmir = emirList.get(emirList.size() - 1);
        assertThat(testEmir.getStatu()).isEqualTo(UPDATED_STATU);
    }

    @Test
    @Transactional
    public void updateNonExistingEmir() throws Exception {
        int databaseSizeBeforeUpdate = emirRepository.findAll().size();

        // Create the Emir
        EmirDTO emirDTO = emirMapper.toDto(emir);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmirMockMvc.perform(put("/api/emirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emirDTO)))
            .andExpect(status().isCreated());

        // Validate the Emir in the database
        List<Emir> emirList = emirRepository.findAll();
        assertThat(emirList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmir() throws Exception {
        // Initialize the database
        emirRepository.saveAndFlush(emir);
        int databaseSizeBeforeDelete = emirRepository.findAll().size();

        // Get the emir
        restEmirMockMvc.perform(delete("/api/emirs/{id}", emir.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Emir> emirList = emirRepository.findAll();
        assertThat(emirList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emir.class);
        Emir emir1 = new Emir();
        emir1.setId(1L);
        Emir emir2 = new Emir();
        emir2.setId(emir1.getId());
        assertThat(emir1).isEqualTo(emir2);
        emir2.setId(2L);
        assertThat(emir1).isNotEqualTo(emir2);
        emir1.setId(null);
        assertThat(emir1).isNotEqualTo(emir2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmirDTO.class);
        EmirDTO emirDTO1 = new EmirDTO();
        emirDTO1.setId(1L);
        EmirDTO emirDTO2 = new EmirDTO();
        assertThat(emirDTO1).isNotEqualTo(emirDTO2);
        emirDTO2.setId(emirDTO1.getId());
        assertThat(emirDTO1).isEqualTo(emirDTO2);
        emirDTO2.setId(2L);
        assertThat(emirDTO1).isNotEqualTo(emirDTO2);
        emirDTO1.setId(null);
        assertThat(emirDTO1).isNotEqualTo(emirDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emirMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emirMapper.fromId(null)).isNull();
    }
}
