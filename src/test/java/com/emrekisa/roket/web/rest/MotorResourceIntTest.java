package com.emrekisa.roket.web.rest;

import com.emrekisa.roket.RoketApp;

import com.emrekisa.roket.domain.Motor;
import com.emrekisa.roket.repository.MotorRepository;
import com.emrekisa.roket.service.MotorService;
import com.emrekisa.roket.service.dto.MotorDTO;
import com.emrekisa.roket.service.mapper.MotorMapper;
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
 * Test class for the MotorResource REST controller.
 *
 * @see MotorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoketApp.class)
public class MotorResourceIntTest {

    private static final String DEFAULT_NUMARASI = "AAAAAAAAAA";
    private static final String UPDATED_NUMARASI = "BBBBBBBBBB";

    private static final String DEFAULT_MARKA = "AAAAAAAAAA";
    private static final String UPDATED_MARKA = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_YIL = "AAAAAAAAAA";
    private static final String UPDATED_YIL = "BBBBBBBBBB";

    @Autowired
    private MotorRepository motorRepository;

    @Autowired
    private MotorMapper motorMapper;

    @Autowired
    private MotorService motorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMotorMockMvc;

    private Motor motor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MotorResource motorResource = new MotorResource(motorService);
        this.restMotorMockMvc = MockMvcBuilders.standaloneSetup(motorResource)
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
    public static Motor createEntity(EntityManager em) {
        Motor motor = new Motor()
            .numarasi(DEFAULT_NUMARASI)
            .marka(DEFAULT_MARKA)
            .model(DEFAULT_MODEL)
            .yil(DEFAULT_YIL);
        return motor;
    }

    @Before
    public void initTest() {
        motor = createEntity(em);
    }

    @Test
    @Transactional
    public void createMotor() throws Exception {
        int databaseSizeBeforeCreate = motorRepository.findAll().size();

        // Create the Motor
        MotorDTO motorDTO = motorMapper.toDto(motor);
        restMotorMockMvc.perform(post("/api/motors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motorDTO)))
            .andExpect(status().isCreated());

        // Validate the Motor in the database
        List<Motor> motorList = motorRepository.findAll();
        assertThat(motorList).hasSize(databaseSizeBeforeCreate + 1);
        Motor testMotor = motorList.get(motorList.size() - 1);
        assertThat(testMotor.getNumarasi()).isEqualTo(DEFAULT_NUMARASI);
        assertThat(testMotor.getMarka()).isEqualTo(DEFAULT_MARKA);
        assertThat(testMotor.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testMotor.getYil()).isEqualTo(DEFAULT_YIL);
    }

    @Test
    @Transactional
    public void createMotorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motorRepository.findAll().size();

        // Create the Motor with an existing ID
        motor.setId(1L);
        MotorDTO motorDTO = motorMapper.toDto(motor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotorMockMvc.perform(post("/api/motors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Motor in the database
        List<Motor> motorList = motorRepository.findAll();
        assertThat(motorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMotors() throws Exception {
        // Initialize the database
        motorRepository.saveAndFlush(motor);

        // Get all the motorList
        restMotorMockMvc.perform(get("/api/motors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motor.getId().intValue())))
            .andExpect(jsonPath("$.[*].numarasi").value(hasItem(DEFAULT_NUMARASI.toString())))
            .andExpect(jsonPath("$.[*].marka").value(hasItem(DEFAULT_MARKA.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].yil").value(hasItem(DEFAULT_YIL.toString())));
    }

    @Test
    @Transactional
    public void getMotor() throws Exception {
        // Initialize the database
        motorRepository.saveAndFlush(motor);

        // Get the motor
        restMotorMockMvc.perform(get("/api/motors/{id}", motor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(motor.getId().intValue()))
            .andExpect(jsonPath("$.numarasi").value(DEFAULT_NUMARASI.toString()))
            .andExpect(jsonPath("$.marka").value(DEFAULT_MARKA.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.yil").value(DEFAULT_YIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMotor() throws Exception {
        // Get the motor
        restMotorMockMvc.perform(get("/api/motors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotor() throws Exception {
        // Initialize the database
        motorRepository.saveAndFlush(motor);
        int databaseSizeBeforeUpdate = motorRepository.findAll().size();

        // Update the motor
        Motor updatedMotor = motorRepository.findOne(motor.getId());
        // Disconnect from session so that the updates on updatedMotor are not directly saved in db
        em.detach(updatedMotor);
        updatedMotor
            .numarasi(UPDATED_NUMARASI)
            .marka(UPDATED_MARKA)
            .model(UPDATED_MODEL)
            .yil(UPDATED_YIL);
        MotorDTO motorDTO = motorMapper.toDto(updatedMotor);

        restMotorMockMvc.perform(put("/api/motors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motorDTO)))
            .andExpect(status().isOk());

        // Validate the Motor in the database
        List<Motor> motorList = motorRepository.findAll();
        assertThat(motorList).hasSize(databaseSizeBeforeUpdate);
        Motor testMotor = motorList.get(motorList.size() - 1);
        assertThat(testMotor.getNumarasi()).isEqualTo(UPDATED_NUMARASI);
        assertThat(testMotor.getMarka()).isEqualTo(UPDATED_MARKA);
        assertThat(testMotor.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testMotor.getYil()).isEqualTo(UPDATED_YIL);
    }

    @Test
    @Transactional
    public void updateNonExistingMotor() throws Exception {
        int databaseSizeBeforeUpdate = motorRepository.findAll().size();

        // Create the Motor
        MotorDTO motorDTO = motorMapper.toDto(motor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMotorMockMvc.perform(put("/api/motors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motorDTO)))
            .andExpect(status().isCreated());

        // Validate the Motor in the database
        List<Motor> motorList = motorRepository.findAll();
        assertThat(motorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMotor() throws Exception {
        // Initialize the database
        motorRepository.saveAndFlush(motor);
        int databaseSizeBeforeDelete = motorRepository.findAll().size();

        // Get the motor
        restMotorMockMvc.perform(delete("/api/motors/{id}", motor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Motor> motorList = motorRepository.findAll();
        assertThat(motorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Motor.class);
        Motor motor1 = new Motor();
        motor1.setId(1L);
        Motor motor2 = new Motor();
        motor2.setId(motor1.getId());
        assertThat(motor1).isEqualTo(motor2);
        motor2.setId(2L);
        assertThat(motor1).isNotEqualTo(motor2);
        motor1.setId(null);
        assertThat(motor1).isNotEqualTo(motor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotorDTO.class);
        MotorDTO motorDTO1 = new MotorDTO();
        motorDTO1.setId(1L);
        MotorDTO motorDTO2 = new MotorDTO();
        assertThat(motorDTO1).isNotEqualTo(motorDTO2);
        motorDTO2.setId(motorDTO1.getId());
        assertThat(motorDTO1).isEqualTo(motorDTO2);
        motorDTO2.setId(2L);
        assertThat(motorDTO1).isNotEqualTo(motorDTO2);
        motorDTO1.setId(null);
        assertThat(motorDTO1).isNotEqualTo(motorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(motorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(motorMapper.fromId(null)).isNull();
    }
}
