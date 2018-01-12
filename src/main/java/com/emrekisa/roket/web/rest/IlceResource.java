package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.IlceService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.web.rest.util.PaginationUtil;
import com.emrekisa.roket.service.dto.IlceDTO;
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
 * REST controller for managing Ilce.
 */
@RestController
@RequestMapping("/api")
public class IlceResource {

    private final Logger log = LoggerFactory.getLogger(IlceResource.class);

    private static final String ENTITY_NAME = "ilce";

    private final IlceService ilceService;

    public IlceResource(IlceService ilceService) {
        this.ilceService = ilceService;
    }

    /**
     * POST  /ilces : Create a new ilce.
     *
     * @param ilceDTO the ilceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ilceDTO, or with status 400 (Bad Request) if the ilce has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ilces")
    @Timed
    public ResponseEntity<IlceDTO> createIlce(@Valid @RequestBody IlceDTO ilceDTO) throws URISyntaxException {
        log.debug("REST request to save Ilce : {}", ilceDTO);
        if (ilceDTO.getId() != null) {
            throw new BadRequestAlertException("A new ilce cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IlceDTO result = ilceService.save(ilceDTO);
        return ResponseEntity.created(new URI("/api/ilces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ilces : Updates an existing ilce.
     *
     * @param ilceDTO the ilceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ilceDTO,
     * or with status 400 (Bad Request) if the ilceDTO is not valid,
     * or with status 500 (Internal Server Error) if the ilceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ilces")
    @Timed
    public ResponseEntity<IlceDTO> updateIlce(@Valid @RequestBody IlceDTO ilceDTO) throws URISyntaxException {
        log.debug("REST request to update Ilce : {}", ilceDTO);
        if (ilceDTO.getId() == null) {
            return createIlce(ilceDTO);
        }
        IlceDTO result = ilceService.save(ilceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ilceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ilces : get all the ilces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ilces in body
     */
    @GetMapping("/ilces")
    @Timed
    public ResponseEntity<List<IlceDTO>> getAllIlces(Pageable pageable) {
        log.debug("REST request to get a page of Ilces");
        Page<IlceDTO> page = ilceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ilces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ilces/:id : get the "id" ilce.
     *
     * @param id the id of the ilceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ilceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ilces/{id}")
    @Timed
    public ResponseEntity<IlceDTO> getIlce(@PathVariable Long id) {
        log.debug("REST request to get Ilce : {}", id);
        IlceDTO ilceDTO = ilceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ilceDTO));
    }

    /**
     * DELETE  /ilces/:id : delete the "id" ilce.
     *
     * @param id the id of the ilceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ilces/{id}")
    @Timed
    public ResponseEntity<Void> deleteIlce(@PathVariable Long id) {
        log.debug("REST request to delete Ilce : {}", id);
        ilceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
