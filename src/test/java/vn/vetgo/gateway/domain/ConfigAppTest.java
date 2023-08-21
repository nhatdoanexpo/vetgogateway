package vn.vetgo.gateway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.vetgo.gateway.web.rest.TestUtil;

class ConfigAppTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigApp.class);
        ConfigApp configApp1 = new ConfigApp();
        configApp1.setId(1L);
        ConfigApp configApp2 = new ConfigApp();
        configApp2.setId(configApp1.getId());
        assertThat(configApp1).isEqualTo(configApp2);
        configApp2.setId(2L);
        assertThat(configApp1).isNotEqualTo(configApp2);
        configApp1.setId(null);
        assertThat(configApp1).isNotEqualTo(configApp2);
    }
}
