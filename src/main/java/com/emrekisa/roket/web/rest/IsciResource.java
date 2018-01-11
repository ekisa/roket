package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.IsciService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.web.rest.util.PaginationUtil;
import com.emrekisa.roket.service.dto.IsciDTO;
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
 * REST controller for managing Isci.
 */
@RestController
@RequestMapping("/api")
public class IsciResource {

    private final Logger log = LoggerFactory.getLogger(IsciResource.class);

    private static final String ENTITY_NAME = "isci";

    private final IsciService isciService;

    public IsciResource(IsciService isciService) {
        this.isciService = isciService;
    }

    /**
     * POST  /iscis : Create a new isci.
     *
     * @param isciDTO the isciDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new isciDTO, or with status 400 (Bad Request) if the isci has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/iscis")
    @Timed
    public ResponseEntity<IsciDTO> createIsci(@RequestBody IsciDTO isciDTO) throws URISyntaxException {
        log.debug("REST request to save Isci : {}", isciDTO);
        if (isciDTO.getId() != null) {
            throw new BadRequestAlertException("A new isci cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IsciDTO result = isciService.save(isciDTO);
        return ResponseEntity.created(new URI("/api/iscis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /iscis : Updates an existing isci.
     *
     * @param isciDTO the isciDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated isciDTO,
     * or with status 400 (Bad Request) if the isciDTO is not valid,
     * or with status 500 (Internal Server Error) if the isciDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/iscis")
    @Timed
    public ResponseEntity<IsciDTO> updateIsci(@RequestBody IsciDTO isciDTO) throws URISyntaxException {
        log.debug("REST request to update Isci : {}", isciDTO);
        if (isciDTO.getId() == null) {
            return createIsci(isciDTO);
        }
        IsciDTO result = isciService.save(isciDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, isciDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /iscis : get all the iscis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of iscis in body
     */
    @GetMapping("/iscis")
    @Timed
    public ResponseEntity<List<IsciDTO>> getAllIscis(Pageable pageable) {
        log.debug("REST request to get a page of Iscis");
        Page<IsciDTO> page = isciService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iscis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iscis/:id : get the "id" isci.
     *
     * @param id the id of the isciDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the isciDTO, or with status 404 (Not Found)
     */
    @GetMapping("/iscis/{id}")
    @Timed
    public ResponseEntity<IsciDTO> getIsci(@PathVariable Long id) {
        log.debug("REST request to get Isci : {}", id);
        IsciDTO isciDTO = isciService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(isciDTO));
    }

    /**
     * DELETE  /iscis/:id : delete the "id" isci.
     *
     * @param id the id of the isciDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/iscis/{id}")
    @Timed
    public ResponseEntity<Void> deleteIsci(@PathVariable Long id) {
        log.debug("REST request to delete Isci : {}", id);
        isciService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
