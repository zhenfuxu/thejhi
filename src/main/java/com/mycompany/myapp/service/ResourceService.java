package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ResourceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Resource}.
 */
public interface ResourceService {

    /**
     * Save a resource.
     *
     * @param resourceDTO the entity to save.
     * @return the persisted entity.
     */
    ResourceDTO save(ResourceDTO resourceDTO);

    /**
     * Get all the resources.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResourceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" resource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResourceDTO> findOne(Long id);

    /**
     * Delete the "id" resource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
