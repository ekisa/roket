package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Emir;
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

    Page<Emir> findAllByIsyeriId(Pageable pageable, Long id);

    Page<Emir> findAllByKuryeId(Pageable pageable, Long id);
}
