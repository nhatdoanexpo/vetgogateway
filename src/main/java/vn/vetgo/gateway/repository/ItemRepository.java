package vn.vetgo.gateway.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.vetgo.gateway.domain.Item;

/**
 * Spring Data JPA repository for the Item entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {}
