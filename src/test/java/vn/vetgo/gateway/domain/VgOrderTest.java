package vn.vetgo.gateway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.vetgo.gateway.web.rest.TestUtil;

class VgOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VgOrder.class);
        VgOrder vgOrder1 = new VgOrder();
        vgOrder1.setId(1L);
        VgOrder vgOrder2 = new VgOrder();
        vgOrder2.setId(vgOrder1.getId());
        assertThat(vgOrder1).isEqualTo(vgOrder2);
        vgOrder2.setId(2L);
        assertThat(vgOrder1).isNotEqualTo(vgOrder2);
        vgOrder1.setId(null);
        assertThat(vgOrder1).isNotEqualTo(vgOrder2);
    }
}
