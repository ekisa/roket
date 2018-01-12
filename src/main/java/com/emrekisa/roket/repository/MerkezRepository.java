package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Merkez;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Merkez entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerkezRepository extends JpaRepository<Merkez, Long> {

}
