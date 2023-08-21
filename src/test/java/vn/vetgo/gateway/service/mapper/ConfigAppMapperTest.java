package vn.vetgo.gateway.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigAppMapperTest {

    private ConfigAppMapper configAppMapper;

    @BeforeEach
    public void setUp() {
        configAppMapper = new ConfigAppMapperImpl();
    }
}
