package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.UserxService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.UserxDTO;
import com.mycompany.myapp.service.dto.UserxCriteria;
import com.mycompany.myapp.service.UserxQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Userx}.
 */
@RestController
@RequestMapping("/api")
public class UserxResource {

    private final Logger log = LoggerFactory.getLogger(UserxResource.class);

    private static final String ENTITY_NAME = "userx";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserxService userxService;

    private final UserxQueryService userxQueryService;

    public UserxResource(UserxService userxService, UserxQueryService userxQueryService) {
        this.userxService = userxService;
        this.userxQueryService = userxQueryService;
    }

    /**
     * {@code POST  /userxes} : Create a new userx.
     *
     * @param userxDTO the userxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userxDTO, or with status {@code 400 (Bad Request)} if the userx has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/userxes")
    public ResponseEntity<UserxDTO> createUserx(@Valid @RequestBody UserxDTO userxDTO) throws URISyntaxException {
        log.debug("REST request to save Userx : {}", userxDTO);
        if (userxDTO.getId() != null) {
            throw new BadRequestAlertException("A new userx cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserxDTO result = userxService.save(userxDTO);
        return ResponseEntity.created(new URI("/api/userxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /userxes} : Updates an existing userx.
     *
     * @param userxDTO the userxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userxDTO,
     * or with status {@code 400 (Bad Request)} if the userxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/userxes")
    public ResponseEntity<UserxDTO> updateUserx(@Valid @RequestBody UserxDTO userxDTO) throws URISyntaxException {
        log.debug("REST request to update Userx : {}", userxDTO);
        if (userxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserxDTO result = userxService.save(userxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /userxes} : get all the userxes.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userxes in body.
     */
    @GetMapping("/userxes")
    public ResponseEntity<List<UserxDTO>> getAllUserxes(UserxCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Userxes by criteria: {}", criteria);
        Page<UserxDTO> page = userxQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /userxes/count} : count all the userxes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/userxes/count")
    public ResponseEntity<Long> countUserxes(UserxCriteria criteria) {
        log.debug("REST request to count Userxes by criteria: {}", criteria);
        return ResponseEntity.ok().body(userxQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /userxes/:id} : get the "id" userx.
     *
     * @param id the id of the userxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/userxes/{id}")
    public ResponseEntity<UserxDTO> getUserx(@PathVariable Long id) {
        log.debug("REST request to get Userx : {}", id);
        Optional<UserxDTO> userxDTO = userxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userxDTO);
    }

    /**
     * {@code DELETE  /userxes/:id} : delete the "id" userx.
     *
     * @param id the id of the userxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/userxes/{id}")
    public ResponseEntity<Void> deleteUserx(@PathVariable Long id) {
        log.debug("REST request to delete Userx : {}", id);
        userxService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
