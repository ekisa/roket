package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Musteri;
import com.emrekisa.roket.repository.MusteriRepository;
import com.emrekisa.roket.service.MusteriService;
import com.emrekisa.roket.service.dto.MusteriDTO;
import com.emrekisa.roket.service.mapper.MusteriMapper;
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
 * Test class for the MusteriResource REST controller.
 *
 * @see MusteriResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class MusteriResourceIntTest {

    private static final String DEFAULT_UNVAN = "AAAAAAAAAA";
    private static final String UPDATED_UNVAN = "BBBBBBBBBB";

    private static final String DEFAULT_EPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_EPOSTA = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    @Autowired
    private MusteriRepository musteriRepository;

    @Autowired
    private MusteriMapper musteriMapper;

    @Autowired
    private MusteriService musteriService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMusteriMockMvc;

    private Musteri musteri;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MusteriResource musteriResource = new MusteriResource(musteriService);
        this.restMusteriMockMvc = MockMvcBuilders.standaloneSetup(musteriResource)
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
    public static Musteri createEntity(EntityManager em) {
        Musteri musteri = new Musteri()
            .unvan(DEFAULT_UNVAN)
            .eposta(DEFAULT_EPOSTA)
            .telefon(DEFAULT_TELEFON);
        return musteri;
    }

    @Before
    public void initTest() {
        musteri = createEntity(em);
    }

    @Test
    @Transactional
    public void createMusteri() throws Exception {
        int databaseSizeBeforeCreate = musteriRepository.findAll().size();

        // Create the Musteri
        MusteriDTO musteriDTO = musteriMapper.toDto(musteri);
        restMusteriMockMvc.perform(post("/api/musteris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musteriDTO)))
            .andExpect(status().isCreated());

        // Validate the Musteri in the database
        List<Musteri> musteriList = musteriRepository.findAll();
        assertThat(musteriList).hasSize(databaseSizeBeforeCreate + 1);
        Musteri testMusteri = musteriList.get(musteriList.size() - 1);
        assertThat(testMusteri.getUnvan()).isEqualTo(DEFAULT_UNVAN);
        assertThat(testMusteri.getEposta()).isEqualTo(DEFAULT_EPOSTA);
        assertThat(testMusteri.getTelefon()).isEqualTo(DEFAULT_TELEFON);
    }

    @Test
    @Transactional
    public void createMusteriWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = musteriRepository.findAll().size();

        // Create the Musteri with an existing ID
        musteri.setId(1L);
        MusteriDTO musteriDTO = musteriMapper.toDto(musteri);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMusteriMockMvc.perform(post("/api/musteris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musteriDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Musteri in the database
        List<Musteri> musteriList = musteriRepository.findAll();
        assertThat(musteriList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUnvanIsRequired() throws Exception {
        int databaseSizeBeforeTest = musteriRepository.findAll().size();
        // set the field null
        musteri.setUnvan(null);

        // Create the Musteri, which fails.
        MusteriDTO musteriDTO = musteriMapper.toDto(musteri);

        restMusteriMockMvc.perform(post("/api/musteris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musteriDTO)))
            .andExpect(status().isBadRequest());

        List<Musteri> musteriList = musteriRepository.findAll();
        assertThat(musteriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEpostaIsRequired() throws Exception {
        int databaseSizeBeforeTest = musteriRepository.findAll().size();
        // set the field null
        musteri.setEposta(null);

        // Create the Musteri, which fails.
        MusteriDTO musteriDTO = musteriMapper.toDto(musteri);

        restMusteriMockMvc.perform(post("/api/musteris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musteriDTO)))
            .andExpect(status().isBadRequest());

        List<Musteri> musteriList = musteriRepository.findAll();
        assertThat(musteriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonIsRequired() throws Exception {
        int databaseSizeBeforeTest = musteriRepository.findAll().size();
        // set the field null
        musteri.setTelefon(null);

        // Create the Musteri, which fails.
        MusteriDTO musteriDTO = musteriMapper.toDto(musteri);

        restMusteriMockMvc.perform(post("/api/musteris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musteriDTO)))
            .andExpect(status().isBadRequest());

        List<Musteri> musteriList = musteriRepository.findAll();
        assertThat(musteriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMusteris() throws Exception {
        // Initialize the database
        musteriRepository.saveAndFlush(musteri);

        // Get all the musteriList
        restMusteriMockMvc.perform(get("/api/musteris?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(musteri.getId().intValue())))
            .andExpect(jsonPath("$.[*].unvan").value(hasItem(DEFAULT_UNVAN.toString())))
            .andExpect(jsonPath("$.[*].eposta").value(hasItem(DEFAULT_EPOSTA.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())));
    }

    @Test
    @Transactional
    public void getMusteri() throws Exception {
        // Initialize the database
        musteriRepository.saveAndFlush(musteri);

        // Get the musteri
        restMusteriMockMvc.perform(get("/api/musteris/{id}", musteri.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(musteri.getId().intValue()))
            .andExpect(jsonPath("$.unvan").value(DEFAULT_UNVAN.toString()))
            .andExpect(jsonPath("$.eposta").value(DEFAULT_EPOSTA.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMusteri() throws Exception {
        // Get the musteri
        restMusteriMockMvc.perform(get("/api/musteris/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMusteri() throws Exception {
        // Initialize the database
        musteriRepository.saveAndFlush(musteri);
        int databaseSizeBeforeUpdate = musteriRepository.findAll().size();

        // Update the musteri
        Musteri updatedMusteri = musteriRepository.findOne(musteri.getId());
        // Disconnect from session so that the updates on updatedMusteri are not directly saved in db
        em.detach(updatedMusteri);
        updatedMusteri
            .unvan(UPDATED_UNVAN)
            .eposta(UPDATED_EPOSTA)
            .telefon(UPDATED_TELEFON);
        MusteriDTO musteriDTO = musteriMapper.toDto(updatedMusteri);

        restMusteriMockMvc.perform(put("/api/musteris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musteriDTO)))
            .andExpect(status().isOk());

        // Validate the Musteri in the database
        List<Musteri> musteriList = musteriRepository.findAll();
        assertThat(musteriList).hasSize(databaseSizeBeforeUpdate);
        Musteri testMusteri = musteriList.get(musteriList.size() - 1);
        assertThat(testMusteri.getUnvan()).isEqualTo(UPDATED_UNVAN);
        assertThat(testMusteri.getEposta()).isEqualTo(UPDATED_EPOSTA);
        assertThat(testMusteri.getTelefon()).isEqualTo(UPDATED_TELEFON);
    }

    @Test
    @Transactional
    public void updateNonExistingMusteri() throws Exception {
        int databaseSizeBeforeUpdate = musteriRepository.findAll().size();

        // Create the Musteri
        MusteriDTO musteriDTO = musteriMapper.toDto(musteri);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMusteriMockMvc.perform(put("/api/musteris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musteriDTO)))
            .andExpect(status().isCreated());

        // Validate the Musteri in the database
        List<Musteri> musteriList = musteriRepository.findAll();
        assertThat(musteriList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMusteri() throws Exception {
        // Initialize the database
        musteriRepository.saveAndFlush(musteri);
        int databaseSizeBeforeDelete = musteriRepository.findAll().size();

        // Get the musteri
        restMusteriMockMvc.perform(delete("/api/musteris/{id}", musteri.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Musteri> musteriList = musteriRepository.findAll();
        assertThat(musteriList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Musteri.class);
        Musteri musteri1 = new Musteri();
        musteri1.setId(1L);
        Musteri musteri2 = new Musteri();
        musteri2.setId(musteri1.getId());
        assertThat(musteri1).isEqualTo(musteri2);
        musteri2.setId(2L);
        assertThat(musteri1).isNotEqualTo(musteri2);
        musteri1.setId(null);
        assertThat(musteri1).isNotEqualTo(musteri2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MusteriDTO.class);
        MusteriDTO musteriDTO1 = new MusteriDTO();
        musteriDTO1.setId(1L);
        MusteriDTO musteriDTO2 = new MusteriDTO();
        assertThat(musteriDTO1).isNotEqualTo(musteriDTO2);
        musteriDTO2.setId(musteriDTO1.getId());
        assertThat(musteriDTO1).isEqualTo(musteriDTO2);
        musteriDTO2.setId(2L);
        assertThat(musteriDTO1).isNotEqualTo(musteriDTO2);
        musteriDTO1.setId(null);
        assertThat(musteriDTO1).isNotEqualTo(musteriDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(musteriMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(musteriMapper.fromId(null)).isNull();
    }
}
