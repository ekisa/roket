package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.EmirGecmisi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmirGecmisi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmirGecmisiRepository extends JpaRepository<EmirGecmisi, Long> {

}
