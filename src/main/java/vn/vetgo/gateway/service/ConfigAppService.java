package vn.vetgo.gateway.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.vetgo.gateway.service.dto.ConfigAppDTO;

/**
 * Service Interface for managing {@link vn.vetgo.gateway.domain.ConfigApp}.
 */
public interface ConfigAppService {
    /**
     * Save a configApp.
     *
     * @param configAppDTO the entity to save.
     * @return the persisted entity.
     */
    ConfigAppDTO save(ConfigAppDTO configAppDTO);

    /**
     * Updates a configApp.
     *
     * @param configAppDTO the entity to update.
     * @return the persisted entity.
     */
    ConfigAppDTO update(ConfigAppDTO configAppDTO);

    /**
     * Partially updates a configApp.
     *
     * @param configAppDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConfigAppDTO> partialUpdate(ConfigAppDTO configAppDTO);

    /**
     * Get all the configApps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConfigAppDTO> findAll(Pageable pageable);

    /**
     * Get the "id" configApp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfigAppDTO> findOne(Long id);

    /**
     * Delete the "id" configApp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
