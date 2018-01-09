package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.EmirGecmisiService;
import com.emrekisa.roket.domain.EmirGecmisi;
import com.emrekisa.roket.repository.EmirGecmisiRepository;
import com.emrekisa.roket.service.dto.EmirGecmisiDTO;
import com.emrekisa.roket.service.mapper.EmirGecmisiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EmirGecmisi.
 */
@Service
@Transactional
public class EmirGecmisiServiceImpl implements EmirGecmisiService {

    private final Logger log = LoggerFactory.getLogger(EmirGecmisiServiceImpl.class);

    private final EmirGecmisiRepository emirGecmisiRepository;

    private final EmirGecmisiMapper emirGecmisiMapper;

    public EmirGecmisiServiceImpl(EmirGecmisiRepository emirGecmisiRepository, EmirGecmisiMapper emirGecmisiMapper) {
        this.emirGecmisiRepository = emirGecmisiRepository;
        this.emirGecmisiMapper = emirGecmisiMapper;
    }

    /**
     * Save a emirGecmisi.
     *
     * @param emirGecmisiDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmirGecmisiDTO save(EmirGecmisiDTO emirGecmisiDTO) {
        log.debug("Request to save EmirGecmisi : {}", emirGecmisiDTO);
        EmirGecmisi emirGecmisi = emirGecmisiMapper.toEntity(emirGecmisiDTO);
        emirGecmisi = emirGecmisiRepository.save(emirGecmisi);
        return emirGecmisiMapper.toDto(emirGecmisi);
    }

    /**
     * Get all the emirGecmisis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmirGecmisiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmirGecmisis");
        return emirGecmisiRepository.findAll(pageable)
            .map(emirGecmisiMapper::toDto);
    }

    /**
     * Get one emirGecmisi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmirGecmisiDTO findOne(Long id) {
        log.debug("Request to get EmirGecmisi : {}", id);
        EmirGecmisi emirGecmisi = emirGecmisiRepository.findOne(id);
        return emirGecmisiMapper.toDto(emirGecmisi);
    }

    /**
     * Delete the emirGecmisi by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmirGecmisi : {}", id);
        emirGecmisiRepository.delete(id);
    }
}
