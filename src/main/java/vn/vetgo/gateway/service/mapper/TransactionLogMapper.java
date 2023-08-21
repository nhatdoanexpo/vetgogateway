package vn.vetgo.gateway.service.mapper;

import org.mapstruct.*;
import vn.vetgo.gateway.domain.TransactionLog;
import vn.vetgo.gateway.service.dto.TransactionLogDTO;

/**
 * Mapper for the entity {@link TransactionLog} and its DTO {@link TransactionLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionLogMapper extends EntityMapper<TransactionLogDTO, TransactionLog> {}
