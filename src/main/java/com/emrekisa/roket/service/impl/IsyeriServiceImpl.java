package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.IsyeriService;
import com.emrekisa.roket.domain.Isyeri;
import com.emrekisa.roket.repository.IsyeriRepository;
import com.emrekisa.roket.service.dto.IsyeriDTO;
import com.emrekisa.roket.service.mapper.IsyeriMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Isyeri.
 */
@Service
@Transactional
public class IsyeriServiceImpl implements IsyeriService {

    private final Logger log = LoggerFactory.getLogger(IsyeriServiceImpl.class);

    private final IsyeriRepository isyeriRepository;

    private final IsyeriMapper isyeriMapper;

    public IsyeriServiceImpl(IsyeriRepository isyeriRepository, IsyeriMapper isyeriMapper) {
        this.isyeriRepository = isyeriRepository;
        this.isyeriMapper = isyeriMapper;
    }

    /**
     * Save a isyeri.
     *
     * @param isyeriDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IsyeriDTO save(IsyeriDTO isyeriDTO) {
        log.debug("Request to save Isyeri : {}", isyeriDTO);
        Isyeri isyeri = isyeriMapper.toEntity(isyeriDTO);
        isyeri = isyeriRepository.save(isyeri);
        return isyeriMapper.toDto(isyeri);
    }

    /**
     * Get all the isyeris.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IsyeriDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Isyeris");
        return isyeriRepository.findAll(pageable)
            .map(isyeriMapper::toDto);
    }

    /**
     * Get one isyeri by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IsyeriDTO findOne(Long id) {
        log.debug("Request to get Isyeri : {}", id);
        Isyeri isyeri = isyeriRepository.findOne(id);
        return isyeriMapper.toDto(isyeri);
    }

    /**
     * Delete the isyeri by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Isyeri : {}", id);
        isyeriRepository.delete(id);
    }
}
