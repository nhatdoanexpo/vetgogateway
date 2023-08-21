package vn.vetgo.gateway.service.mapper;

import org.mapstruct.*;
import vn.vetgo.gateway.domain.Customer;
import vn.vetgo.gateway.service.dto.CustomerDTO;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {}
