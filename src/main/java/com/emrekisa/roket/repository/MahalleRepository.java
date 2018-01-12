package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Mahalle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Mahalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MahalleRepository extends JpaRepository<Mahalle, Long> {

}
