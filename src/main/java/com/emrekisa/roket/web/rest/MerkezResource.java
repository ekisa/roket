package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.MerkezService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.web.rest.util.PaginationUtil;
import com.emrekisa.roket.service.dto.MerkezDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Merkez.
 */
@RestController
@RequestMapping("/api")
public class MerkezResource {

    private final Logger log = LoggerFactory.getLogger(MerkezResource.class);

    private static final String ENTITY_NAME = "merkez";

    private final MerkezService merkezService;

    public MerkezResource(MerkezService merkezService) {
        this.merkezService = merkezService;
    }

    /**
     * POST  /merkezs : Create a new merkez.
     *
     * @param merkezDTO the merkezDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new merkezDTO, or with status 400 (Bad Request) if the merkez has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/merkezs")
    @Timed
    public ResponseEntity<MerkezDTO> createMerkez(@Valid @RequestBody MerkezDTO merkezDTO) throws URISyntaxException {
        log.debug("REST request to save Merkez : {}", merkezDTO);
        if (merkezDTO.getId() != null) {
            throw new BadRequestAlertException("A new merkez cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MerkezDTO result = merkezService.save(merkezDTO);
        return ResponseEntity.created(new URI("/api/merkezs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /merkezs : Updates an existing merkez.
     *
     * @param merkezDTO the merkezDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated merkezDTO,
     * or with status 400 (Bad Request) if the merkezDTO is not valid,
     * or with status 500 (Internal Server Error) if the merkezDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/merkezs")
    @Timed
    public ResponseEntity<MerkezDTO> updateMerkez(@Valid @RequestBody MerkezDTO merkezDTO) throws URISyntaxException {
        log.debug("REST request to update Merkez : {}", merkezDTO);
        if (merkezDTO.getId() == null) {
            return createMerkez(merkezDTO);
        }
        MerkezDTO result = merkezService.save(merkezDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, merkezDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /merkezs : get all the merkezs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of merkezs in body
     */
    @GetMapping("/merkezs")
    @Timed
    public ResponseEntity<List<MerkezDTO>> getAllMerkezs(Pageable pageable) {
        log.debug("REST request to get a page of Merkezs");
        Page<MerkezDTO> page = merkezService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/merkezs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /merkezs/:id : get the "id" merkez.
     *
     * @param id the id of the merkezDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the merkezDTO, or with status 404 (Not Found)
     */
    @GetMapping("/merkezs/{id}")
    @Timed
    public ResponseEntity<MerkezDTO> getMerkez(@PathVariable Long id) {
        log.debug("REST request to get Merkez : {}", id);
        MerkezDTO merkezDTO = merkezService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(merkezDTO));
    }

    /**
     * DELETE  /merkezs/:id : delete the "id" merkez.
     *
     * @param id the id of the merkezDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/merkezs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMerkez(@PathVariable Long id) {
        log.debug("REST request to delete Merkez : {}", id);
        merkezService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
