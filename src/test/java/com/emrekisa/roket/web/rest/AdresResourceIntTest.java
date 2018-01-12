package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Adres;
import com.emrekisa.roket.repository.AdresRepository;
import com.emrekisa.roket.service.AdresService;
import com.emrekisa.roket.service.dto.AdresDTO;
import com.emrekisa.roket.service.mapper.AdresMapper;
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
 * Test class for the AdresResource REST controller.
 *
 * @see AdresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class AdresResourceIntTest {

    private static final String DEFAULT_BBK = "AAAAAAAAAA";
    private static final String UPDATED_BBK = "BBBBBBBBBB";

    private static final String DEFAULT_ICKAPI_NO = "AAAAAAAAAA";
    private static final String UPDATED_ICKAPI_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DISKAPI_NO = "AAAAAAAAAA";
    private static final String UPDATED_DISKAPI_NO = "BBBBBBBBBB";

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_SOKAK = "AAAAAAAAAA";
    private static final String UPDATED_SOKAK = "BBBBBBBBBB";

    private static final String DEFAULT_CADDE = "AAAAAAAAAA";
    private static final String UPDATED_CADDE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRES_TARIFI = "AAAAAAAAAA";
    private static final String UPDATED_ADRES_TARIFI = "BBBBBBBBBB";

    @Autowired
    private AdresRepository adresRepository;

    @Autowired
    private AdresMapper adresMapper;

    @Autowired
    private AdresService adresService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdresMockMvc;

    private Adres adres;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdresResource adresResource = new AdresResource(adresService);
        this.restAdresMockMvc = MockMvcBuilders.standaloneSetup(adresResource)
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
    public static Adres createEntity(EntityManager em) {
        Adres adres = new Adres()
            .bbk(DEFAULT_BBK)
            .ickapiNo(DEFAULT_ICKAPI_NO)
            .diskapiNo(DEFAULT_DISKAPI_NO)
            .site(DEFAULT_SITE)
            .sokak(DEFAULT_SOKAK)
            .cadde(DEFAULT_CADDE)
            .adresTarifi(DEFAULT_ADRES_TARIFI);
        return adres;
    }

    @Before
    public void initTest() {
        adres = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdres() throws Exception {
        int databaseSizeBeforeCreate = adresRepository.findAll().size();

        // Create the Adres
        AdresDTO adresDTO = adresMapper.toDto(adres);
        restAdresMockMvc.perform(post("/api/adres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresDTO)))
            .andExpect(status().isCreated());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeCreate + 1);
        Adres testAdres = adresList.get(adresList.size() - 1);
        assertThat(testAdres.getBbk()).isEqualTo(DEFAULT_BBK);
        assertThat(testAdres.getIckapiNo()).isEqualTo(DEFAULT_ICKAPI_NO);
        assertThat(testAdres.getDiskapiNo()).isEqualTo(DEFAULT_DISKAPI_NO);
        assertThat(testAdres.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testAdres.getSokak()).isEqualTo(DEFAULT_SOKAK);
        assertThat(testAdres.getCadde()).isEqualTo(DEFAULT_CADDE);
        assertThat(testAdres.getAdresTarifi()).isEqualTo(DEFAULT_ADRES_TARIFI);
    }

    @Test
    @Transactional
    public void createAdresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adresRepository.findAll().size();

        // Create the Adres with an existing ID
        adres.setId(1L);
        AdresDTO adresDTO = adresMapper.toDto(adres);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresMockMvc.perform(post("/api/adres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        // Get all the adresList
        restAdresMockMvc.perform(get("/api/adres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adres.getId().intValue())))
            .andExpect(jsonPath("$.[*].bbk").value(hasItem(DEFAULT_BBK.toString())))
            .andExpect(jsonPath("$.[*].ickapiNo").value(hasItem(DEFAULT_ICKAPI_NO.toString())))
            .andExpect(jsonPath("$.[*].diskapiNo").value(hasItem(DEFAULT_DISKAPI_NO.toString())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE.toString())))
            .andExpect(jsonPath("$.[*].sokak").value(hasItem(DEFAULT_SOKAK.toString())))
            .andExpect(jsonPath("$.[*].cadde").value(hasItem(DEFAULT_CADDE.toString())))
            .andExpect(jsonPath("$.[*].adresTarifi").value(hasItem(DEFAULT_ADRES_TARIFI.toString())));
    }

    @Test
    @Transactional
    public void getAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        // Get the adres
        restAdresMockMvc.perform(get("/api/adres/{id}", adres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adres.getId().intValue()))
            .andExpect(jsonPath("$.bbk").value(DEFAULT_BBK.toString()))
            .andExpect(jsonPath("$.ickapiNo").value(DEFAULT_ICKAPI_NO.toString()))
            .andExpect(jsonPath("$.diskapiNo").value(DEFAULT_DISKAPI_NO.toString()))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE.toString()))
            .andExpect(jsonPath("$.sokak").value(DEFAULT_SOKAK.toString()))
            .andExpect(jsonPath("$.cadde").value(DEFAULT_CADDE.toString()))
            .andExpect(jsonPath("$.adresTarifi").value(DEFAULT_ADRES_TARIFI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdres() throws Exception {
        // Get the adres
        restAdresMockMvc.perform(get("/api/adres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);
        int databaseSizeBeforeUpdate = adresRepository.findAll().size();

        // Update the adres
        Adres updatedAdres = adresRepository.findOne(adres.getId());
        // Disconnect from session so that the updates on updatedAdres are not directly saved in db
        em.detach(updatedAdres);
        updatedAdres
            .bbk(UPDATED_BBK)
            .ickapiNo(UPDATED_ICKAPI_NO)
            .diskapiNo(UPDATED_DISKAPI_NO)
            .site(UPDATED_SITE)
            .sokak(UPDATED_SOKAK)
            .cadde(UPDATED_CADDE)
            .adresTarifi(UPDATED_ADRES_TARIFI);
        AdresDTO adresDTO = adresMapper.toDto(updatedAdres);

        restAdresMockMvc.perform(put("/api/adres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresDTO)))
            .andExpect(status().isOk());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
        Adres testAdres = adresList.get(adresList.size() - 1);
        assertThat(testAdres.getBbk()).isEqualTo(UPDATED_BBK);
        assertThat(testAdres.getIckapiNo()).isEqualTo(UPDATED_ICKAPI_NO);
        assertThat(testAdres.getDiskapiNo()).isEqualTo(UPDATED_DISKAPI_NO);
        assertThat(testAdres.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testAdres.getSokak()).isEqualTo(UPDATED_SOKAK);
        assertThat(testAdres.getCadde()).isEqualTo(UPDATED_CADDE);
        assertThat(testAdres.getAdresTarifi()).isEqualTo(UPDATED_ADRES_TARIFI);
    }

    @Test
    @Transactional
    public void updateNonExistingAdres() throws Exception {
        int databaseSizeBeforeUpdate = adresRepository.findAll().size();

        // Create the Adres
        AdresDTO adresDTO = adresMapper.toDto(adres);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdresMockMvc.perform(put("/api/adres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresDTO)))
            .andExpect(status().isCreated());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);
        int databaseSizeBeforeDelete = adresRepository.findAll().size();

        // Get the adres
        restAdresMockMvc.perform(delete("/api/adres/{id}", adres.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adres.class);
        Adres adres1 = new Adres();
        adres1.setId(1L);
        Adres adres2 = new Adres();
        adres2.setId(adres1.getId());
        assertThat(adres1).isEqualTo(adres2);
        adres2.setId(2L);
        assertThat(adres1).isNotEqualTo(adres2);
        adres1.setId(null);
        assertThat(adres1).isNotEqualTo(adres2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdresDTO.class);
        AdresDTO adresDTO1 = new AdresDTO();
        adresDTO1.setId(1L);
        AdresDTO adresDTO2 = new AdresDTO();
        assertThat(adresDTO1).isNotEqualTo(adresDTO2);
        adresDTO2.setId(adresDTO1.getId());
        assertThat(adresDTO1).isEqualTo(adresDTO2);
        adresDTO2.setId(2L);
        assertThat(adresDTO1).isNotEqualTo(adresDTO2);
        adresDTO1.setId(null);
        assertThat(adresDTO1).isNotEqualTo(adresDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adresMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adresMapper.fromId(null)).isNull();
    }
}
