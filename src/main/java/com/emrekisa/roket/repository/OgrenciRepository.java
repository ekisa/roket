package com.emrekisa.roket.repository;

import com.emrekisa.roket.domain.Ogrenci;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Ogrenci entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {
    @Query("select distinct ogrenci from Ogrenci ogrenci left join fetch ogrenci.siniflaris")
    List<Ogrenci> findAllWithEagerRelationships();

    @Query("select ogrenci from Ogrenci ogrenci left join fetch ogrenci.siniflaris where ogrenci.id =:id")
    Ogrenci findOneWithEagerRelationships(@Param("id") Long id);

}
