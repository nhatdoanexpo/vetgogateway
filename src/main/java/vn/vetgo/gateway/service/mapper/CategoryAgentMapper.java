package vn.vetgo.gateway.service.mapper;

import org.mapstruct.*;
import vn.vetgo.gateway.domain.CategoryAgent;
import vn.vetgo.gateway.service.dto.CategoryAgentDTO;

/**
 * Mapper for the entity {@link CategoryAgent} and its DTO {@link CategoryAgentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryAgentMapper extends EntityMapper<CategoryAgentDTO, CategoryAgent> {}
