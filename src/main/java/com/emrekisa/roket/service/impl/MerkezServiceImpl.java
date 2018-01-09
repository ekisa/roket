package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.MerkezService;
import com.emrekisa.roket.domain.Merkez;
import com.emrekisa.roket.repository.MerkezRepository;
import com.emrekisa.roket.service.dto.MerkezDTO;
import com.emrekisa.roket.service.mapper.MerkezMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Merkez.
 */
@Service
@Transactional
public class MerkezServiceImpl implements MerkezService {

    private final Logger log = LoggerFactory.getLogger(MerkezServiceImpl.class);

    private final MerkezRepository merkezRepository;

    private final MerkezMapper merkezMapper;

    public MerkezServiceImpl(MerkezRepository merkezRepository, MerkezMapper merkezMapper) {
        this.merkezRepository = merkezRepository;
        this.merkezMapper = merkezMapper;
    }

    /**
     * Save a merkez.
     *
     * @param merkezDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MerkezDTO save(MerkezDTO merkezDTO) {
        log.debug("Request to save Merkez : {}", merkezDTO);
        Merkez merkez = merkezMapper.toEntity(merkezDTO);
        merkez = merkezRepository.save(merkez);
        return merkezMapper.toDto(merkez);
    }

    /**
     * Get all the merkezs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MerkezDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Merkezs");
        return merkezRepository.findAll(pageable)
            .map(merkezMapper::toDto);
    }

    /**
     * Get one merkez by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MerkezDTO findOne(Long id) {
        log.debug("Request to get Merkez : {}", id);
        Merkez merkez = merkezRepository.findOne(id);
        return merkezMapper.toDto(merkez);
    }

    /**
     * Delete the merkez by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Merkez : {}", id);
        merkezRepository.delete(id);
    }
}
