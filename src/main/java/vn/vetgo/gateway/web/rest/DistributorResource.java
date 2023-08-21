package vn.vetgo.gateway.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import vn.vetgo.gateway.repository.DistributorRepository;
import vn.vetgo.gateway.service.DistributorService;
import vn.vetgo.gateway.service.dto.DistributorDTO;
import vn.vetgo.gateway.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.vetgo.gateway.domain.Distributor}.
 */
@RestController
@RequestMapping("/api")
public class DistributorResource {

    private final Logger log = LoggerFactory.getLogger(DistributorResource.class);

    private static final String ENTITY_NAME = "distributor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistributorService distributorService;

    private final DistributorRepository distributorRepository;

    public DistributorResource(DistributorService distributorService, DistributorRepository distributorRepository) {
        this.distributorService = distributorService;
        this.distributorRepository = distributorRepository;
    }

    /**
     * {@code POST  /distributors} : Create a new distributor.
     *
     * @param distributorDTO the distributorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new distributorDTO, or with status {@code 400 (Bad Request)} if the distributor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/distributors")
    public ResponseEntity<DistributorDTO> createDistributor(@RequestBody DistributorDTO distributorDTO) throws URISyntaxException {
        log.debug("REST request to save Distributor : {}", distributorDTO);
        if (distributorDTO.getId() != null) {
            throw new BadRequestAlertException("A new distributor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistributorDTO result = distributorService.save(distributorDTO);
        return ResponseEntity
            .created(new URI("/api/distributors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /distributors/:id} : Updates an existing distributor.
     *
     * @param id the id of the distributorDTO to save.
     * @param distributorDTO the distributorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated distributorDTO,
     * or with status {@code 400 (Bad Request)} if the distributorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the distributorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/distributors/{id}")
    public ResponseEntity<DistributorDTO> updateDistributor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DistributorDTO distributorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Distributor : {}, {}", id, distributorDTO);
        if (distributorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, distributorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!distributorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DistributorDTO result = distributorService.update(distributorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, distributorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /distributors/:id} : Partial updates given fields of an existing distributor, field will ignore if it is null
     *
     * @param id the id of the distributorDTO to save.
     * @param distributorDTO the distributorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated distributorDTO,
     * or with status {@code 400 (Bad Request)} if the distributorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the distributorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the distributorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/distributors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DistributorDTO> partialUpdateDistributor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DistributorDTO distributorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Distributor partially : {}, {}", id, distributorDTO);
        if (distributorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, distributorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!distributorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DistributorDTO> result = distributorService.partialUpdate(distributorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, distributorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /distributors} : get all the distributors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of distributors in body.
     */
    @GetMapping("/distributors")
    public ResponseEntity<List<DistributorDTO>> getAllDistributors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Distributors");
        Page<DistributorDTO> page = distributorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /distributors/:id} : get the "id" distributor.
     *
     * @param id the id of the distributorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the distributorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/distributors/{id}")
    public ResponseEntity<DistributorDTO> getDistributor(@PathVariable Long id) {
        log.debug("REST request to get Distributor : {}", id);
        Optional<DistributorDTO> distributorDTO = distributorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(distributorDTO);
    }

    /**
     * {@code DELETE  /distributors/:id} : delete the "id" distributor.
     *
     * @param id the id of the distributorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/distributors/{id}")
    public ResponseEntity<Void> deleteDistributor(@PathVariable Long id) {
        log.debug("REST request to delete Distributor : {}", id);
        distributorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
