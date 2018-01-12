package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Kurye;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Kurye entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KuryeRepository extends JpaRepository<Kurye, Long> {

}
