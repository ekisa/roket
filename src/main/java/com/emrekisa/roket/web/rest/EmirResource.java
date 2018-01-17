package com.emrekisa.roket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrekisa.roket.domain.Authority;
import com.emrekisa.roket.domain.Isyeri;
import com.emrekisa.roket.repository.IsyeriRepository;
import com.emrekisa.roket.repository.UserRepository;
import com.emrekisa.roket.security.AuthoritiesConstants;
import com.emrekisa.roket.security.SecurityUtils;
import com.emrekisa.roket.service.EmirService;
import com.emrekisa.roket.service.UserService;
import com.emrekisa.roket.web.rest.errors.BadRequestAlertException;
import com.emrekisa.roket.web.rest.util.HeaderUtil;
import com.emrekisa.roket.web.rest.util.PaginationUtil;
import com.emrekisa.roket.service.dto.EmirDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * REST controller for managing Emir.
 */
@RestController
@RequestMapping("/api")
public class EmirResource {

    private final Logger log = LoggerFactory.getLogger(EmirResource.class);

    private static final String ENTITY_NAME = "emir";

    private final EmirService emirService;

    private final UserRepository userRepository;
    private final IsyeriRepository isyeriRepository;

    public EmirResource(EmirService emirService, UserRepository userRepository,IsyeriRepository isyeriRepository) {
        this.emirService = emirService;
        this.userRepository= userRepository;
        this.isyeriRepository = isyeriRepository;
    }

    /**
     * POST  /emirs : Create a new emir.
     *
     * @param emirDTO the emirDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emirDTO, or with status 400 (Bad Request) if the emir has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/emirs")
    @Timed
    public ResponseEntity<EmirDTO> createEmir(@Valid @RequestBody EmirDTO emirDTO) throws URISyntaxException {
        log.debug("REST request to save Emir : {}", emirDTO);
        if (emirDTO.getId() != null) {
            throw new BadRequestAlertException("A new emir cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmirDTO result = emirService.save(emirDTO);
        return ResponseEntity.created(new URI("/api/emirs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emirs : Updates an existing emir.
     *
     * @param emirDTO the emirDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emirDTO,
     * or with status 400 (Bad Request) if the emirDTO is not valid,
     * or with status 500 (Internal Server Error) if the emirDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/emirs")
    @Timed
    public ResponseEntity<EmirDTO> updateEmir(@Valid @RequestBody EmirDTO emirDTO) throws URISyntaxException {
        log.debug("REST request to update Emir : {}", emirDTO);
        if (emirDTO.getId() == null) {
            return createEmir(emirDTO);
        }
        EmirDTO result = emirService.save(emirDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emirDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emirs : get all the emirs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of emirs in body
     */
    @GetMapping("/emirs")
    @Timed
    public ResponseEntity<List<EmirDTO>> getAllEmirs(Pageable pageable) {
        log.debug("REST request to get a page of Emirs");
        Page<EmirDTO> page = SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(user -> {
                Predicate<Authority> p = a -> a.getName().equals(AuthoritiesConstants.USER_KURYE);
                if (user.getAuthorities().stream().anyMatch(p)) {
                    return emirService.findAllByKuryeId(pageable, user.getId());
                }
                p = a -> a.getName().equals(AuthoritiesConstants.USER_ISYERI);
                if (user.getAuthorities().stream().anyMatch(p)) {
                    Isyeri isyeri = isyeriRepository.findOneByUserId(user.getId());
                    return emirService.findAllByIsyeriId(pageable, isyeri.getId());
                }
                p = a -> a.getName().equals(AuthoritiesConstants.ADMIN);
                if (user.getAuthorities().stream().anyMatch(p)) {
                    return emirService.findAll(pageable);
                }
                return new PageImpl<EmirDTO>(new ArrayList<EmirDTO>());
            }).get();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/emirs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /emirs/:id : get the "id" emir.
     *
     * @param id the id of the emirDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emirDTO, or with status 404 (Not Found)
     */
    @GetMapping("/emirs/{id}")
    @Timed
    public ResponseEntity<EmirDTO> getEmir(@PathVariable Long id) {
        log.debug("REST request to get Emir : {}", id);
        EmirDTO emirDTO = emirService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(emirDTO));
    }

    /**
     * DELETE  /emirs/:id : delete the "id" emir.
     *
     * @param id the id of the emirDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emirs/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmir(@PathVariable Long id) {
        log.debug("REST request to delete Emir : {}", id);
        emirService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
