package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Isyeri;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Isyeri entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IsyeriRepository extends JpaRepository<Isyeri, Long> {
    Isyeri findOneByUserId(Long userId);
    List<Isyeri> findAllByMusteriId(Long musteriId);
}
