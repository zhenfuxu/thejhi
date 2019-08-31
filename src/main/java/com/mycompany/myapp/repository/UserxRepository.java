package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Userx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Userx entity.
 */
@Repository
public interface UserxRepository extends JpaRepository<Userx, Long>, JpaSpecificationExecutor<Userx> {

    @Query(value = "select distinct userx from Userx userx left join fetch userx.roles",
        countQuery = "select count(distinct userx) from Userx userx")
    Page<Userx> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct userx from Userx userx left join fetch userx.roles")
    List<Userx> findAllWithEagerRelationships();

    @Query("select userx from Userx userx left join fetch userx.roles where userx.id =:id")
    Optional<Userx> findOneWithEagerRelationships(@Param("id") Long id);

}
