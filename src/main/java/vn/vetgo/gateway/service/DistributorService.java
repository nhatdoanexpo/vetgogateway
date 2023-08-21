package vn.vetgo.gateway.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.vetgo.gateway.service.dto.DistributorDTO;

/**
 * Service Interface for managing {@link vn.vetgo.gateway.domain.Distributor}.
 */
public interface DistributorService {
    /**
     * Save a distributor.
     *
     * @param distributorDTO the entity to save.
     * @return the persisted entity.
     */
    DistributorDTO save(DistributorDTO distributorDTO);

    /**
     * Updates a distributor.
     *
     * @param distributorDTO the entity to update.
     * @return the persisted entity.
     */
    DistributorDTO update(DistributorDTO distributorDTO);

    /**
     * Partially updates a distributor.
     *
     * @param distributorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DistributorDTO> partialUpdate(DistributorDTO distributorDTO);

    /**
     * Get all the distributors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DistributorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" distributor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistributorDTO> findOne(Long id);

    /**
     * Delete the "id" distributor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
