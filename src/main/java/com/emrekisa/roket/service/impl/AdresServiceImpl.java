package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.AdresService;
import com.emrekisa.roket.domain.Adres;
import com.emrekisa.roket.repository.AdresRepository;
import com.emrekisa.roket.service.dto.AdresDTO;
import com.emrekisa.roket.service.mapper.AdresMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Adres.
 */
@Service
@Transactional
public class AdresServiceImpl implements AdresService {

    private final Logger log = LoggerFactory.getLogger(AdresServiceImpl.class);

    private final AdresRepository adresRepository;

    private final AdresMapper adresMapper;

    public AdresServiceImpl(AdresRepository adresRepository, AdresMapper adresMapper) {
        this.adresRepository = adresRepository;
        this.adresMapper = adresMapper;
    }

    /**
     * Save a adres.
     *
     * @param adresDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdresDTO save(AdresDTO adresDTO) {
        log.debug("Request to save Adres : {}", adresDTO);
        Adres adres = adresMapper.toEntity(adresDTO);
        adres = adresRepository.save(adres);
        return adresMapper.toDto(adres);
    }

    /**
     * Get all the adres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdresDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Adres");
        return adresRepository.findAll(pageable)
            .map(adresMapper::toDto);
    }

    /**
     * Get one adres by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdresDTO findOne(Long id) {
        log.debug("Request to get Adres : {}", id);
        Adres adres = adresRepository.findOne(id);
        return adresMapper.toDto(adres);
    }

    /**
     * Delete the adres by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adres : {}", id);
        adresRepository.delete(id);
    }
}
