package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.IlceService;
import com.emrekisa.roket.domain.Ilce;
import com.emrekisa.roket.repository.IlceRepository;
import com.emrekisa.roket.service.dto.IlceDTO;
import com.emrekisa.roket.service.mapper.IlceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Ilce.
 */
@Service
@Transactional
public class IlceServiceImpl implements IlceService {

    private final Logger log = LoggerFactory.getLogger(IlceServiceImpl.class);

    private final IlceRepository ilceRepository;

    private final IlceMapper ilceMapper;

    public IlceServiceImpl(IlceRepository ilceRepository, IlceMapper ilceMapper) {
        this.ilceRepository = ilceRepository;
        this.ilceMapper = ilceMapper;
    }

    /**
     * Save a ilce.
     *
     * @param ilceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IlceDTO save(IlceDTO ilceDTO) {
        log.debug("Request to save Ilce : {}", ilceDTO);
        Ilce ilce = ilceMapper.toEntity(ilceDTO);
        ilce = ilceRepository.save(ilce);
        return ilceMapper.toDto(ilce);
    }

    /**
     * Get all the ilces.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IlceDTO> findAll() {
        log.debug("Request to get all Ilces");
        return ilceRepository.findAll().stream()
            .map(ilceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ilce by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IlceDTO findOne(Long id) {
        log.debug("Request to get Ilce : {}", id);
        Ilce ilce = ilceRepository.findOne(id);
        return ilceMapper.toDto(ilce);
    }

    /**
     * Delete the ilce by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ilce : {}", id);
        ilceRepository.delete(id);
    }
}
