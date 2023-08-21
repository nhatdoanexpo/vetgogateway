package vn.vetgo.gateway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.vetgo.gateway.web.rest.TestUtil;

class DistributorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Distributor.class);
        Distributor distributor1 = new Distributor();
        distributor1.setId(1L);
        Distributor distributor2 = new Distributor();
        distributor2.setId(distributor1.getId());
        assertThat(distributor1).isEqualTo(distributor2);
        distributor2.setId(2L);
        assertThat(distributor1).isNotEqualTo(distributor2);
        distributor1.setId(null);
        assertThat(distributor1).isNotEqualTo(distributor2);
    }
}
