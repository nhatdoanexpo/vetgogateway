package vn.vetgo.gateway.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.vetgo.gateway.web.rest.TestUtil;

class ConfigAppDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigAppDTO.class);
        ConfigAppDTO configAppDTO1 = new ConfigAppDTO();
        configAppDTO1.setId(1L);
        ConfigAppDTO configAppDTO2 = new ConfigAppDTO();
        assertThat(configAppDTO1).isNotEqualTo(configAppDTO2);
        configAppDTO2.setId(configAppDTO1.getId());
        assertThat(configAppDTO1).isEqualTo(configAppDTO2);
        configAppDTO2.setId(2L);
        assertThat(configAppDTO1).isNotEqualTo(configAppDTO2);
        configAppDTO1.setId(null);
        assertThat(configAppDTO1).isNotEqualTo(configAppDTO2);
    }
}
