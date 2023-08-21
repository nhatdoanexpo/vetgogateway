package vn.vetgo.gateway.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.vetgo.gateway.domain.Distributor;
import vn.vetgo.gateway.repository.DistributorRepository;
import vn.vetgo.gateway.service.DistributorService;
import vn.vetgo.gateway.service.dto.DistributorDTO;
import vn.vetgo.gateway.service.mapper.DistributorMapper;

/**
 * Service Implementation for managing {@link Distributor}.
 */
@Service
@Transactional
public class DistributorServiceImpl implements DistributorService {

    private final Logger log = LoggerFactory.getLogger(DistributorServiceImpl.class);

    private final DistributorRepository distributorRepository;

    private final DistributorMapper distributorMapper;

    public DistributorServiceImpl(DistributorRepository distributorRepository, DistributorMapper distributorMapper) {
        this.distributorRepository = distributorRepository;
        this.distributorMapper = distributorMapper;
    }

    @Override
    public DistributorDTO save(DistributorDTO distributorDTO) {
        log.debug("Request to save Distributor : {}", distributorDTO);
        Distributor distributor = distributorMapper.toEntity(distributorDTO);
        distributor = distributorRepository.save(distributor);
        return distributorMapper.toDto(distributor);
    }

    @Override
    public DistributorDTO update(DistributorDTO distributorDTO) {
        log.debug("Request to update Distributor : {}", distributorDTO);
        Distributor distributor = distributorMapper.toEntity(distributorDTO);
        distributor = distributorRepository.save(distributor);
        return distributorMapper.toDto(distributor);
    }

    @Override
    public Optional<DistributorDTO> partialUpdate(DistributorDTO distributorDTO) {
        log.debug("Request to partially update Distributor : {}", distributorDTO);

        return distributorRepository
            .findById(distributorDTO.getId())
            .map(existingDistributor -> {
                distributorMapper.partialUpdate(existingDistributor, distributorDTO);

                return existingDistributor;
            })
            .map(distributorRepository::save)
            .map(distributorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DistributorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Distributors");
        return distributorRepository.findAll(pageable).map(distributorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DistributorDTO> findOne(Long id) {
        log.debug("Request to get Distributor : {}", id);
        return distributorRepository.findById(id).map(distributorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Distributor : {}", id);
        distributorRepository.deleteById(id);
    }
}
