package vn.vetgo.gateway.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.vetgo.gateway.domain.ConfigApp;
import vn.vetgo.gateway.repository.ConfigAppRepository;
import vn.vetgo.gateway.service.ConfigAppService;
import vn.vetgo.gateway.service.dto.ConfigAppDTO;
import vn.vetgo.gateway.service.mapper.ConfigAppMapper;

/**
 * Service Implementation for managing {@link ConfigApp}.
 */
@Service
@Transactional
public class ConfigAppServiceImpl implements ConfigAppService {

    private final Logger log = LoggerFactory.getLogger(ConfigAppServiceImpl.class);

    private final ConfigAppRepository configAppRepository;

    private final ConfigAppMapper configAppMapper;

    public ConfigAppServiceImpl(ConfigAppRepository configAppRepository, ConfigAppMapper configAppMapper) {
        this.configAppRepository = configAppRepository;
        this.configAppMapper = configAppMapper;
    }

    @Override
    public ConfigAppDTO save(ConfigAppDTO configAppDTO) {
        log.debug("Request to save ConfigApp : {}", configAppDTO);
        ConfigApp configApp = configAppMapper.toEntity(configAppDTO);
        configApp = configAppRepository.save(configApp);
        return configAppMapper.toDto(configApp);
    }

    @Override
    public ConfigAppDTO update(ConfigAppDTO configAppDTO) {
        log.debug("Request to update ConfigApp : {}", configAppDTO);
        ConfigApp configApp = configAppMapper.toEntity(configAppDTO);
        configApp = configAppRepository.save(configApp);
        return configAppMapper.toDto(configApp);
    }

    @Override
    public Optional<ConfigAppDTO> partialUpdate(ConfigAppDTO configAppDTO) {
        log.debug("Request to partially update ConfigApp : {}", configAppDTO);

        return configAppRepository
            .findById(configAppDTO.getId())
            .map(existingConfigApp -> {
                configAppMapper.partialUpdate(existingConfigApp, configAppDTO);

                return existingConfigApp;
            })
            .map(configAppRepository::save)
            .map(configAppMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConfigAppDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConfigApps");
        return configAppRepository.findAll(pageable).map(configAppMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConfigAppDTO> findOne(Long id) {
        log.debug("Request to get ConfigApp : {}", id);
        return configAppRepository.findById(id).map(configAppMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfigApp : {}", id);
        configAppRepository.deleteById(id);
    }
}
