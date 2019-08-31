package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.UserxDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Userx}.
 */
public interface UserxService {

    /**
     * Save a userx.
     *
     * @param userxDTO the entity to save.
     * @return the persisted entity.
     */
    UserxDTO save(UserxDTO userxDTO);

    /**
     * Get all the userxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserxDTO> findAll(Pageable pageable);

    /**
     * Get all the userxes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<UserxDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" userx.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserxDTO> findOne(Long id);

    /**
     * Delete the "id" userx.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
