package vn.vetgo.gateway.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.vetgo.gateway.service.dto.TransactionLogDTO;

/**
 * Service Interface for managing {@link vn.vetgo.gateway.domain.TransactionLog}.
 */
public interface TransactionLogService {
    /**
     * Save a transactionLog.
     *
     * @param transactionLogDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionLogDTO save(TransactionLogDTO transactionLogDTO);

    /**
     * Updates a transactionLog.
     *
     * @param transactionLogDTO the entity to update.
     * @return the persisted entity.
     */
    TransactionLogDTO update(TransactionLogDTO transactionLogDTO);

    /**
     * Partially updates a transactionLog.
     *
     * @param transactionLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TransactionLogDTO> partialUpdate(TransactionLogDTO transactionLogDTO);

    /**
     * Get all the transactionLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionLogDTO> findAll(Pageable pageable);

    /**
     * Get the "id" transactionLog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionLogDTO> findOne(Long id);

    /**
     * Delete the "id" transactionLog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
