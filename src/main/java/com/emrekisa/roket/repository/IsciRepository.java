package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Isci;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Isci entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IsciRepository extends JpaRepository<Isci, Long> {

}
