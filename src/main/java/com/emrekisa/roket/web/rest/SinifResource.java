package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.SinifService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.service.dto.SinifDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Sinif.
 */
@RestController
@RequestMapping("/api")
public class SinifResource {

    private final Logger log = LoggerFactory.getLogger(SinifResource.class);

    private static final String ENTITY_NAME = "sinif";

    private final SinifService sinifService;

    public SinifResource(SinifService sinifService) {
        this.sinifService = sinifService;
    }

    /**
     * POST  /sinifs : Create a new sinif.
     *
     * @param sinifDTO the sinifDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sinifDTO, or with status 400 (Bad Request) if the sinif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sinifs")
    @Timed
    public ResponseEntity<SinifDTO> createSinif(@Valid @RequestBody SinifDTO sinifDTO) throws URISyntaxException {
        log.debug("REST request to save Sinif : {}", sinifDTO);
        if (sinifDTO.getId() != null) {
            throw new BadRequestAlertException("A new sinif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SinifDTO result = sinifService.save(sinifDTO);
        return ResponseEntity.created(new URI("/api/sinifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sinifs : Updates an existing sinif.
     *
     * @param sinifDTO the sinifDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sinifDTO,
     * or with status 400 (Bad Request) if the sinifDTO is not valid,
     * or with status 500 (Internal Server Error) if the sinifDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sinifs")
    @Timed
    public ResponseEntity<SinifDTO> updateSinif(@Valid @RequestBody SinifDTO sinifDTO) throws URISyntaxException {
        log.debug("REST request to update Sinif : {}", sinifDTO);
        if (sinifDTO.getId() == null) {
            return createSinif(sinifDTO);
        }
        SinifDTO result = sinifService.save(sinifDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sinifDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sinifs : get all the sinifs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sinifs in body
     */
    @GetMapping("/sinifs")
    @Timed
    public List<SinifDTO> getAllSinifs() {
        log.debug("REST request to get all Sinifs");
        return sinifService.findAll();
        }

    /**
     * GET  /sinifs/:id : get the "id" sinif.
     *
     * @param id the id of the sinifDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sinifDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sinifs/{id}")
    @Timed
    public ResponseEntity<SinifDTO> getSinif(@PathVariable Long id) {
        log.debug("REST request to get Sinif : {}", id);
        SinifDTO sinifDTO = sinifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinifDTO));
    }

    /**
     * DELETE  /sinifs/:id : delete the "id" sinif.
     *
     * @param id the id of the sinifDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sinifs/{id}")
    @Timed
    public ResponseEntity<Void> deleteSinif(@PathVariable Long id) {
        log.debug("REST request to delete Sinif : {}", id);
        sinifService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
