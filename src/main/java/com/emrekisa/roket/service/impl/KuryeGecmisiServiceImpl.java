package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.KuryeGecmisiService;
import com.emrekisa.roket.domain.KuryeGecmisi;
import com.emrekisa.roket.repository.KuryeGecmisiRepository;
import com.emrekisa.roket.service.dto.KuryeGecmisiDTO;
import com.emrekisa.roket.service.mapper.KuryeGecmisiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing KuryeGecmisi.
 */
@Service
@Transactional
public class KuryeGecmisiServiceImpl implements KuryeGecmisiService {

    private final Logger log = LoggerFactory.getLogger(KuryeGecmisiServiceImpl.class);

    private final KuryeGecmisiRepository kuryeGecmisiRepository;

    private final KuryeGecmisiMapper kuryeGecmisiMapper;

    public KuryeGecmisiServiceImpl(KuryeGecmisiRepository kuryeGecmisiRepository, KuryeGecmisiMapper kuryeGecmisiMapper) {
        this.kuryeGecmisiRepository = kuryeGecmisiRepository;
        this.kuryeGecmisiMapper = kuryeGecmisiMapper;
    }

    /**
     * Save a kuryeGecmisi.
     *
     * @param kuryeGecmisiDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KuryeGecmisiDTO save(KuryeGecmisiDTO kuryeGecmisiDTO) {
        log.debug("Request to save KuryeGecmisi : {}", kuryeGecmisiDTO);
        KuryeGecmisi kuryeGecmisi = kuryeGecmisiMapper.toEntity(kuryeGecmisiDTO);
        kuryeGecmisi = kuryeGecmisiRepository.save(kuryeGecmisi);
        return kuryeGecmisiMapper.toDto(kuryeGecmisi);
    }

    /**
     * Get all the kuryeGecmisis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KuryeGecmisiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KuryeGecmisis");
        return kuryeGecmisiRepository.findAll(pageable)
            .map(kuryeGecmisiMapper::toDto);
    }

    /**
     * Get one kuryeGecmisi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KuryeGecmisiDTO findOne(Long id) {
        log.debug("Request to get KuryeGecmisi : {}", id);
        KuryeGecmisi kuryeGecmisi = kuryeGecmisiRepository.findOne(id);
        return kuryeGecmisiMapper.toDto(kuryeGecmisi);
    }

    /**
     * Delete the kuryeGecmisi by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KuryeGecmisi : {}", id);
        kuryeGecmisiRepository.delete(id);
    }
}
