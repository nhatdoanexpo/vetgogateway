package vn.vetgo.gateway.service.mapper;

import org.mapstruct.*;
import vn.vetgo.gateway.domain.ItemAgent;
import vn.vetgo.gateway.service.dto.ItemAgentDTO;

/**
 * Mapper for the entity {@link ItemAgent} and its DTO {@link ItemAgentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItemAgentMapper extends EntityMapper<ItemAgentDTO, ItemAgent> {}
