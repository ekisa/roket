package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.OgrenciService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.service.dto.OgrenciDTO;
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
 * REST controller for managing Ogrenci.
 */
@RestController
@RequestMapping("/api")
public class OgrenciResource {

    private final Logger log = LoggerFactory.getLogger(OgrenciResource.class);

    private static final String ENTITY_NAME = "ogrenci";

    private final OgrenciService ogrenciService;

    public OgrenciResource(OgrenciService ogrenciService) {
        this.ogrenciService = ogrenciService;
    }

    /**
     * POST  /ogrencis : Create a new ogrenci.
     *
     * @param ogrenciDTO the ogrenciDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ogrenciDTO, or with status 400 (Bad Request) if the ogrenci has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ogrencis")
    @Timed
    public ResponseEntity<OgrenciDTO> createOgrenci(@Valid @RequestBody OgrenciDTO ogrenciDTO) throws URISyntaxException {
        log.debug("REST request to save Ogrenci : {}", ogrenciDTO);
        if (ogrenciDTO.getId() != null) {
            throw new BadRequestAlertException("A new ogrenci cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OgrenciDTO result = ogrenciService.save(ogrenciDTO);
        return ResponseEntity.created(new URI("/api/ogrencis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ogrencis : Updates an existing ogrenci.
     *
     * @param ogrenciDTO the ogrenciDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ogrenciDTO,
     * or with status 400 (Bad Request) if the ogrenciDTO is not valid,
     * or with status 500 (Internal Server Error) if the ogrenciDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ogrencis")
    @Timed
    public ResponseEntity<OgrenciDTO> updateOgrenci(@Valid @RequestBody OgrenciDTO ogrenciDTO) throws URISyntaxException {
        log.debug("REST request to update Ogrenci : {}", ogrenciDTO);
        if (ogrenciDTO.getId() == null) {
            return createOgrenci(ogrenciDTO);
        }
        OgrenciDTO result = ogrenciService.save(ogrenciDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ogrenciDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ogrencis : get all the ogrencis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ogrencis in body
     */
    @GetMapping("/ogrencis")
    @Timed
    public List<OgrenciDTO> getAllOgrencis() {
        log.debug("REST request to get all Ogrencis");
        return ogrenciService.findAll();
        }

    /**
     * GET  /ogrencis/:id : get the "id" ogrenci.
     *
     * @param id the id of the ogrenciDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ogrenciDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ogrencis/{id}")
    @Timed
    public ResponseEntity<OgrenciDTO> getOgrenci(@PathVariable Long id) {
        log.debug("REST request to get Ogrenci : {}", id);
        OgrenciDTO ogrenciDTO = ogrenciService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ogrenciDTO));
    }

    /**
     * DELETE  /ogrencis/:id : delete the "id" ogrenci.
     *
     * @param id the id of the ogrenciDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ogrencis/{id}")
    @Timed
    public ResponseEntity<Void> deleteOgrenci(@PathVariable Long id) {
        log.debug("REST request to delete Ogrenci : {}", id);
        ogrenciService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
