package vn.vetgo.gateway.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.vetgo.gateway.domain.TransactionLog;
import vn.vetgo.gateway.repository.TransactionLogRepository;
import vn.vetgo.gateway.service.TransactionLogService;
import vn.vetgo.gateway.service.dto.TransactionLogDTO;
import vn.vetgo.gateway.service.mapper.TransactionLogMapper;

/**
 * Service Implementation for managing {@link TransactionLog}.
 */
@Service
@Transactional
public class TransactionLogServiceImpl implements TransactionLogService {

    private final Logger log = LoggerFactory.getLogger(TransactionLogServiceImpl.class);

    private final TransactionLogRepository transactionLogRepository;

    private final TransactionLogMapper transactionLogMapper;

    public TransactionLogServiceImpl(TransactionLogRepository transactionLogRepository, TransactionLogMapper transactionLogMapper) {
        this.transactionLogRepository = transactionLogRepository;
        this.transactionLogMapper = transactionLogMapper;
    }

    @Override
    public TransactionLogDTO save(TransactionLogDTO transactionLogDTO) {
        log.debug("Request to save TransactionLog : {}", transactionLogDTO);
        TransactionLog transactionLog = transactionLogMapper.toEntity(transactionLogDTO);
        transactionLog = transactionLogRepository.save(transactionLog);
        return transactionLogMapper.toDto(transactionLog);
    }

    @Override
    public TransactionLogDTO update(TransactionLogDTO transactionLogDTO) {
        log.debug("Request to update TransactionLog : {}", transactionLogDTO);
        TransactionLog transactionLog = transactionLogMapper.toEntity(transactionLogDTO);
        transactionLog = transactionLogRepository.save(transactionLog);
        return transactionLogMapper.toDto(transactionLog);
    }

    @Override
    public Optional<TransactionLogDTO> partialUpdate(TransactionLogDTO transactionLogDTO) {
        log.debug("Request to partially update TransactionLog : {}", transactionLogDTO);

        return transactionLogRepository
            .findById(transactionLogDTO.getId())
            .map(existingTransactionLog -> {
                transactionLogMapper.partialUpdate(existingTransactionLog, transactionLogDTO);

                return existingTransactionLog;
            })
            .map(transactionLogRepository::save)
            .map(transactionLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionLogs");
        return transactionLogRepository.findAll(pageable).map(transactionLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionLogDTO> findOne(Long id) {
        log.debug("Request to get TransactionLog : {}", id);
        return transactionLogRepository.findById(id).map(transactionLogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionLog : {}", id);
        transactionLogRepository.deleteById(id);
    }
}
