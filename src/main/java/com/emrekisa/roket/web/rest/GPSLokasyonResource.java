package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.GPSLokasyonService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.service.dto.GPSLokasyonDTO;
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
 * REST controller for managing GPSLokasyon.
 */
@RestController
@RequestMapping("/api")
public class GPSLokasyonResource {

    private final Logger log = LoggerFactory.getLogger(GPSLokasyonResource.class);

    private static final String ENTITY_NAME = "gPSLokasyon";

    private final GPSLokasyonService gPSLokasyonService;

    public GPSLokasyonResource(GPSLokasyonService gPSLokasyonService) {
        this.gPSLokasyonService = gPSLokasyonService;
    }

    /**
     * POST  /gps-lokasyons : Create a new gPSLokasyon.
     *
     * @param gPSLokasyonDTO the gPSLokasyonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gPSLokasyonDTO, or with status 400 (Bad Request) if the gPSLokasyon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gps-lokasyons")
    @Timed
    public ResponseEntity<GPSLokasyonDTO> createGPSLokasyon(@Valid @RequestBody GPSLokasyonDTO gPSLokasyonDTO) throws URISyntaxException {
        log.debug("REST request to save GPSLokasyon : {}", gPSLokasyonDTO);
        if (gPSLokasyonDTO.getId() != null) {
            throw new BadRequestAlertException("A new gPSLokasyon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GPSLokasyonDTO result = gPSLokasyonService.save(gPSLokasyonDTO);
        return ResponseEntity.created(new URI("/api/gps-lokasyons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gps-lokasyons : Updates an existing gPSLokasyon.
     *
     * @param gPSLokasyonDTO the gPSLokasyonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gPSLokasyonDTO,
     * or with status 400 (Bad Request) if the gPSLokasyonDTO is not valid,
     * or with status 500 (Internal Server Error) if the gPSLokasyonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gps-lokasyons")
    @Timed
    public ResponseEntity<GPSLokasyonDTO> updateGPSLokasyon(@Valid @RequestBody GPSLokasyonDTO gPSLokasyonDTO) throws URISyntaxException {
        log.debug("REST request to update GPSLokasyon : {}", gPSLokasyonDTO);
        if (gPSLokasyonDTO.getId() == null) {
            return createGPSLokasyon(gPSLokasyonDTO);
        }
        GPSLokasyonDTO result = gPSLokasyonService.save(gPSLokasyonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gPSLokasyonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gps-lokasyons : get all the gPSLokasyons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gPSLokasyons in body
     */
    @GetMapping("/gps-lokasyons")
    @Timed
    public List<GPSLokasyonDTO> getAllGPSLokasyons() {
        log.debug("REST request to get all GPSLokasyons");
        return gPSLokasyonService.findAll();
        }

    /**
     * GET  /gps-lokasyons/:id : get the "id" gPSLokasyon.
     *
     * @param id the id of the gPSLokasyonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gPSLokasyonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/gps-lokasyons/{id}")
    @Timed
    public ResponseEntity<GPSLokasyonDTO> getGPSLokasyon(@PathVariable Long id) {
        log.debug("REST request to get GPSLokasyon : {}", id);
        GPSLokasyonDTO gPSLokasyonDTO = gPSLokasyonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gPSLokasyonDTO));
    }

    /**
     * DELETE  /gps-lokasyons/:id : delete the "id" gPSLokasyon.
     *
     * @param id the id of the gPSLokasyonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gps-lokasyons/{id}")
    @Timed
    public ResponseEntity<Void> deleteGPSLokasyon(@PathVariable Long id) {
        log.debug("REST request to delete GPSLokasyon : {}", id);
        gPSLokasyonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
