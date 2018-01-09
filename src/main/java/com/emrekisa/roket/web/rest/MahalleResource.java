package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.MahalleService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.service.dto.MahalleDTO;
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
 * REST controller for managing Mahalle.
 */
@RestController
@RequestMapping("/api")
public class MahalleResource {

    private final Logger log = LoggerFactory.getLogger(MahalleResource.class);

    private static final String ENTITY_NAME = "mahalle";

    private final MahalleService mahalleService;

    public MahalleResource(MahalleService mahalleService) {
        this.mahalleService = mahalleService;
    }

    /**
     * POST  /mahalles : Create a new mahalle.
     *
     * @param mahalleDTO the mahalleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mahalleDTO, or with status 400 (Bad Request) if the mahalle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mahalles")
    @Timed
    public ResponseEntity<MahalleDTO> createMahalle(@Valid @RequestBody MahalleDTO mahalleDTO) throws URISyntaxException {
        log.debug("REST request to save Mahalle : {}", mahalleDTO);
        if (mahalleDTO.getId() != null) {
            throw new BadRequestAlertException("A new mahalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MahalleDTO result = mahalleService.save(mahalleDTO);
        return ResponseEntity.created(new URI("/api/mahalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mahalles : Updates an existing mahalle.
     *
     * @param mahalleDTO the mahalleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mahalleDTO,
     * or with status 400 (Bad Request) if the mahalleDTO is not valid,
     * or with status 500 (Internal Server Error) if the mahalleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mahalles")
    @Timed
    public ResponseEntity<MahalleDTO> updateMahalle(@Valid @RequestBody MahalleDTO mahalleDTO) throws URISyntaxException {
        log.debug("REST request to update Mahalle : {}", mahalleDTO);
        if (mahalleDTO.getId() == null) {
            return createMahalle(mahalleDTO);
        }
        MahalleDTO result = mahalleService.save(mahalleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mahalleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mahalles : get all the mahalles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mahalles in body
     */
    @GetMapping("/mahalles")
    @Timed
    public List<MahalleDTO> getAllMahalles() {
        log.debug("REST request to get all Mahalles");
        return mahalleService.findAll();
        }

    /**
     * GET  /mahalles/:id : get the "id" mahalle.
     *
     * @param id the id of the mahalleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mahalleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mahalles/{id}")
    @Timed
    public ResponseEntity<MahalleDTO> getMahalle(@PathVariable Long id) {
        log.debug("REST request to get Mahalle : {}", id);
        MahalleDTO mahalleDTO = mahalleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mahalleDTO));
    }

    /**
     * DELETE  /mahalles/:id : delete the "id" mahalle.
     *
     * @param id the id of the mahalleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mahalles/{id}")
    @Timed
    public ResponseEntity<Void> deleteMahalle(@PathVariable Long id) {
        log.debug("REST request to delete Mahalle : {}", id);
        mahalleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
