package vn.vetgo.gateway.service.mapper;

import org.mapstruct.*;
import vn.vetgo.gateway.domain.Item;
import vn.vetgo.gateway.service.dto.ItemDTO;

/**
 * Mapper for the entity {@link Item} and its DTO {@link ItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {}
