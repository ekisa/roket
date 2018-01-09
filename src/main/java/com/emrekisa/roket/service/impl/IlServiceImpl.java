package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.IlService;
import com.emrekisa.roket.domain.Il;
import com.emrekisa.roket.repository.IlRepository;
import com.emrekisa.roket.service.dto.IlDTO;
import com.emrekisa.roket.service.mapper.IlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Il.
 */
@Service
@Transactional
public class IlServiceImpl implements IlService {

    private final Logger log = LoggerFactory.getLogger(IlServiceImpl.class);

    private final IlRepository ilRepository;

    private final IlMapper ilMapper;

    public IlServiceImpl(IlRepository ilRepository, IlMapper ilMapper) {
        this.ilRepository = ilRepository;
        this.ilMapper = ilMapper;
    }

    /**
     * Save a il.
     *
     * @param ilDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IlDTO save(IlDTO ilDTO) {
        log.debug("Request to save Il : {}", ilDTO);
        Il il = ilMapper.toEntity(ilDTO);
        il = ilRepository.save(il);
        return ilMapper.toDto(il);
    }

    /**
     * Get all the ils.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IlDTO> findAll() {
        log.debug("Request to get all Ils");
        return ilRepository.findAll().stream()
            .map(ilMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one il by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IlDTO findOne(Long id) {
        log.debug("Request to get Il : {}", id);
        Il il = ilRepository.findOne(id);
        return ilMapper.toDto(il);
    }

    /**
     * Delete the il by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Il : {}", id);
        ilRepository.delete(id);
    }
}
