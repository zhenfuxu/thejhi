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

import com.mycompany.myapp.domain.Resource;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.ResourceRepository;
import com.mycompany.myapp.service.dto.ResourceCriteria;
import com.mycompany.myapp.service.dto.ResourceDTO;
import com.mycompany.myapp.service.mapper.ResourceMapper;

/**
 * Service for executing complex queries for {@link Resource} entities in the database.
 * The main input is a {@link ResourceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResourceDTO} or a {@link Page} of {@link ResourceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResourceQueryService extends QueryService<Resource> {

    private final Logger log = LoggerFactory.getLogger(ResourceQueryService.class);

    private final ResourceRepository resourceRepository;

    private final ResourceMapper resourceMapper;

    public ResourceQueryService(ResourceRepository resourceRepository, ResourceMapper resourceMapper) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
    }

    /**
     * Return a {@link List} of {@link ResourceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResourceDTO> findByCriteria(ResourceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Resource> specification = createSpecification(criteria);
        return resourceMapper.toDto(resourceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResourceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResourceDTO> findByCriteria(ResourceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Resource> specification = createSpecification(criteria);
        return resourceRepository.findAll(specification, page)
            .map(resourceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResourceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Resource> specification = createSpecification(criteria);
        return resourceRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Resource> createSpecification(ResourceCriteria criteria) {
        Specification<Resource> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Resource_.id));
            }
            if (criteria.getResRouterLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResRouterLink(), Resource_.resRouterLink));
            }
            if (criteria.getResDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResDescription(), Resource_.resDescription));
            }
            if (criteria.getResFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResFlag(), Resource_.resFlag));
            }
            if (criteria.getResOperate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResOperate(), Resource_.resOperate));
            }
            if (criteria.getResHref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResHref(), Resource_.resHref));
            }
            if (criteria.getResSrc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResSrc(), Resource_.resSrc));
            }
            if (criteria.getResText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResText(), Resource_.resText));
            }
            if (criteria.getResClass() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResClass(), Resource_.resClass));
            }
            if (criteria.getResEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResEffDate(), Resource_.resEffDate));
            }
            if (criteria.getResExpDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResExpDate(), Resource_.resExpDate));
            }
            if (criteria.getResLft() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResLft(), Resource_.resLft));
            }
            if (criteria.getResRgt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResRgt(), Resource_.resRgt));
            }
            if (criteria.getResLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResLevel(), Resource_.resLevel));
            }
            if (criteria.getResOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResOrder(), Resource_.resOrder));
            }
            if (criteria.getLeaf() != null) {
                specification = specification.and(buildSpecification(criteria.getLeaf(), Resource_.leaf));
            }
            if (criteria.getResDisabled() != null) {
                specification = specification.and(buildSpecification(criteria.getResDisabled(), Resource_.resDisabled));
            }
            if (criteria.getResChecked() != null) {
                specification = specification.and(buildSpecification(criteria.getResChecked(), Resource_.resChecked));
            }
            if (criteria.getResExpanded() != null) {
                specification = specification.and(buildSpecification(criteria.getResExpanded(), Resource_.resExpanded));
            }
            if (criteria.getResSelected() != null) {
                specification = specification.and(buildSpecification(criteria.getResSelected(), Resource_.resSelected));
            }
            if (criteria.getUpperId() != null) {
                specification = specification.and(buildSpecification(criteria.getUpperId(),
                    root -> root.join(Resource_.upper, JoinType.LEFT).get(Resource_.id)));
            }
        }
        return specification;
    }
}
