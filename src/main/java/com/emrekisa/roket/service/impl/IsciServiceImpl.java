package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.IsciService;
import com.emrekisa.roket.domain.Isci;
import com.emrekisa.roket.repository.IsciRepository;
import com.emrekisa.roket.service.dto.IsciDTO;
import com.emrekisa.roket.service.mapper.IsciMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Isci.
 */
@Service
@Transactional
public class IsciServiceImpl implements IsciService {

    private final Logger log = LoggerFactory.getLogger(IsciServiceImpl.class);

    private final IsciRepository isciRepository;

    private final IsciMapper isciMapper;

    public IsciServiceImpl(IsciRepository isciRepository, IsciMapper isciMapper) {
        this.isciRepository = isciRepository;
        this.isciMapper = isciMapper;
    }

    /**
     * Save a isci.
     *
     * @param isciDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IsciDTO save(IsciDTO isciDTO) {
        log.debug("Request to save Isci : {}", isciDTO);
        Isci isci = isciMapper.toEntity(isciDTO);
        isci = isciRepository.save(isci);
        return isciMapper.toDto(isci);
    }

    /**
     * Get all the iscis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IsciDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Iscis");
        return isciRepository.findAll(pageable)
            .map(isciMapper::toDto);
    }

    /**
     * Get one isci by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IsciDTO findOne(Long id) {
        log.debug("Request to get Isci : {}", id);
        Isci isci = isciRepository.findOne(id);
        return isciMapper.toDto(isci);
    }

    /**
     * Delete the isci by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Isci : {}", id);
        isciRepository.delete(id);
    }
}
