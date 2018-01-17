package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.EmirService;
import com.emrekisa.roket.domain.Emir;
import com.emrekisa.roket.repository.EmirRepository;
import com.emrekisa.roket.service.dto.EmirDTO;
import com.emrekisa.roket.service.mapper.EmirMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Emir.
 */
@Service
@Transactional
public class EmirServiceImpl implements EmirService {

    private final Logger log = LoggerFactory.getLogger(EmirServiceImpl.class);

    private final EmirRepository emirRepository;

    private final EmirMapper emirMapper;

    public EmirServiceImpl(EmirRepository emirRepository, EmirMapper emirMapper) {
        this.emirRepository = emirRepository;
        this.emirMapper = emirMapper;
    }

    /**
     * Save a emir.
     *
     * @param emirDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmirDTO save(EmirDTO emirDTO) {
        log.debug("Request to save Emir : {}", emirDTO);
        Emir emir = emirMapper.toEntity(emirDTO);
        emir = emirRepository.save(emir);
        return emirMapper.toDto(emir);
    }

    /**
     * Get all the emirs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmirDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Emirs");
        return emirRepository.findAll(pageable)
            .map(emirMapper::toDto);
    }

    /**
     * Get one emir by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmirDTO findOne(Long id) {
        log.debug("Request to get Emir : {}", id);
        Emir emir = emirRepository.findOne(id);
        return emirMapper.toDto(emir);
    }

    /**
     * Delete the emir by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Emir : {}", id);
        emirRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmirDTO> findAllByIsyeriId(Pageable pageable, Long id) {
        return emirRepository.findAllByIsyeriId(pageable, id).map(emirMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmirDTO> findAllByKuryeId(Pageable pageable, Long id) {
        return emirRepository.findAllByKuryeId(pageable, id).map(emirMapper::toDto);
    }
}
