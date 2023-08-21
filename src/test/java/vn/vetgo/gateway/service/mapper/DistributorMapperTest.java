package vn.vetgo.gateway.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistributorMapperTest {

    private DistributorMapper distributorMapper;

    @BeforeEach
    public void setUp() {
        distributorMapper = new DistributorMapperImpl();
    }
}
