package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Isci;
import com.emrekisa.roket.repository.IsciRepository;
import com.emrekisa.roket.service.IsciService;
import com.emrekisa.roket.service.dto.IsciDTO;
import com.emrekisa.roket.service.mapper.IsciMapper;
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
 * Test class for the IsciResource REST controller.
 *
 * @see IsciResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class IsciResourceIntTest {

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_SOYADI = "AAAAAAAAAA";
    private static final String UPDATED_SOYADI = "BBBBBBBBBB";

    private static final String DEFAULT_EPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_EPOSTA = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final Long DEFAULT_TEMINAT_TUTARI = 1L;
    private static final Long UPDATED_TEMINAT_TUTARI = 2L;

    private static final Long DEFAULT_MAAS = 1L;
    private static final Long UPDATED_MAAS = 2L;

    private static final String DEFAULT_SICIL = "AAAAAAAAAA";
    private static final String UPDATED_SICIL = "BBBBBBBBBB";

    private static final String DEFAULT_TCKN = "AAAAAAAAAA";
    private static final String UPDATED_TCKN = "BBBBBBBBBB";

    private static final String DEFAULT_ZIMMETLI_MALLAR = "AAAAAAAAAA";
    private static final String UPDATED_ZIMMETLI_MALLAR = "BBBBBBBBBB";

    @Autowired
    private IsciRepository isciRepository;

    @Autowired
    private IsciMapper isciMapper;

    @Autowired
    private IsciService isciService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIsciMockMvc;

    private Isci isci;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IsciResource isciResource = new IsciResource(isciService);
        this.restIsciMockMvc = MockMvcBuilders.standaloneSetup(isciResource)
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
    public static Isci createEntity(EntityManager em) {
        Isci isci = new Isci()
            .adi(DEFAULT_ADI)
            .soyadi(DEFAULT_SOYADI)
            .eposta(DEFAULT_EPOSTA)
            .telefon(DEFAULT_TELEFON)
            .teminatTutari(DEFAULT_TEMINAT_TUTARI)
            .maas(DEFAULT_MAAS)
            .sicil(DEFAULT_SICIL)
            .tckn(DEFAULT_TCKN)
            .zimmetliMallar(DEFAULT_ZIMMETLI_MALLAR);
        return isci;
    }

    @Before
    public void initTest() {
        isci = createEntity(em);
    }

    @Test
    @Transactional
    public void createIsci() throws Exception {
        int databaseSizeBeforeCreate = isciRepository.findAll().size();

        // Create the Isci
        IsciDTO isciDTO = isciMapper.toDto(isci);
        restIsciMockMvc.perform(post("/api/iscis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isciDTO)))
            .andExpect(status().isCreated());

        // Validate the Isci in the database
        List<Isci> isciList = isciRepository.findAll();
        assertThat(isciList).hasSize(databaseSizeBeforeCreate + 1);
        Isci testIsci = isciList.get(isciList.size() - 1);
        assertThat(testIsci.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testIsci.getSoyadi()).isEqualTo(DEFAULT_SOYADI);
        assertThat(testIsci.getEposta()).isEqualTo(DEFAULT_EPOSTA);
        assertThat(testIsci.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testIsci.getTeminatTutari()).isEqualTo(DEFAULT_TEMINAT_TUTARI);
        assertThat(testIsci.getMaas()).isEqualTo(DEFAULT_MAAS);
        assertThat(testIsci.getSicil()).isEqualTo(DEFAULT_SICIL);
        assertThat(testIsci.getTckn()).isEqualTo(DEFAULT_TCKN);
        assertThat(testIsci.getZimmetliMallar()).isEqualTo(DEFAULT_ZIMMETLI_MALLAR);
    }

    @Test
    @Transactional
    public void createIsciWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = isciRepository.findAll().size();

        // Create the Isci with an existing ID
        isci.setId(1L);
        IsciDTO isciDTO = isciMapper.toDto(isci);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIsciMockMvc.perform(post("/api/iscis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isciDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Isci in the database
        List<Isci> isciList = isciRepository.findAll();
        assertThat(isciList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIscis() throws Exception {
        // Initialize the database
        isciRepository.saveAndFlush(isci);

        // Get all the isciList
        restIsciMockMvc.perform(get("/api/iscis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isci.getId().intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI.toString())))
            .andExpect(jsonPath("$.[*].soyadi").value(hasItem(DEFAULT_SOYADI.toString())))
            .andExpect(jsonPath("$.[*].eposta").value(hasItem(DEFAULT_EPOSTA.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].teminatTutari").value(hasItem(DEFAULT_TEMINAT_TUTARI.intValue())))
            .andExpect(jsonPath("$.[*].maas").value(hasItem(DEFAULT_MAAS.intValue())))
            .andExpect(jsonPath("$.[*].sicil").value(hasItem(DEFAULT_SICIL.toString())))
            .andExpect(jsonPath("$.[*].tckn").value(hasItem(DEFAULT_TCKN.toString())))
            .andExpect(jsonPath("$.[*].zimmetliMallar").value(hasItem(DEFAULT_ZIMMETLI_MALLAR.toString())));
    }

    @Test
    @Transactional
    public void getIsci() throws Exception {
        // Initialize the database
        isciRepository.saveAndFlush(isci);

        // Get the isci
        restIsciMockMvc.perform(get("/api/iscis/{id}", isci.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(isci.getId().intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI.toString()))
            .andExpect(jsonPath("$.soyadi").value(DEFAULT_SOYADI.toString()))
            .andExpect(jsonPath("$.eposta").value(DEFAULT_EPOSTA.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.teminatTutari").value(DEFAULT_TEMINAT_TUTARI.intValue()))
            .andExpect(jsonPath("$.maas").value(DEFAULT_MAAS.intValue()))
            .andExpect(jsonPath("$.sicil").value(DEFAULT_SICIL.toString()))
            .andExpect(jsonPath("$.tckn").value(DEFAULT_TCKN.toString()))
            .andExpect(jsonPath("$.zimmetliMallar").value(DEFAULT_ZIMMETLI_MALLAR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIsci() throws Exception {
        // Get the isci
        restIsciMockMvc.perform(get("/api/iscis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIsci() throws Exception {
        // Initialize the database
        isciRepository.saveAndFlush(isci);
        int databaseSizeBeforeUpdate = isciRepository.findAll().size();

        // Update the isci
        Isci updatedIsci = isciRepository.findOne(isci.getId());
        // Disconnect from session so that the updates on updatedIsci are not directly saved in db
        em.detach(updatedIsci);
        updatedIsci
            .adi(UPDATED_ADI)
            .soyadi(UPDATED_SOYADI)
            .eposta(UPDATED_EPOSTA)
            .telefon(UPDATED_TELEFON)
            .teminatTutari(UPDATED_TEMINAT_TUTARI)
            .maas(UPDATED_MAAS)
            .sicil(UPDATED_SICIL)
            .tckn(UPDATED_TCKN)
            .zimmetliMallar(UPDATED_ZIMMETLI_MALLAR);
        IsciDTO isciDTO = isciMapper.toDto(updatedIsci);

        restIsciMockMvc.perform(put("/api/iscis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isciDTO)))
            .andExpect(status().isOk());

        // Validate the Isci in the database
        List<Isci> isciList = isciRepository.findAll();
        assertThat(isciList).hasSize(databaseSizeBeforeUpdate);
        Isci testIsci = isciList.get(isciList.size() - 1);
        assertThat(testIsci.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testIsci.getSoyadi()).isEqualTo(UPDATED_SOYADI);
        assertThat(testIsci.getEposta()).isEqualTo(UPDATED_EPOSTA);
        assertThat(testIsci.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testIsci.getTeminatTutari()).isEqualTo(UPDATED_TEMINAT_TUTARI);
        assertThat(testIsci.getMaas()).isEqualTo(UPDATED_MAAS);
        assertThat(testIsci.getSicil()).isEqualTo(UPDATED_SICIL);
        assertThat(testIsci.getTckn()).isEqualTo(UPDATED_TCKN);
        assertThat(testIsci.getZimmetliMallar()).isEqualTo(UPDATED_ZIMMETLI_MALLAR);
    }

    @Test
    @Transactional
    public void updateNonExistingIsci() throws Exception {
        int databaseSizeBeforeUpdate = isciRepository.findAll().size();

        // Create the Isci
        IsciDTO isciDTO = isciMapper.toDto(isci);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIsciMockMvc.perform(put("/api/iscis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isciDTO)))
            .andExpect(status().isCreated());

        // Validate the Isci in the database
        List<Isci> isciList = isciRepository.findAll();
        assertThat(isciList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIsci() throws Exception {
        // Initialize the database
        isciRepository.saveAndFlush(isci);
        int databaseSizeBeforeDelete = isciRepository.findAll().size();

        // Get the isci
        restIsciMockMvc.perform(delete("/api/iscis/{id}", isci.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Isci> isciList = isciRepository.findAll();
        assertThat(isciList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Isci.class);
        Isci isci1 = new Isci();
        isci1.setId(1L);
        Isci isci2 = new Isci();
        isci2.setId(isci1.getId());
        assertThat(isci1).isEqualTo(isci2);
        isci2.setId(2L);
        assertThat(isci1).isNotEqualTo(isci2);
        isci1.setId(null);
        assertThat(isci1).isNotEqualTo(isci2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsciDTO.class);
        IsciDTO isciDTO1 = new IsciDTO();
        isciDTO1.setId(1L);
        IsciDTO isciDTO2 = new IsciDTO();
        assertThat(isciDTO1).isNotEqualTo(isciDTO2);
        isciDTO2.setId(isciDTO1.getId());
        assertThat(isciDTO1).isEqualTo(isciDTO2);
        isciDTO2.setId(2L);
        assertThat(isciDTO1).isNotEqualTo(isciDTO2);
        isciDTO1.setId(null);
        assertThat(isciDTO1).isNotEqualTo(isciDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(isciMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(isciMapper.fromId(null)).isNull();
    }
}
