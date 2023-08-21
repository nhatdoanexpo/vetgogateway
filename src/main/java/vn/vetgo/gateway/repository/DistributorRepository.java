package vn.vetgo.gateway.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.vetgo.gateway.domain.Distributor;

/**
 * Spring Data JPA repository for the Distributor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistributorRepository extends JpaRepository<Distributor, Long> {}
