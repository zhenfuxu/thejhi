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

import com.mycompany.myapp.domain.Organization;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.OrganizationRepository;
import com.mycompany.myapp.service.dto.OrganizationCriteria;
import com.mycompany.myapp.service.dto.OrganizationDTO;
import com.mycompany.myapp.service.mapper.OrganizationMapper;

/**
 * Service for executing complex queries for {@link Organization} entities in the database.
 * The main input is a {@link OrganizationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrganizationDTO} or a {@link Page} of {@link OrganizationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrganizationQueryService extends QueryService<Organization> {

    private final Logger log = LoggerFactory.getLogger(OrganizationQueryService.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    public OrganizationQueryService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
    }

    /**
     * Return a {@link List} of {@link OrganizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrganizationDTO> findByCriteria(OrganizationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Organization> specification = createSpecification(criteria);
        return organizationMapper.toDto(organizationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrganizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganizationDTO> findByCriteria(OrganizationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Organization> specification = createSpecification(criteria);
        return organizationRepository.findAll(specification, page)
            .map(organizationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrganizationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Organization> specification = createSpecification(criteria);
        return organizationRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Organization> createSpecification(OrganizationCriteria criteria) {
        Specification<Organization> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Organization_.id));
            }
            if (criteria.getOrgName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgName(), Organization_.orgName));
            }
            if (criteria.getOrgCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgCode(), Organization_.orgCode));
            }
            if (criteria.getOrgFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgFlag(), Organization_.orgFlag));
            }
            if (criteria.getOrgAreaCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgAreaCode(), Organization_.orgAreaCode));
            }
            if (criteria.getOrgAreaName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgAreaName(), Organization_.orgAreaName));
            }
            if (criteria.getOrgDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgDescription(), Organization_.orgDescription));
            }
            if (criteria.getOrgLft() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrgLft(), Organization_.orgLft));
            }
            if (criteria.getOrgRgt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrgRgt(), Organization_.orgRgt));
            }
            if (criteria.getOrgLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrgLevel(), Organization_.orgLevel));
            }
            if (criteria.getOrgOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrgOrder(), Organization_.orgOrder));
            }
            if (criteria.getLeaf() != null) {
                specification = specification.and(buildSpecification(criteria.getLeaf(), Organization_.leaf));
            }
            if (criteria.getUpperId() != null) {
                specification = specification.and(buildSpecification(criteria.getUpperId(),
                    root -> root.join(Organization_.upper, JoinType.LEFT).get(Organization_.id)));
            }
        }
        return specification;
    }
}
