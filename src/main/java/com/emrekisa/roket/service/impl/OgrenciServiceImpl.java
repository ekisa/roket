package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.OgrenciService;
import com.emrekisa.roket.domain.Ogrenci;
import com.emrekisa.roket.repository.OgrenciRepository;
import com.emrekisa.roket.service.dto.OgrenciDTO;
import com.emrekisa.roket.service.mapper.OgrenciMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Ogrenci.
 */
@Service
@Transactional
public class OgrenciServiceImpl implements OgrenciService {

    private final Logger log = LoggerFactory.getLogger(OgrenciServiceImpl.class);

    private final OgrenciRepository ogrenciRepository;

    private final OgrenciMapper ogrenciMapper;

    public OgrenciServiceImpl(OgrenciRepository ogrenciRepository, OgrenciMapper ogrenciMapper) {
        this.ogrenciRepository = ogrenciRepository;
        this.ogrenciMapper = ogrenciMapper;
    }

    /**
     * Save a ogrenci.
     *
     * @param ogrenciDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OgrenciDTO save(OgrenciDTO ogrenciDTO) {
        log.debug("Request to save Ogrenci : {}", ogrenciDTO);
        Ogrenci ogrenci = ogrenciMapper.toEntity(ogrenciDTO);
        ogrenci = ogrenciRepository.save(ogrenci);
        return ogrenciMapper.toDto(ogrenci);
    }

    /**
     * Get all the ogrencis.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OgrenciDTO> findAll() {
        log.debug("Request to get all Ogrencis");
        return ogrenciRepository.findAllWithEagerRelationships().stream()
            .map(ogrenciMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ogrenci by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OgrenciDTO findOne(Long id) {
        log.debug("Request to get Ogrenci : {}", id);
        Ogrenci ogrenci = ogrenciRepository.findOneWithEagerRelationships(id);
        return ogrenciMapper.toDto(ogrenci);
    }

    /**
     * Delete the ogrenci by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ogrenci : {}", id);
        ogrenciRepository.delete(id);
    }
}
