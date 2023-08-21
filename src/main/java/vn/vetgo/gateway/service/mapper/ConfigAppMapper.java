package vn.vetgo.gateway.service.mapper;

import org.mapstruct.*;
import vn.vetgo.gateway.domain.ConfigApp;
import vn.vetgo.gateway.service.dto.ConfigAppDTO;

/**
 * Mapper for the entity {@link ConfigApp} and its DTO {@link ConfigAppDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConfigAppMapper extends EntityMapper<ConfigAppDTO, ConfigApp> {}
