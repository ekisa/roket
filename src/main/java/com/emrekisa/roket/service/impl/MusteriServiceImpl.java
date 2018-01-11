package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.MusteriService;
import com.emrekisa.roket.domain.Musteri;
import com.emrekisa.roket.repository.MusteriRepository;
import com.emrekisa.roket.service.dto.MusteriDTO;
import com.emrekisa.roket.service.mapper.MusteriMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Musteri.
 */
@Service
@Transactional
public class MusteriServiceImpl implements MusteriService {

    private final Logger log = LoggerFactory.getLogger(MusteriServiceImpl.class);

    private final MusteriRepository musteriRepository;

    private final MusteriMapper musteriMapper;

    public MusteriServiceImpl(MusteriRepository musteriRepository, MusteriMapper musteriMapper) {
        this.musteriRepository = musteriRepository;
        this.musteriMapper = musteriMapper;
    }

    /**
     * Save a musteri.
     *
     * @param musteriDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MusteriDTO save(MusteriDTO musteriDTO) {
        log.debug("Request to save Musteri : {}", musteriDTO);
        Musteri musteri = musteriMapper.toEntity(musteriDTO);
        musteri = musteriRepository.save(musteri);
        return musteriMapper.toDto(musteri);
    }

    /**
     * Get all the musteris.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MusteriDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Musteris");
        return musteriRepository.findAll(pageable)
            .map(musteriMapper::toDto);
    }

    /**
     * Get one musteri by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MusteriDTO findOne(Long id) {
        log.debug("Request to get Musteri : {}", id);
        Musteri musteri = musteriRepository.findOne(id);
        return musteriMapper.toDto(musteri);
    }

    /**
     * Delete the musteri by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Musteri : {}", id);
        musteriRepository.delete(id);
    }
}
