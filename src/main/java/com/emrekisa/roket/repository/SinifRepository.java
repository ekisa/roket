package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Sinif;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sinif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SinifRepository extends JpaRepository<Sinif, Long> {

}
