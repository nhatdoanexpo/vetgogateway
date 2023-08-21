package vn.vetgo.gateway.service.mapper;

import org.mapstruct.*;
import vn.vetgo.gateway.domain.Category;
import vn.vetgo.gateway.service.dto.CategoryDTO;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {}
