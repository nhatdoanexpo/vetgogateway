package vn.vetgo.gateway.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.vetgo.gateway.domain.VgOrder;

/**
 * Spring Data JPA repository for the VgOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VgOrderRepository extends JpaRepository<VgOrder, Long> {}
