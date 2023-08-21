package vn.vetgo.gateway.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VgOrderMapperTest {

    private VgOrderMapper vgOrderMapper;

    @BeforeEach
    public void setUp() {
        vgOrderMapper = new VgOrderMapperImpl();
    }
}
