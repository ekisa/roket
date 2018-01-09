package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Ilce;
import com.emrekisa.roket.repository.IlceRepository;
import com.emrekisa.roket.service.IlceService;
import com.emrekisa.roket.service.dto.IlceDTO;
import com.emrekisa.roket.service.mapper.IlceMapper;
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
 * Test class for the IlceResource REST controller.
 *
 * @see IlceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class IlceResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    @Autowired
    private IlceRepository ilceRepository;

    @Autowired
    private IlceMapper ilceMapper;

    @Autowired
    private IlceService ilceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIlceMockMvc;

    private Ilce ilce;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IlceResource ilceResource = new IlceResource(ilceService);
        this.restIlceMockMvc = MockMvcBuilders.standaloneSetup(ilceResource)
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
    public static Ilce createEntity(EntityManager em) {
        Ilce ilce = new Ilce()
            .ad(DEFAULT_AD);
        return ilce;
    }

    @Before
    public void initTest() {
        ilce = createEntity(em);
    }

    @Test
    @Transactional
    public void createIlce() throws Exception {
        int databaseSizeBeforeCreate = ilceRepository.findAll().size();

        // Create the Ilce
        IlceDTO ilceDTO = ilceMapper.toDto(ilce);
        restIlceMockMvc.perform(post("/api/ilces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ilceDTO)))
            .andExpect(status().isCreated());

        // Validate the Ilce in the database
        List<Ilce> ilceList = ilceRepository.findAll();
        assertThat(ilceList).hasSize(databaseSizeBeforeCreate + 1);
        Ilce testIlce = ilceList.get(ilceList.size() - 1);
        assertThat(testIlce.getAd()).isEqualTo(DEFAULT_AD);
    }

    @Test
    @Transactional
    public void createIlceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ilceRepository.findAll().size();

        // Create the Ilce with an existing ID
        ilce.setId(1L);
        IlceDTO ilceDTO = ilceMapper.toDto(ilce);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIlceMockMvc.perform(post("/api/ilces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ilceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ilce in the database
        List<Ilce> ilceList = ilceRepository.findAll();
        assertThat(ilceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = ilceRepository.findAll().size();
        // set the field null
        ilce.setAd(null);

        // Create the Ilce, which fails.
        IlceDTO ilceDTO = ilceMapper.toDto(ilce);

        restIlceMockMvc.perform(post("/api/ilces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ilceDTO)))
            .andExpect(status().isBadRequest());

        List<Ilce> ilceList = ilceRepository.findAll();
        assertThat(ilceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIlces() throws Exception {
        // Initialize the database
        ilceRepository.saveAndFlush(ilce);

        // Get all the ilceList
        restIlceMockMvc.perform(get("/api/ilces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ilce.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())));
    }

    @Test
    @Transactional
    public void getIlce() throws Exception {
        // Initialize the database
        ilceRepository.saveAndFlush(ilce);

        // Get the ilce
        restIlceMockMvc.perform(get("/api/ilces/{id}", ilce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ilce.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIlce() throws Exception {
        // Get the ilce
        restIlceMockMvc.perform(get("/api/ilces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIlce() throws Exception {
        // Initialize the database
        ilceRepository.saveAndFlush(ilce);
        int databaseSizeBeforeUpdate = ilceRepository.findAll().size();

        // Update the ilce
        Ilce updatedIlce = ilceRepository.findOne(ilce.getId());
        // Disconnect from session so that the updates on updatedIlce are not directly saved in db
        em.detach(updatedIlce);
        updatedIlce
            .ad(UPDATED_AD);
        IlceDTO ilceDTO = ilceMapper.toDto(updatedIlce);

        restIlceMockMvc.perform(put("/api/ilces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ilceDTO)))
            .andExpect(status().isOk());

        // Validate the Ilce in the database
        List<Ilce> ilceList = ilceRepository.findAll();
        assertThat(ilceList).hasSize(databaseSizeBeforeUpdate);
        Ilce testIlce = ilceList.get(ilceList.size() - 1);
        assertThat(testIlce.getAd()).isEqualTo(UPDATED_AD);
    }

    @Test
    @Transactional
    public void updateNonExistingIlce() throws Exception {
        int databaseSizeBeforeUpdate = ilceRepository.findAll().size();

        // Create the Ilce
        IlceDTO ilceDTO = ilceMapper.toDto(ilce);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIlceMockMvc.perform(put("/api/ilces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ilceDTO)))
            .andExpect(status().isCreated());

        // Validate the Ilce in the database
        List<Ilce> ilceList = ilceRepository.findAll();
        assertThat(ilceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIlce() throws Exception {
        // Initialize the database
        ilceRepository.saveAndFlush(ilce);
        int databaseSizeBeforeDelete = ilceRepository.findAll().size();

        // Get the ilce
        restIlceMockMvc.perform(delete("/api/ilces/{id}", ilce.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ilce> ilceList = ilceRepository.findAll();
        assertThat(ilceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ilce.class);
        Ilce ilce1 = new Ilce();
        ilce1.setId(1L);
        Ilce ilce2 = new Ilce();
        ilce2.setId(ilce1.getId());
        assertThat(ilce1).isEqualTo(ilce2);
        ilce2.setId(2L);
        assertThat(ilce1).isNotEqualTo(ilce2);
        ilce1.setId(null);
        assertThat(ilce1).isNotEqualTo(ilce2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IlceDTO.class);
        IlceDTO ilceDTO1 = new IlceDTO();
        ilceDTO1.setId(1L);
        IlceDTO ilceDTO2 = new IlceDTO();
        assertThat(ilceDTO1).isNotEqualTo(ilceDTO2);
        ilceDTO2.setId(ilceDTO1.getId());
        assertThat(ilceDTO1).isEqualTo(ilceDTO2);
        ilceDTO2.setId(2L);
        assertThat(ilceDTO1).isNotEqualTo(ilceDTO2);
        ilceDTO1.setId(null);
        assertThat(ilceDTO1).isNotEqualTo(ilceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ilceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ilceMapper.fromId(null)).isNull();
    }
}
