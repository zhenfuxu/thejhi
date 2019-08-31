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

import com.mycompany.myapp.domain.Userx;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.UserxRepository;
import com.mycompany.myapp.service.dto.UserxCriteria;
import com.mycompany.myapp.service.dto.UserxDTO;
import com.mycompany.myapp.service.mapper.UserxMapper;

/**
 * Service for executing complex queries for {@link Userx} entities in the database.
 * The main input is a {@link UserxCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserxDTO} or a {@link Page} of {@link UserxDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserxQueryService extends QueryService<Userx> {

    private final Logger log = LoggerFactory.getLogger(UserxQueryService.class);

    private final UserxRepository userxRepository;

    private final UserxMapper userxMapper;

    public UserxQueryService(UserxRepository userxRepository, UserxMapper userxMapper) {
        this.userxRepository = userxRepository;
        this.userxMapper = userxMapper;
    }

    /**
     * Return a {@link List} of {@link UserxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserxDTO> findByCriteria(UserxCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Userx> specification = createSpecification(criteria);
        return userxMapper.toDto(userxRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserxDTO> findByCriteria(UserxCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Userx> specification = createSpecification(criteria);
        return userxRepository.findAll(specification, page)
            .map(userxMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserxCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Userx> specification = createSpecification(criteria);
        return userxRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Userx> createSpecification(UserxCriteria criteria) {
        Specification<Userx> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Userx_.id));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), Userx_.login));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), Userx_.password));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Userx_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Userx_.lastName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Userx_.email));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), Userx_.activated));
            }
            if (criteria.getLangKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLangKey(), Userx_.langKey));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), Userx_.imageUrl));
            }
            if (criteria.getActivationKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivationKey(), Userx_.activationKey));
            }
            if (criteria.getResetKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResetKey(), Userx_.resetKey));
            }
            if (criteria.getResetDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResetDate(), Userx_.resetDate));
            }
            if (criteria.getOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrganizationId(),
                    root -> root.join(Userx_.organization, JoinType.LEFT).get(Organization_.id)));
            }
            if (criteria.getRolesId() != null) {
                specification = specification.and(buildSpecification(criteria.getRolesId(),
                    root -> root.join(Userx_.roles, JoinType.LEFT).get(Role_.id)));
            }
        }
        return specification;
    }
}
