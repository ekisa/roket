package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Emir;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Emir entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmirRepository extends JpaRepository<Emir, Long> {

}
