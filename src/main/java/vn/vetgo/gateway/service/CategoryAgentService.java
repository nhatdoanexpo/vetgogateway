package vn.vetgo.gateway.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.vetgo.gateway.service.dto.CategoryAgentDTO;

/**
 * Service Interface for managing {@link vn.vetgo.gateway.domain.CategoryAgent}.
 */
public interface CategoryAgentService {
    /**
     * Save a categoryAgent.
     *
     * @param categoryAgentDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryAgentDTO save(CategoryAgentDTO categoryAgentDTO);

    /**
     * Updates a categoryAgent.
     *
     * @param categoryAgentDTO the entity to update.
     * @return the persisted entity.
     */
    CategoryAgentDTO update(CategoryAgentDTO categoryAgentDTO);

    /**
     * Partially updates a categoryAgent.
     *
     * @param categoryAgentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoryAgentDTO> partialUpdate(CategoryAgentDTO categoryAgentDTO);

    /**
     * Get all the categoryAgents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryAgentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categoryAgent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryAgentDTO> findOne(Long id);

    /**
     * Delete the "id" categoryAgent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
