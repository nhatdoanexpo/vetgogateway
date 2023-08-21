package vn.vetgo.gateway.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.vetgo.gateway.domain.TransactionLog;

/**
 * Spring Data JPA repository for the TransactionLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {}
