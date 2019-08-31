package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.UserRoleOrganization;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.UserRoleOrganizationRepository;
import com.mycompany.myapp.service.dto.UserRoleOrganizationCriteria;
import com.mycompany.myapp.service.dto.UserRoleOrganizationDTO;
import com.mycompany.myapp.service.mapper.UserRoleOrganizationMapper;

/**
 * Service for executing complex queries for {@link UserRoleOrganization} entities in the database.
 * The main input is a {@link UserRoleOrganizationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserRoleOrganizationDTO} or a {@link Page} of {@link UserRoleOrganizationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserRoleOrganizationQueryService extends QueryService<UserRoleOrganization> {

    private final Logger log = LoggerFactory.getLogger(UserRoleOrganizationQueryService.class);

    private final UserRoleOrganizationRepository userRoleOrganizationRepository;

    private final UserRoleOrganizationMapper userRoleOrganizationMapper;

    public UserRoleOrganizationQueryService(UserRoleOrganizationRepository userRoleOrganizationRepository, UserRoleOrganizationMapper userRoleOrganizationMapper) {
        this.userRoleOrganizationRepository = userRoleOrganizationRepository;
        this.userRoleOrganizationMapper = userRoleOrganizationMapper;
    }

    /**
     * Return a {@link List} of {@link UserRoleOrganizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserRoleOrganizationDTO> findByCriteria(UserRoleOrganizationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserRoleOrganization> specification = createSpecification(criteria);
        return userRoleOrganizationMapper.toDto(userRoleOrganizationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserRoleOrganizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserRoleOrganizationDTO> findByCriteria(UserRoleOrganizationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserRoleOrganization> specification = createSpecification(criteria);
        return userRoleOrganizationRepository.findAll(specification, page)
            .map(userRoleOrganizationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserRoleOrganizationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserRoleOrganization> specification = createSpecification(criteria);
        return userRoleOrganizationRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<UserRoleOrganization> createSpecification(UserRoleOrganizationCriteria criteria) {
        Specification<UserRoleOrganization> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserRoleOrganization_.id));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), UserRoleOrganization_.userName));
            }
            if (criteria.getRoleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoleName(), UserRoleOrganization_.roleName));
            }
            if (criteria.getOrgName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgName(), UserRoleOrganization_.orgName));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(UserRoleOrganization_.user, JoinType.LEFT).get(Userx_.id)));
            }
            if (criteria.getRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoleId(),
                    root -> root.join(UserRoleOrganization_.role, JoinType.LEFT).get(Role_.id)));
            }
            if (criteria.getOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrganizationId(),
                    root -> root.join(UserRoleOrganization_.organization, JoinType.LEFT).get(Organization_.id)));
            }
        }
        return specification;
    }
}
