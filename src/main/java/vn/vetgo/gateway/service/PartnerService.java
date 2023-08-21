package vn.vetgo.gateway.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.vetgo.gateway.service.dto.PartnerDTO;

/**
 * Service Interface for managing {@link vn.vetgo.gateway.domain.Partner}.
 */
public interface PartnerService {
    /**
     * Save a partner.
     *
     * @param partnerDTO the entity to save.
     * @return the persisted entity.
     */
    PartnerDTO save(PartnerDTO partnerDTO);

    /**
     * Updates a partner.
     *
     * @param partnerDTO the entity to update.
     * @return the persisted entity.
     */
    PartnerDTO update(PartnerDTO partnerDTO);

    /**
     * Partially updates a partner.
     *
     * @param partnerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PartnerDTO> partialUpdate(PartnerDTO partnerDTO);

    /**
     * Get all the partners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PartnerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" partner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartnerDTO> findOne(Long id);

    /**
     * Delete the "id" partner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
