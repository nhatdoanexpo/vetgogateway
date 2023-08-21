package vn.vetgo.gateway.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.vetgo.gateway.domain.ConfigApp;

/**
 * Spring Data JPA repository for the ConfigApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigAppRepository extends JpaRepository<ConfigApp, Long> {}
