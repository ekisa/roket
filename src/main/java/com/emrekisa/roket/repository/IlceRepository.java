package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Ilce;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ilce entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IlceRepository extends JpaRepository<Ilce, Long> {

}
