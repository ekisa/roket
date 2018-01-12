package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.IlService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.web.rest.util.PaginationUtil;
import com.emrekisa.roket.service.dto.IlDTO;
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
 * REST controller for managing Il.
 */
@RestController
@RequestMapping("/api")
public class IlResource {

    private final Logger log = LoggerFactory.getLogger(IlResource.class);

    private static final String ENTITY_NAME = "il";

    private final IlService ilService;

    public IlResource(IlService ilService) {
        this.ilService = ilService;
    }

    /**
     * POST  /ils : Create a new il.
     *
     * @param ilDTO the ilDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ilDTO, or with status 400 (Bad Request) if the il has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ils")
    @Timed
    public ResponseEntity<IlDTO> createIl(@Valid @RequestBody IlDTO ilDTO) throws URISyntaxException {
        log.debug("REST request to save Il : {}", ilDTO);
        if (ilDTO.getId() != null) {
            throw new BadRequestAlertException("A new il cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IlDTO result = ilService.save(ilDTO);
        return ResponseEntity.created(new URI("/api/ils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ils : Updates an existing il.
     *
     * @param ilDTO the ilDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ilDTO,
     * or with status 400 (Bad Request) if the ilDTO is not valid,
     * or with status 500 (Internal Server Error) if the ilDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ils")
    @Timed
    public ResponseEntity<IlDTO> updateIl(@Valid @RequestBody IlDTO ilDTO) throws URISyntaxException {
        log.debug("REST request to update Il : {}", ilDTO);
        if (ilDTO.getId() == null) {
            return createIl(ilDTO);
        }
        IlDTO result = ilService.save(ilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ilDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ils : get all the ils.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ils in body
     */
    @GetMapping("/ils")
    @Timed
    public ResponseEntity<List<IlDTO>> getAllIls(Pageable pageable) {
        log.debug("REST request to get a page of Ils");
        Page<IlDTO> page = ilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ils");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ils/:id : get the "id" il.
     *
     * @param id the id of the ilDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ilDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ils/{id}")
    @Timed
    public ResponseEntity<IlDTO> getIl(@PathVariable Long id) {
        log.debug("REST request to get Il : {}", id);
        IlDTO ilDTO = ilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ilDTO));
    }

    /**
     * DELETE  /ils/:id : delete the "id" il.
     *
     * @param id the id of the ilDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ils/{id}")
    @Timed
    public ResponseEntity<Void> deleteIl(@PathVariable Long id) {
        log.debug("REST request to delete Il : {}", id);
        ilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
