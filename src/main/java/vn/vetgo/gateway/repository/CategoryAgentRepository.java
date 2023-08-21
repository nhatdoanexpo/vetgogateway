package vn.vetgo.gateway.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.vetgo.gateway.domain.CategoryAgent;

/**
 * Spring Data JPA repository for the CategoryAgent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryAgentRepository extends JpaRepository<CategoryAgent, Long> {}
