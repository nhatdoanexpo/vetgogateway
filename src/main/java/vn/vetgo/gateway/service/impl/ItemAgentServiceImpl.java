package vn.vetgo.gateway.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.vetgo.gateway.domain.ItemAgent;
import vn.vetgo.gateway.repository.ItemAgentRepository;
import vn.vetgo.gateway.service.ItemAgentService;
import vn.vetgo.gateway.service.dto.ItemAgentDTO;
import vn.vetgo.gateway.service.mapper.ItemAgentMapper;

/**
 * Service Implementation for managing {@link ItemAgent}.
 */
@Service
@Transactional
public class ItemAgentServiceImpl implements ItemAgentService {

    private final Logger log = LoggerFactory.getLogger(ItemAgentServiceImpl.class);

    private final ItemAgentRepository itemAgentRepository;

    private final ItemAgentMapper itemAgentMapper;

    public ItemAgentServiceImpl(ItemAgentRepository itemAgentRepository, ItemAgentMapper itemAgentMapper) {
        this.itemAgentRepository = itemAgentRepository;
        this.itemAgentMapper = itemAgentMapper;
    }

    @Override
    public ItemAgentDTO save(ItemAgentDTO itemAgentDTO) {
        log.debug("Request to save ItemAgent : {}", itemAgentDTO);
        ItemAgent itemAgent = itemAgentMapper.toEntity(itemAgentDTO);
        itemAgent = itemAgentRepository.save(itemAgent);
        return itemAgentMapper.toDto(itemAgent);
    }

    @Override
    public ItemAgentDTO update(ItemAgentDTO itemAgentDTO) {
        log.debug("Request to update ItemAgent : {}", itemAgentDTO);
        ItemAgent itemAgent = itemAgentMapper.toEntity(itemAgentDTO);
        itemAgent = itemAgentRepository.save(itemAgent);
        return itemAgentMapper.toDto(itemAgent);
    }

    @Override
    public Optional<ItemAgentDTO> partialUpdate(ItemAgentDTO itemAgentDTO) {
        log.debug("Request to partially update ItemAgent : {}", itemAgentDTO);

        return itemAgentRepository
            .findById(itemAgentDTO.getId())
            .map(existingItemAgent -> {
                itemAgentMapper.partialUpdate(existingItemAgent, itemAgentDTO);

                return existingItemAgent;
            })
            .map(itemAgentRepository::save)
            .map(itemAgentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemAgentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemAgents");
        return itemAgentRepository.findAll(pageable).map(itemAgentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemAgentDTO> findOne(Long id) {
        log.debug("Request to get ItemAgent : {}", id);
        return itemAgentRepository.findById(id).map(itemAgentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemAgent : {}", id);
        itemAgentRepository.deleteById(id);
    }
}
