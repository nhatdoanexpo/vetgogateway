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
import vn.vetgo.gateway.repository.TransactionLogRepository;
import vn.vetgo.gateway.service.TransactionLogService;
import vn.vetgo.gateway.service.dto.TransactionLogDTO;
import vn.vetgo.gateway.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.vetgo.gateway.domain.TransactionLog}.
 */
@RestController
@RequestMapping("/api")
public class TransactionLogResource {

    private final Logger log = LoggerFactory.getLogger(TransactionLogResource.class);

    private static final String ENTITY_NAME = "transactionLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionLogService transactionLogService;

    private final TransactionLogRepository transactionLogRepository;

    public TransactionLogResource(TransactionLogService transactionLogService, TransactionLogRepository transactionLogRepository) {
        this.transactionLogService = transactionLogService;
        this.transactionLogRepository = transactionLogRepository;
    }

    /**
     * {@code POST  /transaction-logs} : Create a new transactionLog.
     *
     * @param transactionLogDTO the transactionLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionLogDTO, or with status {@code 400 (Bad Request)} if the transactionLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-logs")
    public ResponseEntity<TransactionLogDTO> createTransactionLog(@RequestBody TransactionLogDTO transactionLogDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransactionLog : {}", transactionLogDTO);
        if (transactionLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionLogDTO result = transactionLogService.save(transactionLogDTO);
        return ResponseEntity
            .created(new URI("/api/transaction-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-logs/:id} : Updates an existing transactionLog.
     *
     * @param id the id of the transactionLogDTO to save.
     * @param transactionLogDTO the transactionLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionLogDTO,
     * or with status {@code 400 (Bad Request)} if the transactionLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-logs/{id}")
    public ResponseEntity<TransactionLogDTO> updateTransactionLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransactionLogDTO transactionLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransactionLog : {}, {}", id, transactionLogDTO);
        if (transactionLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransactionLogDTO result = transactionLogService.update(transactionLogDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transaction-logs/:id} : Partial updates given fields of an existing transactionLog, field will ignore if it is null
     *
     * @param id the id of the transactionLogDTO to save.
     * @param transactionLogDTO the transactionLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionLogDTO,
     * or with status {@code 400 (Bad Request)} if the transactionLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transactionLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transactionLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transaction-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransactionLogDTO> partialUpdateTransactionLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransactionLogDTO transactionLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransactionLog partially : {}, {}", id, transactionLogDTO);
        if (transactionLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransactionLogDTO> result = transactionLogService.partialUpdate(transactionLogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionLogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transaction-logs} : get all the transactionLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionLogs in body.
     */
    @GetMapping("/transaction-logs")
    public ResponseEntity<List<TransactionLogDTO>> getAllTransactionLogs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TransactionLogs");
        Page<TransactionLogDTO> page = transactionLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transaction-logs/:id} : get the "id" transactionLog.
     *
     * @param id the id of the transactionLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-logs/{id}")
    public ResponseEntity<TransactionLogDTO> getTransactionLog(@PathVariable Long id) {
        log.debug("REST request to get TransactionLog : {}", id);
        Optional<TransactionLogDTO> transactionLogDTO = transactionLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionLogDTO);
    }

    /**
     * {@code DELETE  /transaction-logs/:id} : delete the "id" transactionLog.
     *
     * @param id the id of the transactionLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-logs/{id}")
    public ResponseEntity<Void> deleteTransactionLog(@PathVariable Long id) {
        log.debug("REST request to delete TransactionLog : {}", id);
        transactionLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
