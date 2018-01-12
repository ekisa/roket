package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Musteri;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Musteri entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MusteriRepository extends JpaRepository<Musteri, Long> {

}
