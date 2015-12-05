package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.UserTmp;
import com.mycompany.myapp.repository.UserTmpRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserTmp.
 */
@RestController
@RequestMapping("/api")
public class UserTmpResource {

    private final Logger log = LoggerFactory.getLogger(UserTmpResource.class);

    @Inject
    private UserTmpRepository userTmpRepository;

    /**
     * POST  /userTmps -> Create a new userTmp.
     */
    @RequestMapping(value = "/userTmps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserTmp> createUserTmp(@RequestBody UserTmp userTmp) throws URISyntaxException {
        log.debug("REST request to save UserTmp : {}", userTmp);
        if (userTmp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userTmp cannot already have an ID").body(null);
        }
        UserTmp result = userTmpRepository.save(userTmp);
        return ResponseEntity.created(new URI("/api/userTmps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userTmp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /userTmps -> Updates an existing userTmp.
     */
    @RequestMapping(value = "/userTmps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserTmp> updateUserTmp(@RequestBody UserTmp userTmp) throws URISyntaxException {
        log.debug("REST request to update UserTmp : {}", userTmp);
        if (userTmp.getId() == null) {
            return createUserTmp(userTmp);
        }
        UserTmp result = userTmpRepository.save(userTmp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userTmp", userTmp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /userTmps -> get all the userTmps.
     */
    @RequestMapping(value = "/userTmps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UserTmp> getAllUserTmps() {
        log.debug("REST request to get all UserTmps");
        return userTmpRepository.findAll();
    }

    /**
     * GET  /userTmps/:id -> get the "id" userTmp.
     */
    @RequestMapping(value = "/userTmps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserTmp> getUserTmp(@PathVariable Long id) {
        log.debug("REST request to get UserTmp : {}", id);
        return Optional.ofNullable(userTmpRepository.findOne(id))
            .map(userTmp -> new ResponseEntity<>(
                userTmp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /userTmps/:id -> delete the "id" userTmp.
     */
    @RequestMapping(value = "/userTmps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUserTmp(@PathVariable Long id) {
        log.debug("REST request to delete UserTmp : {}", id);
        userTmpRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userTmp", id.toString())).build();
    }
}
