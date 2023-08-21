package vn.vetgo.gateway.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.vetgo.gateway.service.dto.VgOrderDTO;

/**
 * Service Interface for managing {@link vn.vetgo.gateway.domain.VgOrder}.
 */
public interface VgOrderService {
    /**
     * Save a vgOrder.
     *
     * @param vgOrderDTO the entity to save.
     * @return the persisted entity.
     */
    VgOrderDTO save(VgOrderDTO vgOrderDTO);

    /**
     * Updates a vgOrder.
     *
     * @param vgOrderDTO the entity to update.
     * @return the persisted entity.
     */
    VgOrderDTO update(VgOrderDTO vgOrderDTO);

    /**
     * Partially updates a vgOrder.
     *
     * @param vgOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VgOrderDTO> partialUpdate(VgOrderDTO vgOrderDTO);

    /**
     * Get all the vgOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VgOrderDTO> findAll(Pageable pageable);

    /**
     * Get the "id" vgOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VgOrderDTO> findOne(Long id);

    /**
     * Delete the "id" vgOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
