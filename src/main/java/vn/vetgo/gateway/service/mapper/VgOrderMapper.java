package vn.vetgo.gateway.service.mapper;

import org.mapstruct.*;
import vn.vetgo.gateway.domain.VgOrder;
import vn.vetgo.gateway.service.dto.VgOrderDTO;

/**
 * Mapper for the entity {@link VgOrder} and its DTO {@link VgOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface VgOrderMapper extends EntityMapper<VgOrderDTO, VgOrder> {}
