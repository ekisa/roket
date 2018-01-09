package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Motor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Motor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotorRepository extends JpaRepository<Motor, Long> {

}
