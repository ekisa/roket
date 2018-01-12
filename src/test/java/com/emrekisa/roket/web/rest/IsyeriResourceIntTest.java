package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Isyeri;
import com.emrekisa.roket.repository.IsyeriRepository;
import com.emrekisa.roket.service.IsyeriService;
import com.emrekisa.roket.service.dto.IsyeriDTO;
import com.emrekisa.roket.service.mapper.IsyeriMapper;
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
 * Test class for the IsyeriResource REST controller.
 *
 * @see IsyeriResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class IsyeriResourceIntTest {

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    @Autowired
    private IsyeriRepository isyeriRepository;

    @Autowired
    private IsyeriMapper isyeriMapper;

    @Autowired
    private IsyeriService isyeriService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIsyeriMockMvc;

    private Isyeri isyeri;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IsyeriResource isyeriResource = new IsyeriResource(isyeriService);
        this.restIsyeriMockMvc = MockMvcBuilders.standaloneSetup(isyeriResource)
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
    public static Isyeri createEntity(EntityManager em) {
        Isyeri isyeri = new Isyeri()
            .adi(DEFAULT_ADI)
            .telefon(DEFAULT_TELEFON)
            .aciklama(DEFAULT_ACIKLAMA);
        return isyeri;
    }

    @Before
    public void initTest() {
        isyeri = createEntity(em);
    }

    @Test
    @Transactional
    public void createIsyeri() throws Exception {
        int databaseSizeBeforeCreate = isyeriRepository.findAll().size();

        // Create the Isyeri
        IsyeriDTO isyeriDTO = isyeriMapper.toDto(isyeri);
        restIsyeriMockMvc.perform(post("/api/isyeris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isyeriDTO)))
            .andExpect(status().isCreated());

        // Validate the Isyeri in the database
        List<Isyeri> isyeriList = isyeriRepository.findAll();
        assertThat(isyeriList).hasSize(databaseSizeBeforeCreate + 1);
        Isyeri testIsyeri = isyeriList.get(isyeriList.size() - 1);
        assertThat(testIsyeri.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testIsyeri.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testIsyeri.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
    }

    @Test
    @Transactional
    public void createIsyeriWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = isyeriRepository.findAll().size();

        // Create the Isyeri with an existing ID
        isyeri.setId(1L);
        IsyeriDTO isyeriDTO = isyeriMapper.toDto(isyeri);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIsyeriMockMvc.perform(post("/api/isyeris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isyeriDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Isyeri in the database
        List<Isyeri> isyeriList = isyeriRepository.findAll();
        assertThat(isyeriList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = isyeriRepository.findAll().size();
        // set the field null
        isyeri.setAdi(null);

        // Create the Isyeri, which fails.
        IsyeriDTO isyeriDTO = isyeriMapper.toDto(isyeri);

        restIsyeriMockMvc.perform(post("/api/isyeris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isyeriDTO)))
            .andExpect(status().isBadRequest());

        List<Isyeri> isyeriList = isyeriRepository.findAll();
        assertThat(isyeriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonIsRequired() throws Exception {
        int databaseSizeBeforeTest = isyeriRepository.findAll().size();
        // set the field null
        isyeri.setTelefon(null);

        // Create the Isyeri, which fails.
        IsyeriDTO isyeriDTO = isyeriMapper.toDto(isyeri);

        restIsyeriMockMvc.perform(post("/api/isyeris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isyeriDTO)))
            .andExpect(status().isBadRequest());

        List<Isyeri> isyeriList = isyeriRepository.findAll();
        assertThat(isyeriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIsyeris() throws Exception {
        // Initialize the database
        isyeriRepository.saveAndFlush(isyeri);

        // Get all the isyeriList
        restIsyeriMockMvc.perform(get("/api/isyeris?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isyeri.getId().intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())));
    }

    @Test
    @Transactional
    public void getIsyeri() throws Exception {
        // Initialize the database
        isyeriRepository.saveAndFlush(isyeri);

        // Get the isyeri
        restIsyeriMockMvc.perform(get("/api/isyeris/{id}", isyeri.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(isyeri.getId().intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIsyeri() throws Exception {
        // Get the isyeri
        restIsyeriMockMvc.perform(get("/api/isyeris/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIsyeri() throws Exception {
        // Initialize the database
        isyeriRepository.saveAndFlush(isyeri);
        int databaseSizeBeforeUpdate = isyeriRepository.findAll().size();

        // Update the isyeri
        Isyeri updatedIsyeri = isyeriRepository.findOne(isyeri.getId());
        // Disconnect from session so that the updates on updatedIsyeri are not directly saved in db
        em.detach(updatedIsyeri);
        updatedIsyeri
            .adi(UPDATED_ADI)
            .telefon(UPDATED_TELEFON)
            .aciklama(UPDATED_ACIKLAMA);
        IsyeriDTO isyeriDTO = isyeriMapper.toDto(updatedIsyeri);

        restIsyeriMockMvc.perform(put("/api/isyeris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isyeriDTO)))
            .andExpect(status().isOk());

        // Validate the Isyeri in the database
        List<Isyeri> isyeriList = isyeriRepository.findAll();
        assertThat(isyeriList).hasSize(databaseSizeBeforeUpdate);
        Isyeri testIsyeri = isyeriList.get(isyeriList.size() - 1);
        assertThat(testIsyeri.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testIsyeri.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testIsyeri.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void updateNonExistingIsyeri() throws Exception {
        int databaseSizeBeforeUpdate = isyeriRepository.findAll().size();

        // Create the Isyeri
        IsyeriDTO isyeriDTO = isyeriMapper.toDto(isyeri);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIsyeriMockMvc.perform(put("/api/isyeris")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isyeriDTO)))
            .andExpect(status().isCreated());

        // Validate the Isyeri in the database
        List<Isyeri> isyeriList = isyeriRepository.findAll();
        assertThat(isyeriList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIsyeri() throws Exception {
        // Initialize the database
        isyeriRepository.saveAndFlush(isyeri);
        int databaseSizeBeforeDelete = isyeriRepository.findAll().size();

        // Get the isyeri
        restIsyeriMockMvc.perform(delete("/api/isyeris/{id}", isyeri.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Isyeri> isyeriList = isyeriRepository.findAll();
        assertThat(isyeriList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Isyeri.class);
        Isyeri isyeri1 = new Isyeri();
        isyeri1.setId(1L);
        Isyeri isyeri2 = new Isyeri();
        isyeri2.setId(isyeri1.getId());
        assertThat(isyeri1).isEqualTo(isyeri2);
        isyeri2.setId(2L);
        assertThat(isyeri1).isNotEqualTo(isyeri2);
        isyeri1.setId(null);
        assertThat(isyeri1).isNotEqualTo(isyeri2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsyeriDTO.class);
        IsyeriDTO isyeriDTO1 = new IsyeriDTO();
        isyeriDTO1.setId(1L);
        IsyeriDTO isyeriDTO2 = new IsyeriDTO();
        assertThat(isyeriDTO1).isNotEqualTo(isyeriDTO2);
        isyeriDTO2.setId(isyeriDTO1.getId());
        assertThat(isyeriDTO1).isEqualTo(isyeriDTO2);
        isyeriDTO2.setId(2L);
        assertThat(isyeriDTO1).isNotEqualTo(isyeriDTO2);
        isyeriDTO1.setId(null);
        assertThat(isyeriDTO1).isNotEqualTo(isyeriDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(isyeriMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(isyeriMapper.fromId(null)).isNull();
    }
}
