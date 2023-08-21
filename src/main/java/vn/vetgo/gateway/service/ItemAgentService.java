package vn.vetgo.gateway.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.vetgo.gateway.service.dto.ItemAgentDTO;

/**
 * Service Interface for managing {@link vn.vetgo.gateway.domain.ItemAgent}.
 */
public interface ItemAgentService {
    /**
     * Save a itemAgent.
     *
     * @param itemAgentDTO the entity to save.
     * @return the persisted entity.
     */
    ItemAgentDTO save(ItemAgentDTO itemAgentDTO);

    /**
     * Updates a itemAgent.
     *
     * @param itemAgentDTO the entity to update.
     * @return the persisted entity.
     */
    ItemAgentDTO update(ItemAgentDTO itemAgentDTO);

    /**
     * Partially updates a itemAgent.
     *
     * @param itemAgentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ItemAgentDTO> partialUpdate(ItemAgentDTO itemAgentDTO);

    /**
     * Get all the itemAgents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemAgentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" itemAgent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemAgentDTO> findOne(Long id);

    /**
     * Delete the "id" itemAgent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
