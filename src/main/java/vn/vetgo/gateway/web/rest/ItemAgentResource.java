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
import vn.vetgo.gateway.repository.ItemAgentRepository;
import vn.vetgo.gateway.service.ItemAgentService;
import vn.vetgo.gateway.service.dto.ItemAgentDTO;
import vn.vetgo.gateway.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.vetgo.gateway.domain.ItemAgent}.
 */
@RestController
@RequestMapping("/api")
public class ItemAgentResource {

    private final Logger log = LoggerFactory.getLogger(ItemAgentResource.class);

    private static final String ENTITY_NAME = "itemAgent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemAgentService itemAgentService;

    private final ItemAgentRepository itemAgentRepository;

    public ItemAgentResource(ItemAgentService itemAgentService, ItemAgentRepository itemAgentRepository) {
        this.itemAgentService = itemAgentService;
        this.itemAgentRepository = itemAgentRepository;
    }

    /**
     * {@code POST  /item-agents} : Create a new itemAgent.
     *
     * @param itemAgentDTO the itemAgentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemAgentDTO, or with status {@code 400 (Bad Request)} if the itemAgent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-agents")
    public ResponseEntity<ItemAgentDTO> createItemAgent(@RequestBody ItemAgentDTO itemAgentDTO) throws URISyntaxException {
        log.debug("REST request to save ItemAgent : {}", itemAgentDTO);
        if (itemAgentDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemAgent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemAgentDTO result = itemAgentService.save(itemAgentDTO);
        return ResponseEntity
            .created(new URI("/api/item-agents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-agents/:id} : Updates an existing itemAgent.
     *
     * @param id the id of the itemAgentDTO to save.
     * @param itemAgentDTO the itemAgentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemAgentDTO,
     * or with status {@code 400 (Bad Request)} if the itemAgentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemAgentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-agents/{id}")
    public ResponseEntity<ItemAgentDTO> updateItemAgent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemAgentDTO itemAgentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ItemAgent : {}, {}", id, itemAgentDTO);
        if (itemAgentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemAgentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemAgentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemAgentDTO result = itemAgentService.update(itemAgentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemAgentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /item-agents/:id} : Partial updates given fields of an existing itemAgent, field will ignore if it is null
     *
     * @param id the id of the itemAgentDTO to save.
     * @param itemAgentDTO the itemAgentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemAgentDTO,
     * or with status {@code 400 (Bad Request)} if the itemAgentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the itemAgentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemAgentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/item-agents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ItemAgentDTO> partialUpdateItemAgent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemAgentDTO itemAgentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItemAgent partially : {}, {}", id, itemAgentDTO);
        if (itemAgentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemAgentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemAgentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemAgentDTO> result = itemAgentService.partialUpdate(itemAgentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemAgentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /item-agents} : get all the itemAgents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemAgents in body.
     */
    @GetMapping("/item-agents")
    public ResponseEntity<List<ItemAgentDTO>> getAllItemAgents(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ItemAgents");
        Page<ItemAgentDTO> page = itemAgentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-agents/:id} : get the "id" itemAgent.
     *
     * @param id the id of the itemAgentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemAgentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-agents/{id}")
    public ResponseEntity<ItemAgentDTO> getItemAgent(@PathVariable Long id) {
        log.debug("REST request to get ItemAgent : {}", id);
        Optional<ItemAgentDTO> itemAgentDTO = itemAgentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemAgentDTO);
    }

    /**
     * {@code DELETE  /item-agents/:id} : delete the "id" itemAgent.
     *
     * @param id the id of the itemAgentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-agents/{id}")
    public ResponseEntity<Void> deleteItemAgent(@PathVariable Long id) {
        log.debug("REST request to delete ItemAgent : {}", id);
        itemAgentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
