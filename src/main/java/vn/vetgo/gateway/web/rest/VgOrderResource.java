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
import vn.vetgo.gateway.repository.VgOrderRepository;
import vn.vetgo.gateway.service.VgOrderService;
import vn.vetgo.gateway.service.dto.VgOrderDTO;
import vn.vetgo.gateway.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.vetgo.gateway.domain.VgOrder}.
 */
@RestController
@RequestMapping("/api")
public class VgOrderResource {

    private final Logger log = LoggerFactory.getLogger(VgOrderResource.class);

    private static final String ENTITY_NAME = "vgOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VgOrderService vgOrderService;

    private final VgOrderRepository vgOrderRepository;

    public VgOrderResource(VgOrderService vgOrderService, VgOrderRepository vgOrderRepository) {
        this.vgOrderService = vgOrderService;
        this.vgOrderRepository = vgOrderRepository;
    }

    /**
     * {@code POST  /vg-orders} : Create a new vgOrder.
     *
     * @param vgOrderDTO the vgOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vgOrderDTO, or with status {@code 400 (Bad Request)} if the vgOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vg-orders")
    public ResponseEntity<VgOrderDTO> createVgOrder(@RequestBody VgOrderDTO vgOrderDTO) throws URISyntaxException {
        log.debug("REST request to save VgOrder : {}", vgOrderDTO);
        if (vgOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new vgOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VgOrderDTO result = vgOrderService.save(vgOrderDTO);
        return ResponseEntity
            .created(new URI("/api/vg-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vg-orders/:id} : Updates an existing vgOrder.
     *
     * @param id the id of the vgOrderDTO to save.
     * @param vgOrderDTO the vgOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vgOrderDTO,
     * or with status {@code 400 (Bad Request)} if the vgOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vgOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vg-orders/{id}")
    public ResponseEntity<VgOrderDTO> updateVgOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VgOrderDTO vgOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VgOrder : {}, {}", id, vgOrderDTO);
        if (vgOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vgOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vgOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VgOrderDTO result = vgOrderService.update(vgOrderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vgOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vg-orders/:id} : Partial updates given fields of an existing vgOrder, field will ignore if it is null
     *
     * @param id the id of the vgOrderDTO to save.
     * @param vgOrderDTO the vgOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vgOrderDTO,
     * or with status {@code 400 (Bad Request)} if the vgOrderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vgOrderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vgOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vg-orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VgOrderDTO> partialUpdateVgOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VgOrderDTO vgOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VgOrder partially : {}, {}", id, vgOrderDTO);
        if (vgOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vgOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vgOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VgOrderDTO> result = vgOrderService.partialUpdate(vgOrderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vgOrderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vg-orders} : get all the vgOrders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vgOrders in body.
     */
    @GetMapping("/vg-orders")
    public ResponseEntity<List<VgOrderDTO>> getAllVgOrders(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of VgOrders");
        Page<VgOrderDTO> page = vgOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vg-orders/:id} : get the "id" vgOrder.
     *
     * @param id the id of the vgOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vgOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vg-orders/{id}")
    public ResponseEntity<VgOrderDTO> getVgOrder(@PathVariable Long id) {
        log.debug("REST request to get VgOrder : {}", id);
        Optional<VgOrderDTO> vgOrderDTO = vgOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vgOrderDTO);
    }

    /**
     * {@code DELETE  /vg-orders/:id} : delete the "id" vgOrder.
     *
     * @param id the id of the vgOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vg-orders/{id}")
    public ResponseEntity<Void> deleteVgOrder(@PathVariable Long id) {
        log.debug("REST request to delete VgOrder : {}", id);
        vgOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
