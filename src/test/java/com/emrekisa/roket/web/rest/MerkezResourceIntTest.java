package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Merkez;
import com.emrekisa.roket.repository.MerkezRepository;
import com.emrekisa.roket.service.MerkezService;
import com.emrekisa.roket.service.dto.MerkezDTO;
import com.emrekisa.roket.service.mapper.MerkezMapper;
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
 * Test class for the MerkezResource REST controller.
 *
 * @see MerkezResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class MerkezResourceIntTest {

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    @Autowired
    private MerkezRepository merkezRepository;

    @Autowired
    private MerkezMapper merkezMapper;

    @Autowired
    private MerkezService merkezService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMerkezMockMvc;

    private Merkez merkez;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MerkezResource merkezResource = new MerkezResource(merkezService);
        this.restMerkezMockMvc = MockMvcBuilders.standaloneSetup(merkezResource)
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
    public static Merkez createEntity(EntityManager em) {
        Merkez merkez = new Merkez()
            .adi(DEFAULT_ADI);
        return merkez;
    }

    @Before
    public void initTest() {
        merkez = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerkez() throws Exception {
        int databaseSizeBeforeCreate = merkezRepository.findAll().size();

        // Create the Merkez
        MerkezDTO merkezDTO = merkezMapper.toDto(merkez);
        restMerkezMockMvc.perform(post("/api/merkezs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merkezDTO)))
            .andExpect(status().isCreated());

        // Validate the Merkez in the database
        List<Merkez> merkezList = merkezRepository.findAll();
        assertThat(merkezList).hasSize(databaseSizeBeforeCreate + 1);
        Merkez testMerkez = merkezList.get(merkezList.size() - 1);
        assertThat(testMerkez.getAdi()).isEqualTo(DEFAULT_ADI);
    }

    @Test
    @Transactional
    public void createMerkezWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = merkezRepository.findAll().size();

        // Create the Merkez with an existing ID
        merkez.setId(1L);
        MerkezDTO merkezDTO = merkezMapper.toDto(merkez);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerkezMockMvc.perform(post("/api/merkezs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merkezDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Merkez in the database
        List<Merkez> merkezList = merkezRepository.findAll();
        assertThat(merkezList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = merkezRepository.findAll().size();
        // set the field null
        merkez.setAdi(null);

        // Create the Merkez, which fails.
        MerkezDTO merkezDTO = merkezMapper.toDto(merkez);

        restMerkezMockMvc.perform(post("/api/merkezs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merkezDTO)))
            .andExpect(status().isBadRequest());

        List<Merkez> merkezList = merkezRepository.findAll();
        assertThat(merkezList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMerkezs() throws Exception {
        // Initialize the database
        merkezRepository.saveAndFlush(merkez);

        // Get all the merkezList
        restMerkezMockMvc.perform(get("/api/merkezs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merkez.getId().intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI.toString())));
    }

    @Test
    @Transactional
    public void getMerkez() throws Exception {
        // Initialize the database
        merkezRepository.saveAndFlush(merkez);

        // Get the merkez
        restMerkezMockMvc.perform(get("/api/merkezs/{id}", merkez.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merkez.getId().intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMerkez() throws Exception {
        // Get the merkez
        restMerkezMockMvc.perform(get("/api/merkezs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerkez() throws Exception {
        // Initialize the database
        merkezRepository.saveAndFlush(merkez);
        int databaseSizeBeforeUpdate = merkezRepository.findAll().size();

        // Update the merkez
        Merkez updatedMerkez = merkezRepository.findOne(merkez.getId());
        // Disconnect from session so that the updates on updatedMerkez are not directly saved in db
        em.detach(updatedMerkez);
        updatedMerkez
            .adi(UPDATED_ADI);
        MerkezDTO merkezDTO = merkezMapper.toDto(updatedMerkez);

        restMerkezMockMvc.perform(put("/api/merkezs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merkezDTO)))
            .andExpect(status().isOk());

        // Validate the Merkez in the database
        List<Merkez> merkezList = merkezRepository.findAll();
        assertThat(merkezList).hasSize(databaseSizeBeforeUpdate);
        Merkez testMerkez = merkezList.get(merkezList.size() - 1);
        assertThat(testMerkez.getAdi()).isEqualTo(UPDATED_ADI);
    }

    @Test
    @Transactional
    public void updateNonExistingMerkez() throws Exception {
        int databaseSizeBeforeUpdate = merkezRepository.findAll().size();

        // Create the Merkez
        MerkezDTO merkezDTO = merkezMapper.toDto(merkez);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMerkezMockMvc.perform(put("/api/merkezs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merkezDTO)))
            .andExpect(status().isCreated());

        // Validate the Merkez in the database
        List<Merkez> merkezList = merkezRepository.findAll();
        assertThat(merkezList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMerkez() throws Exception {
        // Initialize the database
        merkezRepository.saveAndFlush(merkez);
        int databaseSizeBeforeDelete = merkezRepository.findAll().size();

        // Get the merkez
        restMerkezMockMvc.perform(delete("/api/merkezs/{id}", merkez.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Merkez> merkezList = merkezRepository.findAll();
        assertThat(merkezList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Merkez.class);
        Merkez merkez1 = new Merkez();
        merkez1.setId(1L);
        Merkez merkez2 = new Merkez();
        merkez2.setId(merkez1.getId());
        assertThat(merkez1).isEqualTo(merkez2);
        merkez2.setId(2L);
        assertThat(merkez1).isNotEqualTo(merkez2);
        merkez1.setId(null);
        assertThat(merkez1).isNotEqualTo(merkez2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MerkezDTO.class);
        MerkezDTO merkezDTO1 = new MerkezDTO();
        merkezDTO1.setId(1L);
        MerkezDTO merkezDTO2 = new MerkezDTO();
        assertThat(merkezDTO1).isNotEqualTo(merkezDTO2);
        merkezDTO2.setId(merkezDTO1.getId());
        assertThat(merkezDTO1).isEqualTo(merkezDTO2);
        merkezDTO2.setId(2L);
        assertThat(merkezDTO1).isNotEqualTo(merkezDTO2);
        merkezDTO1.setId(null);
        assertThat(merkezDTO1).isNotEqualTo(merkezDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(merkezMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(merkezMapper.fromId(null)).isNull();
    }
}
