package vn.vetgo.gateway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.vetgo.gateway.web.rest.TestUtil;

class ItemAgentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemAgent.class);
        ItemAgent itemAgent1 = new ItemAgent();
        itemAgent1.setId(1L);
        ItemAgent itemAgent2 = new ItemAgent();
        itemAgent2.setId(itemAgent1.getId());
        assertThat(itemAgent1).isEqualTo(itemAgent2);
        itemAgent2.setId(2L);
        assertThat(itemAgent1).isNotEqualTo(itemAgent2);
        itemAgent1.setId(null);
        assertThat(itemAgent1).isNotEqualTo(itemAgent2);
    }
}
