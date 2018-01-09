package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.KuryeService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.web.rest.util.PaginationUtil;
import com.emrekisa.roket.service.dto.KuryeDTO;
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
 * REST controller for managing Kurye.
 */
@RestController
@RequestMapping("/api")
public class KuryeResource {

    private final Logger log = LoggerFactory.getLogger(KuryeResource.class);

    private static final String ENTITY_NAME = "kurye";

    private final KuryeService kuryeService;

    public KuryeResource(KuryeService kuryeService) {
        this.kuryeService = kuryeService;
    }

    /**
     * POST  /kuryes : Create a new kurye.
     *
     * @param kuryeDTO the kuryeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kuryeDTO, or with status 400 (Bad Request) if the kurye has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kuryes")
    @Timed
    public ResponseEntity<KuryeDTO> createKurye(@Valid @RequestBody KuryeDTO kuryeDTO) throws URISyntaxException {
        log.debug("REST request to save Kurye : {}", kuryeDTO);
        if (kuryeDTO.getId() != null) {
            throw new BadRequestAlertException("A new kurye cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KuryeDTO result = kuryeService.save(kuryeDTO);
        return ResponseEntity.created(new URI("/api/kuryes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kuryes : Updates an existing kurye.
     *
     * @param kuryeDTO the kuryeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kuryeDTO,
     * or with status 400 (Bad Request) if the kuryeDTO is not valid,
     * or with status 500 (Internal Server Error) if the kuryeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kuryes")
    @Timed
    public ResponseEntity<KuryeDTO> updateKurye(@Valid @RequestBody KuryeDTO kuryeDTO) throws URISyntaxException {
        log.debug("REST request to update Kurye : {}", kuryeDTO);
        if (kuryeDTO.getId() == null) {
            return createKurye(kuryeDTO);
        }
        KuryeDTO result = kuryeService.save(kuryeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kuryeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kuryes : get all the kuryes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of kuryes in body
     */
    @GetMapping("/kuryes")
    @Timed
    public ResponseEntity<List<KuryeDTO>> getAllKuryes(Pageable pageable) {
        log.debug("REST request to get a page of Kuryes");
        Page<KuryeDTO> page = kuryeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/kuryes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /kuryes/:id : get the "id" kurye.
     *
     * @param id the id of the kuryeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kuryeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/kuryes/{id}")
    @Timed
    public ResponseEntity<KuryeDTO> getKurye(@PathVariable Long id) {
        log.debug("REST request to get Kurye : {}", id);
        KuryeDTO kuryeDTO = kuryeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kuryeDTO));
    }

    /**
     * DELETE  /kuryes/:id : delete the "id" kurye.
     *
     * @param id the id of the kuryeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kuryes/{id}")
    @Timed
    public ResponseEntity<Void> deleteKurye(@PathVariable Long id) {
        log.debug("REST request to delete Kurye : {}", id);
        kuryeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
