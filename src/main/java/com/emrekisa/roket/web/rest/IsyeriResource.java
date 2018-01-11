package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.IsyeriService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.web.rest.util.PaginationUtil;
import com.emrekisa.roket.service.dto.IsyeriDTO;
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
 * REST controller for managing Isyeri.
 */
@RestController
@RequestMapping("/api")
public class IsyeriResource {

    private final Logger log = LoggerFactory.getLogger(IsyeriResource.class);

    private static final String ENTITY_NAME = "isyeri";

    private final IsyeriService isyeriService;

    public IsyeriResource(IsyeriService isyeriService) {
        this.isyeriService = isyeriService;
    }

    /**
     * POST  /isyeris : Create a new isyeri.
     *
     * @param isyeriDTO the isyeriDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new isyeriDTO, or with status 400 (Bad Request) if the isyeri has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/isyeris")
    @Timed
    public ResponseEntity<IsyeriDTO> createIsyeri(@Valid @RequestBody IsyeriDTO isyeriDTO) throws URISyntaxException {
        log.debug("REST request to save Isyeri : {}", isyeriDTO);
        if (isyeriDTO.getId() != null) {
            throw new BadRequestAlertException("A new isyeri cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IsyeriDTO result = isyeriService.save(isyeriDTO);
        return ResponseEntity.created(new URI("/api/isyeris/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /isyeris : Updates an existing isyeri.
     *
     * @param isyeriDTO the isyeriDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated isyeriDTO,
     * or with status 400 (Bad Request) if the isyeriDTO is not valid,
     * or with status 500 (Internal Server Error) if the isyeriDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/isyeris")
    @Timed
    public ResponseEntity<IsyeriDTO> updateIsyeri(@Valid @RequestBody IsyeriDTO isyeriDTO) throws URISyntaxException {
        log.debug("REST request to update Isyeri : {}", isyeriDTO);
        if (isyeriDTO.getId() == null) {
            return createIsyeri(isyeriDTO);
        }
        IsyeriDTO result = isyeriService.save(isyeriDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, isyeriDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /isyeris : get all the isyeris.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of isyeris in body
     */
    @GetMapping("/isyeris")
    @Timed
    public ResponseEntity<List<IsyeriDTO>> getAllIsyeris(Pageable pageable) {
        log.debug("REST request to get a page of Isyeris");
        Page<IsyeriDTO> page = isyeriService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/isyeris");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /isyeris/:id : get the "id" isyeri.
     *
     * @param id the id of the isyeriDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the isyeriDTO, or with status 404 (Not Found)
     */
    @GetMapping("/isyeris/{id}")
    @Timed
    public ResponseEntity<IsyeriDTO> getIsyeri(@PathVariable Long id) {
        log.debug("REST request to get Isyeri : {}", id);
        IsyeriDTO isyeriDTO = isyeriService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(isyeriDTO));
    }

    /**
     * DELETE  /isyeris/:id : delete the "id" isyeri.
     *
     * @param id the id of the isyeriDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/isyeris/{id}")
    @Timed
    public ResponseEntity<Void> deleteIsyeri(@PathVariable Long id) {
        log.debug("REST request to delete Isyeri : {}", id);
        isyeriService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
