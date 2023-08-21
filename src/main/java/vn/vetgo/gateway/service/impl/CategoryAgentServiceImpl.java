package vn.vetgo.gateway.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.vetgo.gateway.domain.CategoryAgent;
import vn.vetgo.gateway.repository.CategoryAgentRepository;
import vn.vetgo.gateway.service.CategoryAgentService;
import vn.vetgo.gateway.service.dto.CategoryAgentDTO;
import vn.vetgo.gateway.service.mapper.CategoryAgentMapper;

/**
 * Service Implementation for managing {@link CategoryAgent}.
 */
@Service
@Transactional
public class CategoryAgentServiceImpl implements CategoryAgentService {

    private final Logger log = LoggerFactory.getLogger(CategoryAgentServiceImpl.class);

    private final CategoryAgentRepository categoryAgentRepository;

    private final CategoryAgentMapper categoryAgentMapper;

    public CategoryAgentServiceImpl(CategoryAgentRepository categoryAgentRepository, CategoryAgentMapper categoryAgentMapper) {
        this.categoryAgentRepository = categoryAgentRepository;
        this.categoryAgentMapper = categoryAgentMapper;
    }

    @Override
    public CategoryAgentDTO save(CategoryAgentDTO categoryAgentDTO) {
        log.debug("Request to save CategoryAgent : {}", categoryAgentDTO);
        CategoryAgent categoryAgent = categoryAgentMapper.toEntity(categoryAgentDTO);
        categoryAgent = categoryAgentRepository.save(categoryAgent);
        return categoryAgentMapper.toDto(categoryAgent);
    }

    @Override
    public CategoryAgentDTO update(CategoryAgentDTO categoryAgentDTO) {
        log.debug("Request to update CategoryAgent : {}", categoryAgentDTO);
        CategoryAgent categoryAgent = categoryAgentMapper.toEntity(categoryAgentDTO);
        categoryAgent = categoryAgentRepository.save(categoryAgent);
        return categoryAgentMapper.toDto(categoryAgent);
    }

    @Override
    public Optional<CategoryAgentDTO> partialUpdate(CategoryAgentDTO categoryAgentDTO) {
        log.debug("Request to partially update CategoryAgent : {}", categoryAgentDTO);

        return categoryAgentRepository
            .findById(categoryAgentDTO.getId())
            .map(existingCategoryAgent -> {
                categoryAgentMapper.partialUpdate(existingCategoryAgent, categoryAgentDTO);

                return existingCategoryAgent;
            })
            .map(categoryAgentRepository::save)
            .map(categoryAgentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryAgentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryAgents");
        return categoryAgentRepository.findAll(pageable).map(categoryAgentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryAgentDTO> findOne(Long id) {
        log.debug("Request to get CategoryAgent : {}", id);
        return categoryAgentRepository.findById(id).map(categoryAgentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryAgent : {}", id);
        categoryAgentRepository.deleteById(id);
    }
}
