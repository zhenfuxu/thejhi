package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.UserxService;
import com.mycompany.myapp.domain.Userx;
import com.mycompany.myapp.repository.UserxRepository;
import com.mycompany.myapp.service.dto.UserxDTO;
import com.mycompany.myapp.service.mapper.UserxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Userx}.
 */
@Service
@Transactional
public class UserxServiceImpl implements UserxService {

    private final Logger log = LoggerFactory.getLogger(UserxServiceImpl.class);

    private final UserxRepository userxRepository;

    private final UserxMapper userxMapper;

    public UserxServiceImpl(UserxRepository userxRepository, UserxMapper userxMapper) {
        this.userxRepository = userxRepository;
        this.userxMapper = userxMapper;
    }

    /**
     * Save a userx.
     *
     * @param userxDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserxDTO save(UserxDTO userxDTO) {
        log.debug("Request to save Userx : {}", userxDTO);
        Userx userx = userxMapper.toEntity(userxDTO);
        userx = userxRepository.save(userx);
        return userxMapper.toDto(userx);
    }

    /**
     * Get all the userxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Userxes");
        return userxRepository.findAll(pageable)
            .map(userxMapper::toDto);
    }

    /**
     * Get all the userxes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UserxDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userxRepository.findAllWithEagerRelationships(pageable).map(userxMapper::toDto);
    }
    

    /**
     * Get one userx by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserxDTO> findOne(Long id) {
        log.debug("Request to get Userx : {}", id);
        return userxRepository.findOneWithEagerRelationships(id)
            .map(userxMapper::toDto);
    }

    /**
     * Delete the userx by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Userx : {}", id);
        userxRepository.deleteById(id);
    }
}
