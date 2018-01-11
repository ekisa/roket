package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.MotorService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.web.rest.util.PaginationUtil;
import com.emrekisa.roket.service.dto.MotorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Motor.
 */
@RestController
@RequestMapping("/api")
public class MotorResource {

    private final Logger log = LoggerFactory.getLogger(MotorResource.class);

    private static final String ENTITY_NAME = "motor";

    private final MotorService motorService;

    public MotorResource(MotorService motorService) {
        this.motorService = motorService;
    }

    /**
     * POST  /motors : Create a new motor.
     *
     * @param motorDTO the motorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new motorDTO, or with status 400 (Bad Request) if the motor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/motors")
    @Timed
    public ResponseEntity<MotorDTO> createMotor(@RequestBody MotorDTO motorDTO) throws URISyntaxException {
        log.debug("REST request to save Motor : {}", motorDTO);
        if (motorDTO.getId() != null) {
            throw new BadRequestAlertException("A new motor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MotorDTO result = motorService.save(motorDTO);
        return ResponseEntity.created(new URI("/api/motors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /motors : Updates an existing motor.
     *
     * @param motorDTO the motorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated motorDTO,
     * or with status 400 (Bad Request) if the motorDTO is not valid,
     * or with status 500 (Internal Server Error) if the motorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/motors")
    @Timed
    public ResponseEntity<MotorDTO> updateMotor(@RequestBody MotorDTO motorDTO) throws URISyntaxException {
        log.debug("REST request to update Motor : {}", motorDTO);
        if (motorDTO.getId() == null) {
            return createMotor(motorDTO);
        }
        MotorDTO result = motorService.save(motorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, motorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /motors : get all the motors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of motors in body
     */
    @GetMapping("/motors")
    @Timed
    public ResponseEntity<List<MotorDTO>> getAllMotors(Pageable pageable) {
        log.debug("REST request to get a page of Motors");
        Page<MotorDTO> page = motorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/motors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /motors/:id : get the "id" motor.
     *
     * @param id the id of the motorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the motorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/motors/{id}")
    @Timed
    public ResponseEntity<MotorDTO> getMotor(@PathVariable Long id) {
        log.debug("REST request to get Motor : {}", id);
        MotorDTO motorDTO = motorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(motorDTO));
    }

    /**
     * DELETE  /motors/:id : delete the "id" motor.
     *
     * @param id the id of the motorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/motors/{id}")
    @Timed
    public ResponseEntity<Void> deleteMotor(@PathVariable Long id) {
        log.debug("REST request to delete Motor : {}", id);
        motorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
