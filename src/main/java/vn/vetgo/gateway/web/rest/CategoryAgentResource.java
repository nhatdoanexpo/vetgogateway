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
import vn.vetgo.gateway.repository.CategoryAgentRepository;
import vn.vetgo.gateway.service.CategoryAgentService;
import vn.vetgo.gateway.service.dto.CategoryAgentDTO;
import vn.vetgo.gateway.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.vetgo.gateway.domain.CategoryAgent}.
 */
@RestController
@RequestMapping("/api")
public class CategoryAgentResource {

    private final Logger log = LoggerFactory.getLogger(CategoryAgentResource.class);

    private static final String ENTITY_NAME = "categoryAgent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryAgentService categoryAgentService;

    private final CategoryAgentRepository categoryAgentRepository;

    public CategoryAgentResource(CategoryAgentService categoryAgentService, CategoryAgentRepository categoryAgentRepository) {
        this.categoryAgentService = categoryAgentService;
        this.categoryAgentRepository = categoryAgentRepository;
    }

    /**
     * {@code POST  /category-agents} : Create a new categoryAgent.
     *
     * @param categoryAgentDTO the categoryAgentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryAgentDTO, or with status {@code 400 (Bad Request)} if the categoryAgent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-agents")
    public ResponseEntity<CategoryAgentDTO> createCategoryAgent(@RequestBody CategoryAgentDTO categoryAgentDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryAgent : {}", categoryAgentDTO);
        if (categoryAgentDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryAgent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryAgentDTO result = categoryAgentService.save(categoryAgentDTO);
        return ResponseEntity
            .created(new URI("/api/category-agents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-agents/:id} : Updates an existing categoryAgent.
     *
     * @param id the id of the categoryAgentDTO to save.
     * @param categoryAgentDTO the categoryAgentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryAgentDTO,
     * or with status {@code 400 (Bad Request)} if the categoryAgentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryAgentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-agents/{id}")
    public ResponseEntity<CategoryAgentDTO> updateCategoryAgent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoryAgentDTO categoryAgentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategoryAgent : {}, {}", id, categoryAgentDTO);
        if (categoryAgentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryAgentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryAgentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoryAgentDTO result = categoryAgentService.update(categoryAgentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryAgentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /category-agents/:id} : Partial updates given fields of an existing categoryAgent, field will ignore if it is null
     *
     * @param id the id of the categoryAgentDTO to save.
     * @param categoryAgentDTO the categoryAgentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryAgentDTO,
     * or with status {@code 400 (Bad Request)} if the categoryAgentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoryAgentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoryAgentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/category-agents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoryAgentDTO> partialUpdateCategoryAgent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoryAgentDTO categoryAgentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategoryAgent partially : {}, {}", id, categoryAgentDTO);
        if (categoryAgentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryAgentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryAgentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoryAgentDTO> result = categoryAgentService.partialUpdate(categoryAgentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryAgentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /category-agents} : get all the categoryAgents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryAgents in body.
     */
    @GetMapping("/category-agents")
    public ResponseEntity<List<CategoryAgentDTO>> getAllCategoryAgents(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CategoryAgents");
        Page<CategoryAgentDTO> page = categoryAgentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-agents/:id} : get the "id" categoryAgent.
     *
     * @param id the id of the categoryAgentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryAgentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-agents/{id}")
    public ResponseEntity<CategoryAgentDTO> getCategoryAgent(@PathVariable Long id) {
        log.debug("REST request to get CategoryAgent : {}", id);
        Optional<CategoryAgentDTO> categoryAgentDTO = categoryAgentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryAgentDTO);
    }

    /**
     * {@code DELETE  /category-agents/:id} : delete the "id" categoryAgent.
     *
     * @param id the id of the categoryAgentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-agents/{id}")
    public ResponseEntity<Void> deleteCategoryAgent(@PathVariable Long id) {
        log.debug("REST request to delete CategoryAgent : {}", id);
        categoryAgentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
