package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.GPSLokasyonService;
import com.emrekisa.roket.domain.GPSLokasyon;
import com.emrekisa.roket.repository.GPSLokasyonRepository;
import com.emrekisa.roket.service.dto.GPSLokasyonDTO;
import com.emrekisa.roket.service.mapper.GPSLokasyonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing GPSLokasyon.
 */
@Service
@Transactional
public class GPSLokasyonServiceImpl implements GPSLokasyonService {

    private final Logger log = LoggerFactory.getLogger(GPSLokasyonServiceImpl.class);

    private final GPSLokasyonRepository gPSLokasyonRepository;

    private final GPSLokasyonMapper gPSLokasyonMapper;

    public GPSLokasyonServiceImpl(GPSLokasyonRepository gPSLokasyonRepository, GPSLokasyonMapper gPSLokasyonMapper) {
        this.gPSLokasyonRepository = gPSLokasyonRepository;
        this.gPSLokasyonMapper = gPSLokasyonMapper;
    }

    /**
     * Save a gPSLokasyon.
     *
     * @param gPSLokasyonDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GPSLokasyonDTO save(GPSLokasyonDTO gPSLokasyonDTO) {
        log.debug("Request to save GPSLokasyon : {}", gPSLokasyonDTO);
        GPSLokasyon gPSLokasyon = gPSLokasyonMapper.toEntity(gPSLokasyonDTO);
        gPSLokasyon = gPSLokasyonRepository.save(gPSLokasyon);
        return gPSLokasyonMapper.toDto(gPSLokasyon);
    }

    /**
     * Get all the gPSLokasyons.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GPSLokasyonDTO> findAll() {
        log.debug("Request to get all GPSLokasyons");
        return gPSLokasyonRepository.findAll().stream()
            .map(gPSLokasyonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one gPSLokasyon by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GPSLokasyonDTO findOne(Long id) {
        log.debug("Request to get GPSLokasyon : {}", id);
        GPSLokasyon gPSLokasyon = gPSLokasyonRepository.findOne(id);
        return gPSLokasyonMapper.toDto(gPSLokasyon);
    }

    /**
     * Delete the gPSLokasyon by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GPSLokasyon : {}", id);
        gPSLokasyonRepository.delete(id);
    }
}
