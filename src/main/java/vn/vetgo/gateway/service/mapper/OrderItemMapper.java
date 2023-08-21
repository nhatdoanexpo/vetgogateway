package vn.vetgo.gateway.service.mapper;

import org.mapstruct.*;
import vn.vetgo.gateway.domain.OrderItem;
import vn.vetgo.gateway.service.dto.OrderItemDTO;

/**
 * Mapper for the entity {@link OrderItem} and its DTO {@link OrderItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {}
