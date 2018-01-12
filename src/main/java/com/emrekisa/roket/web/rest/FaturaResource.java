package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.FaturaService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.web.rest.util.PaginationUtil;
import com.emrekisa.roket.service.dto.FaturaDTO;
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
 * REST controller for managing Fatura.
 */
@RestController
@RequestMapping("/api")
public class FaturaResource {

    private final Logger log = LoggerFactory.getLogger(FaturaResource.class);

    private static final String ENTITY_NAME = "fatura";

    private final FaturaService faturaService;

    public FaturaResource(FaturaService faturaService) {
        this.faturaService = faturaService;
    }

    /**
     * POST  /faturas : Create a new fatura.
     *
     * @param faturaDTO the faturaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new faturaDTO, or with status 400 (Bad Request) if the fatura has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/faturas")
    @Timed
    public ResponseEntity<FaturaDTO> createFatura(@Valid @RequestBody FaturaDTO faturaDTO) throws URISyntaxException {
        log.debug("REST request to save Fatura : {}", faturaDTO);
        if (faturaDTO.getId() != null) {
            throw new BadRequestAlertException("A new fatura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FaturaDTO result = faturaService.save(faturaDTO);
        return ResponseEntity.created(new URI("/api/faturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /faturas : Updates an existing fatura.
     *
     * @param faturaDTO the faturaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faturaDTO,
     * or with status 400 (Bad Request) if the faturaDTO is not valid,
     * or with status 500 (Internal Server Error) if the faturaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/faturas")
    @Timed
    public ResponseEntity<FaturaDTO> updateFatura(@Valid @RequestBody FaturaDTO faturaDTO) throws URISyntaxException {
        log.debug("REST request to update Fatura : {}", faturaDTO);
        if (faturaDTO.getId() == null) {
            return createFatura(faturaDTO);
        }
        FaturaDTO result = faturaService.save(faturaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, faturaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /faturas : get all the faturas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of faturas in body
     */
    @GetMapping("/faturas")
    @Timed
    public ResponseEntity<List<FaturaDTO>> getAllFaturas(Pageable pageable) {
        log.debug("REST request to get a page of Faturas");
        Page<FaturaDTO> page = faturaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/faturas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /faturas/:id : get the "id" fatura.
     *
     * @param id the id of the faturaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the faturaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/faturas/{id}")
    @Timed
    public ResponseEntity<FaturaDTO> getFatura(@PathVariable Long id) {
        log.debug("REST request to get Fatura : {}", id);
        FaturaDTO faturaDTO = faturaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(faturaDTO));
    }

    /**
     * DELETE  /faturas/:id : delete the "id" fatura.
     *
     * @param id the id of the faturaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/faturas/{id}")
    @Timed
    public ResponseEntity<Void> deleteFatura(@PathVariable Long id) {
        log.debug("REST request to delete Fatura : {}", id);
        faturaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
