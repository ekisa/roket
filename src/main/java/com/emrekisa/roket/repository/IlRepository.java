package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Il;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Il entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IlRepository extends JpaRepository<Il, Long> {

}
