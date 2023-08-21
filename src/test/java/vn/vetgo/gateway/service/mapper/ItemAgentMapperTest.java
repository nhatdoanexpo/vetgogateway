package vn.vetgo.gateway.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemAgentMapperTest {

    private ItemAgentMapper itemAgentMapper;

    @BeforeEach
    public void setUp() {
        itemAgentMapper = new ItemAgentMapperImpl();
    }
}
