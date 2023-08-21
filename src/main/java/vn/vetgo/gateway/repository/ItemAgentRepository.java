package vn.vetgo.gateway.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.vetgo.gateway.domain.ItemAgent;

/**
 * Spring Data JPA repository for the ItemAgent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemAgentRepository extends JpaRepository<ItemAgent, Long> {}
