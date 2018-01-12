package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.SinifService;
import com.emrekisa.roket.domain.Sinif;
import com.emrekisa.roket.repository.SinifRepository;
import com.emrekisa.roket.service.dto.SinifDTO;
import com.emrekisa.roket.service.mapper.SinifMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Sinif.
 */
@Service
@Transactional
public class SinifServiceImpl implements SinifService {

    private final Logger log = LoggerFactory.getLogger(SinifServiceImpl.class);

    private final SinifRepository sinifRepository;

    private final SinifMapper sinifMapper;

    public SinifServiceImpl(SinifRepository sinifRepository, SinifMapper sinifMapper) {
        this.sinifRepository = sinifRepository;
        this.sinifMapper = sinifMapper;
    }

    /**
     * Save a sinif.
     *
     * @param sinifDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SinifDTO save(SinifDTO sinifDTO) {
        log.debug("Request to save Sinif : {}", sinifDTO);
        Sinif sinif = sinifMapper.toEntity(sinifDTO);
        sinif = sinifRepository.save(sinif);
        return sinifMapper.toDto(sinif);
    }

    /**
     * Get all the sinifs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SinifDTO> findAll() {
        log.debug("Request to get all Sinifs");
        return sinifRepository.findAll().stream()
            .map(sinifMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sinif by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SinifDTO findOne(Long id) {
        log.debug("Request to get Sinif : {}", id);
        Sinif sinif = sinifRepository.findOne(id);
        return sinifMapper.toDto(sinif);
    }

    /**
     * Delete the sinif by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sinif : {}", id);
        sinifRepository.delete(id);
    }
}
