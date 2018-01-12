package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.MahalleService;
import com.emrekisa.roket.domain.Mahalle;
import com.emrekisa.roket.repository.MahalleRepository;
import com.emrekisa.roket.service.dto.MahalleDTO;
import com.emrekisa.roket.service.mapper.MahalleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Mahalle.
 */
@Service
@Transactional
public class MahalleServiceImpl implements MahalleService {

    private final Logger log = LoggerFactory.getLogger(MahalleServiceImpl.class);

    private final MahalleRepository mahalleRepository;

    private final MahalleMapper mahalleMapper;

    public MahalleServiceImpl(MahalleRepository mahalleRepository, MahalleMapper mahalleMapper) {
        this.mahalleRepository = mahalleRepository;
        this.mahalleMapper = mahalleMapper;
    }

    /**
     * Save a mahalle.
     *
     * @param mahalleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MahalleDTO save(MahalleDTO mahalleDTO) {
        log.debug("Request to save Mahalle : {}", mahalleDTO);
        Mahalle mahalle = mahalleMapper.toEntity(mahalleDTO);
        mahalle = mahalleRepository.save(mahalle);
        return mahalleMapper.toDto(mahalle);
    }

    /**
     * Get all the mahalles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MahalleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mahalles");
        return mahalleRepository.findAll(pageable)
            .map(mahalleMapper::toDto);
    }

    /**
     * Get one mahalle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MahalleDTO findOne(Long id) {
        log.debug("Request to get Mahalle : {}", id);
        Mahalle mahalle = mahalleRepository.findOne(id);
        return mahalleMapper.toDto(mahalle);
    }

    /**
     * Delete the mahalle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mahalle : {}", id);
        mahalleRepository.delete(id);
    }
}
