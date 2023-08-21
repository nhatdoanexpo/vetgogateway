package vn.vetgo.gateway.service.mapper;

import org.mapstruct.*;
import vn.vetgo.gateway.domain.Distributor;
import vn.vetgo.gateway.service.dto.DistributorDTO;

/**
 * Mapper for the entity {@link Distributor} and its DTO {@link DistributorDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistributorMapper extends EntityMapper<DistributorDTO, Distributor> {}
