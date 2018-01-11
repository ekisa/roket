package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.KuryeService;
import com.emrekisa.roket.domain.Kurye;
import com.emrekisa.roket.repository.KuryeRepository;
import com.emrekisa.roket.service.dto.KuryeDTO;
import com.emrekisa.roket.service.mapper.KuryeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Kurye.
 */
@Service
@Transactional
public class KuryeServiceImpl implements KuryeService {

    private final Logger log = LoggerFactory.getLogger(KuryeServiceImpl.class);

    private final KuryeRepository kuryeRepository;

    private final KuryeMapper kuryeMapper;

    public KuryeServiceImpl(KuryeRepository kuryeRepository, KuryeMapper kuryeMapper) {
        this.kuryeRepository = kuryeRepository;
        this.kuryeMapper = kuryeMapper;
    }

    /**
     * Save a kurye.
     *
     * @param kuryeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KuryeDTO save(KuryeDTO kuryeDTO) {
        log.debug("Request to save Kurye : {}", kuryeDTO);
        Kurye kurye = kuryeMapper.toEntity(kuryeDTO);
        kurye = kuryeRepository.save(kurye);
        return kuryeMapper.toDto(kurye);
    }

    /**
     * Get all the kuryes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KuryeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Kuryes");
        return kuryeRepository.findAll(pageable)
            .map(kuryeMapper::toDto);
    }

    /**
     * Get one kurye by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KuryeDTO findOne(Long id) {
        log.debug("Request to get Kurye : {}", id);
        Kurye kurye = kuryeRepository.findOne(id);
        return kuryeMapper.toDto(kurye);
    }

    /**
     * Delete the kurye by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Kurye : {}", id);
        kuryeRepository.delete(id);
    }
}
