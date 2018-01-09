package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.MusteriService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.service.dto.MusteriDTO;
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
 * REST controller for managing Musteri.
 */
@RestController
@RequestMapping("/api")
public class MusteriResource {

    private final Logger log = LoggerFactory.getLogger(MusteriResource.class);

    private static final String ENTITY_NAME = "musteri";

    private final MusteriService musteriService;

    public MusteriResource(MusteriService musteriService) {
        this.musteriService = musteriService;
    }

    /**
     * POST  /musteris : Create a new musteri.
     *
     * @param musteriDTO the musteriDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new musteriDTO, or with status 400 (Bad Request) if the musteri has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/musteris")
    @Timed
    public ResponseEntity<MusteriDTO> createMusteri(@Valid @RequestBody MusteriDTO musteriDTO) throws URISyntaxException {
        log.debug("REST request to save Musteri : {}", musteriDTO);
        if (musteriDTO.getId() != null) {
            throw new BadRequestAlertException("A new musteri cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MusteriDTO result = musteriService.save(musteriDTO);
        return ResponseEntity.created(new URI("/api/musteris/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /musteris : Updates an existing musteri.
     *
     * @param musteriDTO the musteriDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated musteriDTO,
     * or with status 400 (Bad Request) if the musteriDTO is not valid,
     * or with status 500 (Internal Server Error) if the musteriDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/musteris")
    @Timed
    public ResponseEntity<MusteriDTO> updateMusteri(@Valid @RequestBody MusteriDTO musteriDTO) throws URISyntaxException {
        log.debug("REST request to update Musteri : {}", musteriDTO);
        if (musteriDTO.getId() == null) {
            return createMusteri(musteriDTO);
        }
        MusteriDTO result = musteriService.save(musteriDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, musteriDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /musteris : get all the musteris.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of musteris in body
     */
    @GetMapping("/musteris")
    @Timed
    public List<MusteriDTO> getAllMusteris() {
        log.debug("REST request to get all Musteris");
        return musteriService.findAll();
        }

    /**
     * GET  /musteris/:id : get the "id" musteri.
     *
     * @param id the id of the musteriDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the musteriDTO, or with status 404 (Not Found)
     */
    @GetMapping("/musteris/{id}")
    @Timed
    public ResponseEntity<MusteriDTO> getMusteri(@PathVariable Long id) {
        log.debug("REST request to get Musteri : {}", id);
        MusteriDTO musteriDTO = musteriService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(musteriDTO));
    }

    /**
     * DELETE  /musteris/:id : delete the "id" musteri.
     *
     * @param id the id of the musteriDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/musteris/{id}")
    @Timed
    public ResponseEntity<Void> deleteMusteri(@PathVariable Long id) {
        log.debug("REST request to delete Musteri : {}", id);
        musteriService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
