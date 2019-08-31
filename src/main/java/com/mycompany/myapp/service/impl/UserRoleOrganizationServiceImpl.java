package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.UserRoleOrganizationService;
import com.mycompany.myapp.domain.UserRoleOrganization;
import com.mycompany.myapp.repository.UserRoleOrganizationRepository;
import com.mycompany.myapp.service.dto.UserRoleOrganizationDTO;
import com.mycompany.myapp.service.mapper.UserRoleOrganizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserRoleOrganization}.
 */
@Service
@Transactional
public class UserRoleOrganizationServiceImpl implements UserRoleOrganizationService {

    private final Logger log = LoggerFactory.getLogger(UserRoleOrganizationServiceImpl.class);

    private final UserRoleOrganizationRepository userRoleOrganizationRepository;

    private final UserRoleOrganizationMapper userRoleOrganizationMapper;

    public UserRoleOrganizationServiceImpl(UserRoleOrganizationRepository userRoleOrganizationRepository, UserRoleOrganizationMapper userRoleOrganizationMapper) {
        this.userRoleOrganizationRepository = userRoleOrganizationRepository;
        this.userRoleOrganizationMapper = userRoleOrganizationMapper;
    }

    /**
     * Save a userRoleOrganization.
     *
     * @param userRoleOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserRoleOrganizationDTO save(UserRoleOrganizationDTO userRoleOrganizationDTO) {
        log.debug("Request to save UserRoleOrganization : {}", userRoleOrganizationDTO);
        UserRoleOrganization userRoleOrganization = userRoleOrganizationMapper.toEntity(userRoleOrganizationDTO);
        userRoleOrganization = userRoleOrganizationRepository.save(userRoleOrganization);
        return userRoleOrganizationMapper.toDto(userRoleOrganization);
    }

    /**
     * Get all the userRoleOrganizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserRoleOrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserRoleOrganizations");
        return userRoleOrganizationRepository.findAll(pageable)
            .map(userRoleOrganizationMapper::toDto);
    }


    /**
     * Get one userRoleOrganization by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserRoleOrganizationDTO> findOne(Long id) {
        log.debug("Request to get UserRoleOrganization : {}", id);
        return userRoleOrganizationRepository.findById(id)
            .map(userRoleOrganizationMapper::toDto);
    }

    /**
     * Delete the userRoleOrganization by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserRoleOrganization : {}", id);
        userRoleOrganizationRepository.deleteById(id);
    }
}
