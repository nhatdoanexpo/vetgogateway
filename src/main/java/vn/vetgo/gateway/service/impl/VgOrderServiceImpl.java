package vn.vetgo.gateway.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.vetgo.gateway.domain.VgOrder;
import vn.vetgo.gateway.repository.VgOrderRepository;
import vn.vetgo.gateway.service.VgOrderService;
import vn.vetgo.gateway.service.dto.VgOrderDTO;
import vn.vetgo.gateway.service.mapper.VgOrderMapper;

/**
 * Service Implementation for managing {@link VgOrder}.
 */
@Service
@Transactional
public class VgOrderServiceImpl implements VgOrderService {

    private final Logger log = LoggerFactory.getLogger(VgOrderServiceImpl.class);

    private final VgOrderRepository vgOrderRepository;

    private final VgOrderMapper vgOrderMapper;

    public VgOrderServiceImpl(VgOrderRepository vgOrderRepository, VgOrderMapper vgOrderMapper) {
        this.vgOrderRepository = vgOrderRepository;
        this.vgOrderMapper = vgOrderMapper;
    }

    @Override
    public VgOrderDTO save(VgOrderDTO vgOrderDTO) {
        log.debug("Request to save VgOrder : {}", vgOrderDTO);
        VgOrder vgOrder = vgOrderMapper.toEntity(vgOrderDTO);
        vgOrder = vgOrderRepository.save(vgOrder);
        return vgOrderMapper.toDto(vgOrder);
    }

    @Override
    public VgOrderDTO update(VgOrderDTO vgOrderDTO) {
        log.debug("Request to update VgOrder : {}", vgOrderDTO);
        VgOrder vgOrder = vgOrderMapper.toEntity(vgOrderDTO);
        vgOrder = vgOrderRepository.save(vgOrder);
        return vgOrderMapper.toDto(vgOrder);
    }

    @Override
    public Optional<VgOrderDTO> partialUpdate(VgOrderDTO vgOrderDTO) {
        log.debug("Request to partially update VgOrder : {}", vgOrderDTO);

        return vgOrderRepository
            .findById(vgOrderDTO.getId())
            .map(existingVgOrder -> {
                vgOrderMapper.partialUpdate(existingVgOrder, vgOrderDTO);

                return existingVgOrder;
            })
            .map(vgOrderRepository::save)
            .map(vgOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VgOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VgOrders");
        return vgOrderRepository.findAll(pageable).map(vgOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VgOrderDTO> findOne(Long id) {
        log.debug("Request to get VgOrder : {}", id);
        return vgOrderRepository.findById(id).map(vgOrderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VgOrder : {}", id);
        vgOrderRepository.deleteById(id);
    }
}
