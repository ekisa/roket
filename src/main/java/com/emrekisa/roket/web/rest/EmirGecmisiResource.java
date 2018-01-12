package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.EmirGecmisiService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.web.rest.util.PaginationUtil;
import com.emrekisa.roket.service.dto.EmirGecmisiDTO;
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
 * REST controller for managing EmirGecmisi.
 */
@RestController
@RequestMapping("/api")
public class EmirGecmisiResource {

    private final Logger log = LoggerFactory.getLogger(EmirGecmisiResource.class);

    private static final String ENTITY_NAME = "emirGecmisi";

    private final EmirGecmisiService emirGecmisiService;

    public EmirGecmisiResource(EmirGecmisiService emirGecmisiService) {
        this.emirGecmisiService = emirGecmisiService;
    }

    /**
     * POST  /emir-gecmisis : Create a new emirGecmisi.
     *
     * @param emirGecmisiDTO the emirGecmisiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emirGecmisiDTO, or with status 400 (Bad Request) if the emirGecmisi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/emir-gecmisis")
    @Timed
    public ResponseEntity<EmirGecmisiDTO> createEmirGecmisi(@Valid @RequestBody EmirGecmisiDTO emirGecmisiDTO) throws URISyntaxException {
        log.debug("REST request to save EmirGecmisi : {}", emirGecmisiDTO);
        if (emirGecmisiDTO.getId() != null) {
            throw new BadRequestAlertException("A new emirGecmisi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmirGecmisiDTO result = emirGecmisiService.save(emirGecmisiDTO);
        return ResponseEntity.created(new URI("/api/emir-gecmisis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emir-gecmisis : Updates an existing emirGecmisi.
     *
     * @param emirGecmisiDTO the emirGecmisiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emirGecmisiDTO,
     * or with status 400 (Bad Request) if the emirGecmisiDTO is not valid,
     * or with status 500 (Internal Server Error) if the emirGecmisiDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/emir-gecmisis")
    @Timed
    public ResponseEntity<EmirGecmisiDTO> updateEmirGecmisi(@Valid @RequestBody EmirGecmisiDTO emirGecmisiDTO) throws URISyntaxException {
        log.debug("REST request to update EmirGecmisi : {}", emirGecmisiDTO);
        if (emirGecmisiDTO.getId() == null) {
            return createEmirGecmisi(emirGecmisiDTO);
        }
        EmirGecmisiDTO result = emirGecmisiService.save(emirGecmisiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emirGecmisiDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emir-gecmisis : get all the emirGecmisis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of emirGecmisis in body
     */
    @GetMapping("/emir-gecmisis")
    @Timed
    public ResponseEntity<List<EmirGecmisiDTO>> getAllEmirGecmisis(Pageable pageable) {
        log.debug("REST request to get a page of EmirGecmisis");
        Page<EmirGecmisiDTO> page = emirGecmisiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/emir-gecmisis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /emir-gecmisis/:id : get the "id" emirGecmisi.
     *
     * @param id the id of the emirGecmisiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emirGecmisiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/emir-gecmisis/{id}")
    @Timed
    public ResponseEntity<EmirGecmisiDTO> getEmirGecmisi(@PathVariable Long id) {
        log.debug("REST request to get EmirGecmisi : {}", id);
        EmirGecmisiDTO emirGecmisiDTO = emirGecmisiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(emirGecmisiDTO));
    }

    /**
     * DELETE  /emir-gecmisis/:id : delete the "id" emirGecmisi.
     *
     * @param id the id of the emirGecmisiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emir-gecmisis/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmirGecmisi(@PathVariable Long id) {
        log.debug("REST request to delete EmirGecmisi : {}", id);
        emirGecmisiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
