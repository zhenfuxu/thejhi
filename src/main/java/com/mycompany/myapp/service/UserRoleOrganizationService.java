package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.UserRoleOrganizationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.UserRoleOrganization}.
 */
public interface UserRoleOrganizationService {

    /**
     * Save a userRoleOrganization.
     *
     * @param userRoleOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    UserRoleOrganizationDTO save(UserRoleOrganizationDTO userRoleOrganizationDTO);

    /**
     * Get all the userRoleOrganizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserRoleOrganizationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userRoleOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserRoleOrganizationDTO> findOne(Long id);

    /**
     * Delete the "id" userRoleOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
