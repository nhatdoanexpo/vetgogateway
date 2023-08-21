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
import vn.vetgo.gateway.repository.ConfigAppRepository;
import vn.vetgo.gateway.service.ConfigAppService;
import vn.vetgo.gateway.service.dto.ConfigAppDTO;
import vn.vetgo.gateway.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.vetgo.gateway.domain.ConfigApp}.
 */
@RestController
@RequestMapping("/api")
public class ConfigAppResource {

    private final Logger log = LoggerFactory.getLogger(ConfigAppResource.class);

    private static final String ENTITY_NAME = "configApp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigAppService configAppService;

    private final ConfigAppRepository configAppRepository;

    public ConfigAppResource(ConfigAppService configAppService, ConfigAppRepository configAppRepository) {
        this.configAppService = configAppService;
        this.configAppRepository = configAppRepository;
    }

    /**
     * {@code POST  /config-apps} : Create a new configApp.
     *
     * @param configAppDTO the configAppDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configAppDTO, or with status {@code 400 (Bad Request)} if the configApp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/config-apps")
    public ResponseEntity<ConfigAppDTO> createConfigApp(@RequestBody ConfigAppDTO configAppDTO) throws URISyntaxException {
        log.debug("REST request to save ConfigApp : {}", configAppDTO);
        if (configAppDTO.getId() != null) {
            throw new BadRequestAlertException("A new configApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigAppDTO result = configAppService.save(configAppDTO);
        return ResponseEntity
            .created(new URI("/api/config-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /config-apps/:id} : Updates an existing configApp.
     *
     * @param id the id of the configAppDTO to save.
     * @param configAppDTO the configAppDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configAppDTO,
     * or with status {@code 400 (Bad Request)} if the configAppDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configAppDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/config-apps/{id}")
    public ResponseEntity<ConfigAppDTO> updateConfigApp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfigAppDTO configAppDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ConfigApp : {}, {}", id, configAppDTO);
        if (configAppDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configAppDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configAppRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConfigAppDTO result = configAppService.update(configAppDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configAppDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /config-apps/:id} : Partial updates given fields of an existing configApp, field will ignore if it is null
     *
     * @param id the id of the configAppDTO to save.
     * @param configAppDTO the configAppDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configAppDTO,
     * or with status {@code 400 (Bad Request)} if the configAppDTO is not valid,
     * or with status {@code 404 (Not Found)} if the configAppDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the configAppDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/config-apps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConfigAppDTO> partialUpdateConfigApp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfigAppDTO configAppDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConfigApp partially : {}, {}", id, configAppDTO);
        if (configAppDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configAppDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configAppRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConfigAppDTO> result = configAppService.partialUpdate(configAppDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configAppDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /config-apps} : get all the configApps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configApps in body.
     */
    @GetMapping("/config-apps")
    public ResponseEntity<List<ConfigAppDTO>> getAllConfigApps(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ConfigApps");
        Page<ConfigAppDTO> page = configAppService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /config-apps/:id} : get the "id" configApp.
     *
     * @param id the id of the configAppDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configAppDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/config-apps/{id}")
    public ResponseEntity<ConfigAppDTO> getConfigApp(@PathVariable Long id) {
        log.debug("REST request to get ConfigApp : {}", id);
        Optional<ConfigAppDTO> configAppDTO = configAppService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configAppDTO);
    }

    /**
     * {@code DELETE  /config-apps/:id} : delete the "id" configApp.
     *
     * @param id the id of the configAppDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/config-apps/{id}")
    public ResponseEntity<Void> deleteConfigApp(@PathVariable Long id) {
        log.debug("REST request to delete ConfigApp : {}", id);
        configAppService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
