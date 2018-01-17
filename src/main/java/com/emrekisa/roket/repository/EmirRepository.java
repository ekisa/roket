package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Emir;
import com.emrekisa.roket.domain.enumeration.EMIR_STATU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Emir entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmirRepository extends JpaRepository<Emir, Long> {

    Page<Emir> findAllByIsyeriIdAndStatu(Pageable pageable, Long id, EMIR_STATU statu);

    Page<Emir> findAllByKuryeIdAndStatu(Pageable pageable, Long id, EMIR_STATU statu);

    Page<Emir> findAllByStatu(Pageable pageable, EMIR_STATU statu);
}
