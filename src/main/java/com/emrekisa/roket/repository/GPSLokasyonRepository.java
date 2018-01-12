package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.GPSLokasyon;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GPSLokasyon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GPSLokasyonRepository extends JpaRepository<GPSLokasyon, Long> {

}
