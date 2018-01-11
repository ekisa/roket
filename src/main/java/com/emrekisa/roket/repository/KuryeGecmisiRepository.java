package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.KuryeGecmisi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the KuryeGecmisi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KuryeGecmisiRepository extends JpaRepository<KuryeGecmisi, Long> {

}
