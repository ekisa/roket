package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.FaturaService;
import com.emrekisa.roket.domain.Fatura;
import com.emrekisa.roket.repository.FaturaRepository;
import com.emrekisa.roket.service.dto.FaturaDTO;
import com.emrekisa.roket.service.mapper.FaturaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Fatura.
 */
@Service
@Transactional
public class FaturaServiceImpl implements FaturaService {

    private final Logger log = LoggerFactory.getLogger(FaturaServiceImpl.class);

    private final FaturaRepository faturaRepository;

    private final FaturaMapper faturaMapper;

    public FaturaServiceImpl(FaturaRepository faturaRepository, FaturaMapper faturaMapper) {
        this.faturaRepository = faturaRepository;
        this.faturaMapper = faturaMapper;
    }

    /**
     * Save a fatura.
     *
     * @param faturaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FaturaDTO save(FaturaDTO faturaDTO) {
        log.debug("Request to save Fatura : {}", faturaDTO);
        Fatura fatura = faturaMapper.toEntity(faturaDTO);
        fatura = faturaRepository.save(fatura);
        return faturaMapper.toDto(fatura);
    }

    /**
     * Get all the faturas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FaturaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Faturas");
        return faturaRepository.findAll(pageable)
            .map(faturaMapper::toDto);
    }

    /**
     * Get one fatura by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FaturaDTO findOne(Long id) {
        log.debug("Request to get Fatura : {}", id);
        Fatura fatura = faturaRepository.findOne(id);
        return faturaMapper.toDto(fatura);
    }

    /**
     * Delete the fatura by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fatura : {}", id);
        faturaRepository.delete(id);
    }
}
