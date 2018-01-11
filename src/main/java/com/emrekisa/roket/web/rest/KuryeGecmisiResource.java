package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.service.KuryeGecmisiService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.web.rest.util.PaginationUtil;
import com.emrekisa.roket.service.dto.KuryeGecmisiDTO;
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
 * REST controller for managing KuryeGecmisi.
 */
@RestController
@RequestMapping("/api")
public class KuryeGecmisiResource {

    private final Logger log = LoggerFactory.getLogger(KuryeGecmisiResource.class);

    private static final String ENTITY_NAME = "kuryeGecmisi";

    private final KuryeGecmisiService kuryeGecmisiService;

    public KuryeGecmisiResource(KuryeGecmisiService kuryeGecmisiService) {
        this.kuryeGecmisiService = kuryeGecmisiService;
    }

    /**
     * POST  /kurye-gecmisis : Create a new kuryeGecmisi.
     *
     * @param kuryeGecmisiDTO the kuryeGecmisiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kuryeGecmisiDTO, or with status 400 (Bad Request) if the kuryeGecmisi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kurye-gecmisis")
    @Timed
    public ResponseEntity<KuryeGecmisiDTO> createKuryeGecmisi(@Valid @RequestBody KuryeGecmisiDTO kuryeGecmisiDTO) throws URISyntaxException {
        log.debug("REST request to save KuryeGecmisi : {}", kuryeGecmisiDTO);
        if (kuryeGecmisiDTO.getId() != null) {
            throw new BadRequestAlertException("A new kuryeGecmisi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KuryeGecmisiDTO result = kuryeGecmisiService.save(kuryeGecmisiDTO);
        return ResponseEntity.created(new URI("/api/kurye-gecmisis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kurye-gecmisis : Updates an existing kuryeGecmisi.
     *
     * @param kuryeGecmisiDTO the kuryeGecmisiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kuryeGecmisiDTO,
     * or with status 400 (Bad Request) if the kuryeGecmisiDTO is not valid,
     * or with status 500 (Internal Server Error) if the kuryeGecmisiDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kurye-gecmisis")
    @Timed
    public ResponseEntity<KuryeGecmisiDTO> updateKuryeGecmisi(@Valid @RequestBody KuryeGecmisiDTO kuryeGecmisiDTO) throws URISyntaxException {
        log.debug("REST request to update KuryeGecmisi : {}", kuryeGecmisiDTO);
        if (kuryeGecmisiDTO.getId() == null) {
            return createKuryeGecmisi(kuryeGecmisiDTO);
        }
        KuryeGecmisiDTO result = kuryeGecmisiService.save(kuryeGecmisiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kuryeGecmisiDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kurye-gecmisis : get all the kuryeGecmisis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of kuryeGecmisis in body
     */
    @GetMapping("/kurye-gecmisis")
    @Timed
    public ResponseEntity<List<KuryeGecmisiDTO>> getAllKuryeGecmisis(Pageable pageable) {
        log.debug("REST request to get a page of KuryeGecmisis");
        Page<KuryeGecmisiDTO> page = kuryeGecmisiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/kurye-gecmisis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /kurye-gecmisis/:id : get the "id" kuryeGecmisi.
     *
     * @param id the id of the kuryeGecmisiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kuryeGecmisiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/kurye-gecmisis/{id}")
    @Timed
    public ResponseEntity<KuryeGecmisiDTO> getKuryeGecmisi(@PathVariable Long id) {
        log.debug("REST request to get KuryeGecmisi : {}", id);
        KuryeGecmisiDTO kuryeGecmisiDTO = kuryeGecmisiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kuryeGecmisiDTO));
    }

    /**
     * DELETE  /kurye-gecmisis/:id : delete the "id" kuryeGecmisi.
     *
     * @param id the id of the kuryeGecmisiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kurye-gecmisis/{id}")
    @Timed
    public ResponseEntity<Void> deleteKuryeGecmisi(@PathVariable Long id) {
        log.debug("REST request to delete KuryeGecmisi : {}", id);
        kuryeGecmisiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
