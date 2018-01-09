package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Fatura;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Fatura entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {

}
