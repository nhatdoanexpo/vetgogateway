package vn.vetgo.gateway.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryAgentMapperTest {

    private CategoryAgentMapper categoryAgentMapper;

    @BeforeEach
    public void setUp() {
        categoryAgentMapper = new CategoryAgentMapperImpl();
    }
}
